package com.recursiveloop.cms;

import com.recursiveloop.jcrutils.RlJcrItemType;
import com.recursiveloop.jcrutils.RlJcrFieldType;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Value;
import javax.jcr.Workspace;
import javax.jcr.NamespaceRegistry;
import javax.jcr.nodetype.NodeTypeManager;
import javax.inject.Inject;
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
import org.apache.jackrabbit.commons.cnd.CndImporter;
import org.apache.jackrabbit.commons.cnd.ParseException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
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

  @Inject
  ItemParser m_parser;

  private Session m_session;
  private ObjectContentManager m_ocm;
  private ShallowItem m_itemRoot;
  private Map<String, ShallowItem> m_itemMap; // By path
  private Map<String, ItemType> m_typeMap;    // By name

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
      classes.add(RlJcrItemType.class);
      classes.add(RlJcrFieldType.class);

      Mapper mapper = new AnnotationMapperImpl(classes);
      m_ocm =  new ObjectContentManagerImpl(m_session, mapper);

      m_itemMap = new HashMap<String, ShallowItem>();
      m_typeMap = new HashMap<String, ItemType>();

      loadJcrTypes();
      loadTypes();
      loadShallowItems();
    }
    catch (InvalidItemException ex) {
      m_logger.log(Level.SEVERE, "Error initialising repository", ex);
    }
    catch (RepositoryException ex) {
      m_logger.log(Level.SEVERE, "Error initialising repository", ex);
    }
    catch (IOException ex) {
      m_logger.log(Level.SEVERE, "Error initialising repository", ex);
    }
    catch (ParseException ex) {
      m_logger.log(Level.SEVERE, "Error initialising repository", ex);
    }
  }

  @PreDestroy
  public void teardown() {
    m_session.logout();
  }

  @Lock(LockType.READ)
  public ShallowItem getShallowItemTree() {
    return m_itemRoot;
  }

  @Lock(LockType.READ)
  public ShallowItem getShallowItem(String path) {
    return m_itemMap.get(path);
  }

  @Lock(LockType.READ)
  public StringItem getStringItem(String path)
    throws RepositoryException, NoSuchItemException, NoSuchTypeException, InvalidItemException {

    ShallowItem item = m_itemMap.get(path);

    try {
      if (item != null) {
        Node node = m_session.getNode(item.getPath());
        ItemType type = m_typeMap.get(item.getTypeName());

        if (type == null) {
          throw new NoSuchTypeException(item.getTypeName());
        }

        BinaryItem binItem = new BinaryItem(node, type);
        StringItem strItem = m_parser.stringify(binItem, type);

        return strItem;
      }
      else {
        m_logger.log(Level.WARNING, "Error retrieving full item; no such item");
        throw new NoSuchItemException(path);
      }
    }
    catch (RepositoryException ex) {
      m_logger.log(Level.SEVERE, "Error populating item from repository", ex);
      throw ex;
    }
  }

  @Lock(LockType.READ)
  public Collection<String> getTypeList() {
    return m_typeMap.keySet();
  }

  @Lock(LockType.READ)
  public ItemType getType(String name) {
    if (name == "folder") {
      return new ItemType("folder");
    }

    ItemType type = m_typeMap.get(name);

    if (type == null) {
      m_logger.log(Level.WARNING, "Error fetching type '" + name + "'; no such type");
      return new ItemType();
    }

    return type;
  }

  /**
  * Verifies that the item is of known type.
  */
  @Lock(LockType.WRITE)
  public void insertNewItem(StringItem item)
    throws RepositoryException, NoSuchItemException, NoSuchTypeException, InvalidItemException {

    m_logger.log(Level.INFO, "Inserting new item");

    String path = item.getPath();

    Node root = m_session.getRootNode();
    createNodeIfNotExists(root, path, "rlt:item");

    ItemType type = m_typeMap.get(item.getTypeName());
    if (type == null) {
      throw new NoSuchTypeException(item.getTypeName());
    }

    Node node = m_session.getNode(path);

    BinaryItem binItem = m_parser.parse(item, type);
    binItem.writeTo(node);

    m_session.save();


    // Retain a shallow copy of the new item

    ShallowItem shallowCopy = new ShallowItem(item);

    int i = path.lastIndexOf("/");
    if (i != -1) {
      String parent = path.substring(0, i);
      ShallowItem par = m_itemMap.get(parent);
      if (par != null) {
        par.addChild(shallowCopy);
      }
    }
    m_itemMap.put(path, shallowCopy);
  }

  /**
  * Verifies that the item to be updated actually exists in addition to
  * the usual validation check.
  */
  @Lock(LockType.WRITE)
  public void updateItem(StringItem item)
    throws RepositoryException, NoSuchItemException, NoSuchTypeException, InvalidItemException {

    m_logger.log(Level.INFO, "Updating item");

    String path = item.getPath();

    if (!m_session.nodeExists(item.getPath())) {
      throw new NoSuchItemException(item.getPath());
    }

    ItemType type = m_typeMap.get(item.getTypeName());
    if (type == null) {
      throw new NoSuchTypeException(item.getTypeName());
    }

    Node node = m_session.getNode(path);

    BinaryItem binItem = m_parser.parse(item, type);
    binItem.writeTo(node);

    m_session.save();
  }

  @Lock(LockType.WRITE)
  public void deleteItem(String path) throws RepositoryException, NoSuchItemException {
    if (!m_session.nodeExists(path)) {
      throw new NoSuchItemException(path);
    }

    m_session.removeItem(path);
    m_session.save();

    ShallowItem item = m_itemMap.get(path);

    int i = path.lastIndexOf("/");
    if (i != -1) {
      String parent = path.substring(0, i);
      ShallowItem par = m_itemMap.get(parent);
      if (par != null) {
        par.removeChild(item);
      }
    }
    m_itemMap.remove(path);
  }

  @Lock(LockType.WRITE)
  public void insertNewType(ItemType type) {

  }

  @Lock(LockType.WRITE)
  public void updateType(ItemType type) {

  }

  @Lock(LockType.WRITE)
  public void deleteType(ItemType type) {

  }

  private void loadShallowItems() throws RepositoryException, InvalidItemException {
    Node root = m_session.getRootNode();

    createNodeIfNotExists(root, "rl:items");

    m_itemMap.clear();

    Node node = root.getNode("rl:items");
    m_itemRoot = new ShallowItem(node);

    extractItems_r(node, m_itemRoot);
  }

  private void extractItems_r(Node node, ShallowItem item) throws RepositoryException {
    m_itemMap.put(item.getPath(), item);

    NodeIterator children = node.getNodes();

    while (children.hasNext()) {
      Node chNode = children.nextNode();

      ShallowItem chInst;
      try {
        chInst = new ShallowItem(chNode);
      }
      catch (InvalidItemException ex) {
        m_logger.log(Level.WARNING, "Repository contains invalid item at '" + item.getPath() + "'");
        continue;
      }

      item.addChild(chInst);
      extractItems_r(chNode, chInst);
    }
  }

  private void loadJcrTypes() throws RepositoryException, IOException, ParseException {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    InputStream is = classloader.getResourceAsStream("types.cnd");
    InputStreamReader rdr = new InputStreamReader(is);

    Workspace workspace = m_session.getWorkspace();
    NodeTypeManager manager = workspace.getNodeTypeManager();
    NamespaceRegistry nsReg = workspace.getNamespaceRegistry();

    CndImporter.registerNodeTypes(rdr, "types.cnd", manager, nsReg, m_session.getValueFactory(), true);
  }

  private void loadTypes() throws RepositoryException {
    Node root = m_session.getRootNode();

    createNodeIfNotExists(root, "rl:types");

    m_typeMap.clear();

    extractTypes(root);
  }

  private void extractTypes(Node root) throws RepositoryException {
    Node defs = root.getNode("rl:types");
    NodeIterator children = defs.getNodes();

    while (children.hasNext()) {
      Node node = children.nextNode();

      RlJcrItemType def = (RlJcrItemType)m_ocm.getObject(node.getPath());
      ItemType type = new ItemType(node.getName(), def);

      m_typeMap.put(type.getName(), type);
    }
  }

  private void createNodeIfNotExists(Node node, String relPath)
    throws RepositoryException {

    createNodeIfNotExists(node, relPath, "rlt:folder");
  }

  /**
  * Intermediate nodes are given type rlt:folder
  */
  private void createNodeIfNotExists(Node node, String relPath, String typeName)
    throws RepositoryException {

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
        node.addNode(path, i + 1 == parts.length ? typeName : "rlt:folder");
      }
    }
  }
}
