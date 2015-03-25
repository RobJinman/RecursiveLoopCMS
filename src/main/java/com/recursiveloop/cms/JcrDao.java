package com.recursiveloop.cms;

import com.recursiveloop.cms.jcrbeans.Definition;
import com.recursiveloop.cms.jcrbeans.FieldDef;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Value;
import javax.inject.Named;
import javax.annotation.PreDestroy;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Lock;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LockType;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.apache.jackrabbit.ocm.manager.impl.ObjectContentManagerImpl;
import org.apache.jackrabbit.ocm.mapper.Mapper;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.AnnotationMapperImpl;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.logging.Level;


@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class JcrDao {
  private static final Logger m_logger = Logger.getLogger(JcrDao.class.getName());

  private Session m_session;
  private ObjectContentManager m_ocm;
  private Item m_itemRoot;
  private Map<String, Item> m_itemMap; // By path
  private Map<String, Type> m_typeMap; // By name

  @PostConstruct
  public void setup() {
    try {
      Repository repository = JcrUtils.getRepository();
      m_session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

      Value transactionSupport = repository.getDescriptorValue(Repository.OPTION_TRANSACTIONS_SUPPORTED);
      if (transactionSupport.getBoolean() == false) {
        m_logger.log(Level.SEVERE, "Repository does not support transactions");
      }

      List<Class> classes = new ArrayList<Class>();	
      classes.add(Definition.class);
      classes.add(FieldDef.class);

      Mapper mapper = new AnnotationMapperImpl(classes);
      m_ocm =  new ObjectContentManagerImpl(m_session, mapper);

      m_itemMap = new HashMap<String, Item>();
      m_typeMap = new HashMap<String, Type>();

      load();
    }
    catch (RepositoryException ex) {
      m_logger.log(Level.SEVERE, "Error accessing repository", ex);
    }
  }

  @PreDestroy
  public void teardown() {
    m_session.logout();
  }

  @Lock(LockType.READ)
  public Item getItemTree() {
    return m_itemRoot;
  }

  @Lock(LockType.READ)
  public Item getItem(String path) {
    return m_itemMap.get(path);
  }

  @Lock(LockType.READ)
  public Item fetchFullItem(String path) {
    Item item = m_itemMap.get(path);
    if (item != null) {
      Item cpy = new Item(m_itemMap.get(path));

      try {
        Node node = m_session.getNode(path);
        cpy.populate(node);
      }
      catch (RepositoryException ex) {
        m_logger.log(Level.SEVERE, "Error populating item from repository", ex);
      }

      return cpy;
    }
    else {
      m_logger.log(Level.WARNING, "Error retrieving full item; no such item");
      return null;
    }
  }

  @Lock(LockType.READ)
  public Collection<String> getTypeList() {
    return m_typeMap.keySet();
  }

  @Lock(LockType.READ)
  public Type fetchType(String name) {
    if (name == "folder") {
      return new Type();
    }

    Type type = m_typeMap.get(name);

    if (type == null) {
      m_logger.log(Level.WARNING, "Error fetching type '" + name + "'; no such type");
      return new Type();
    }

    return type;
  }

  /**
  * Verifies that the item is of known type.
  */
  @Lock(LockType.WRITE)
  public void insertNewItem(Item item)
    throws RepositoryException, NoSuchItemException, NoSuchTypeException, InvalidItemException {

    m_logger.log(Level.INFO, "Inserting new item");

    String path = item.getPath();

    Node root = m_session.getRootNode();
    createNodeIfNotExists(root, path);

    commitItem(item);

    // Gut the item before stashing it away
    item.setContent(new ItemContent());

    int i = path.lastIndexOf("/");
    if (i != -1) {
      String parent = path.substring(0, i);
      Item par = m_itemMap.get(parent);
      if (par != null) {
        par.addChild(item);
      }
    }
    m_itemMap.put(item.getPath(), item);
  }

  /**
  * Verifies that the item to be updated actually exists in addition to
  * the usual validation check.
  */
  @Lock(LockType.WRITE)
  public void updateItem(Item item)
    throws RepositoryException, NoSuchItemException, NoSuchTypeException, InvalidItemException {

    m_logger.log(Level.INFO, "Updating item");

    commitItem(item);
  }

  @Lock(LockType.WRITE)
  public void deleteItem(String path) throws RepositoryException, NoSuchItemException {
    if (!m_session.nodeExists(path)) {
      throw new NoSuchItemException();
    }

    m_session.removeItem(path);
    m_session.save();

    Item item = m_itemMap.get(path);

    int i = path.lastIndexOf("/");
    if (i != -1) {
      String parent = path.substring(0, i);
      Item par = m_itemMap.get(parent);
      if (par != null) {
        par.removeChild(item);
      }
    }
    m_itemMap.remove(path);
  }

  @Lock(LockType.WRITE)
  public void commitType(Type type) {

  }

  @Lock(LockType.WRITE)
  public void deleteType(Type type) {

  }

  private void commitItem(Item item)
    throws RepositoryException, NoSuchItemException, NoSuchTypeException, InvalidItemException {

    try {
      Node root = m_session.getRootNode();

      if (!m_session.nodeExists(item.getPath())) {
        throw new NoSuchItemException();
      }

      Type type = m_typeMap.get(item.getTypeName());
      if (type == null) {
        throw new NoSuchTypeException();
      }

      ItemContent content = item.getContent();
      // TODO: Use ItemParser to convert ItemContent into ItemData

      Node node = m_session.getNode(item.getPath());
      node.setProperty("type", type.getName());

      Iterator<Map.Entry<String, String>> i = content.iterator();
      while (i.hasNext()) {
        Map.Entry<String, String> pair = i.next();
        node.setProperty(pair.getKey(), pair.getValue());
      }
      m_session.save();
    }
    catch (RepositoryException ex) {
      m_logger.log(Level.SEVERE, "Error commiting item to repository", ex);
      throw ex;
    }
  }

  /**
  * This loads the entire item tree and all types into memory. This could
  * be improved by loading only a specified sub-tree to a specified depth.
  */
  private void load() throws RepositoryException {
    Node root = m_session.getRootNode();

    createNodeIfNotExists(root, "rlcms/instances");
    createNodeIfNotExists(root, "rlcms/defs");

    m_itemMap.clear();
    m_typeMap.clear();

    extractTypes(root);

    Node item = root.getNode("rlcms/instances");
    m_itemRoot = new Item(item, false);

    extractItems_r(item, m_itemRoot);
  }

  private void extractItems_r(Node node, Item item) throws RepositoryException {
    m_itemMap.put(item.getPath(), item);

    NodeIterator children = node.getNodes();

    while (children.hasNext()) {
      Node chNode = children.nextNode();

      Item chInst = new Item(chNode, false);
      item.addChild(chInst);

      extractItems_r(chNode, chInst);
    }
  }

  private void extractTypes(Node root) throws RepositoryException {
    Node defs = root.getNode("rlcms/defs");
    NodeIterator children = defs.getNodes();

    while (children.hasNext()) {
      Node node = children.nextNode();

      Definition def = (Definition)m_ocm.getObject(node.getPath());
      Type type = new Type(def);

      m_typeMap.put(type.getName(), type);
    }
  }

  private void createNodeIfNotExists(Node node, String relPath) throws RepositoryException {
    if (relPath.startsWith("/")) {
      relPath = relPath.substring(1);
    }
    String[] parts = relPath.split("/");

    String path = "";
    for (int i = 0; i < parts.length; ++i) {
      if (i > 0) {
        path = path.concat("/");
      }
      path = path.concat(parts[i]);

      if (!node.hasNode(path)) {
        node.addNode(path);
      }
    }
  }
}
