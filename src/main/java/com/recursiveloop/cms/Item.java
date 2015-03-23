package com.recursiveloop.cms;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Property;
import javax.json.JsonObject;
import java.util.List;
import java.util.ArrayList;


public class Item {
  private String m_name;
  private String m_path;
  private List<Item> m_children;
  private String m_typeName;
  private ItemContent m_content;

  public Item(Node node, boolean populate) throws RepositoryException {
    m_name = node.getName();
    m_path = node.getPath();
    m_children = new ArrayList<Item>();
    m_typeName = "folder";
    m_content = new ItemContent();

    if (node.hasProperty("type")) {
      Property prop = node.getProperty("type");
      m_typeName = prop.getString();
    }

    if (populate) {
      populate(node);
    }
  }

  /**
  * Produces deep copy, excluding content
  */
  public Item(Item cpy) {
    m_name = cpy.m_name;
    m_path = cpy.m_path;
    m_children = new ArrayList<Item>();
    for (Item ch : cpy.m_children) {
      m_children.add(new Item(ch));
    }
    m_typeName = cpy.m_typeName;
    m_content = new ItemContent();
  }

  /**
  * The JSON object should look like as follows, with data being
  * a flat list of key-value pairs. Child data should not be included.
  *
  * {
  *   "name": ...,
  *   "path": ...,
  *   "typeName": ...,
  *   "data": {}
  *}
  */
  public Item(JsonObject root) {
    m_name = root.getString("name");
    m_path = root.getString("path");
    m_children = new ArrayList<Item>();
    m_typeName = root.getString("typeName");
    m_content = new ItemContent(root.getJsonObject("data"));
  }

  /**
  * Does not populate children
  */
  public void populate(Node node) throws RepositoryException {
    m_content.populate(node);
  }

  public String getName() {
    return m_name;
  }

  public String getPath() {
    return m_path;
  }

  public List<Item> getChildren() {
    return m_children;
  }

  public void addChild(Item ch) {
    m_children.add(ch);
  }

  public String getTypeName() {
    return m_typeName;
  }

  public ItemContent getContent() {
    return m_content;
  }

  public void setContent(ItemContent content) {
    m_content = content;
  }
}
