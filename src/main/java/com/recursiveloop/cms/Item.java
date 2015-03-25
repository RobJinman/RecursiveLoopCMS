package com.recursiveloop.cms;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Property;
import javax.json.JsonObject;
import java.util.List;
import java.util.ArrayList;


public class Item {
  private ItemMetaData m_meta;
  private ItemContent m_content;
  private ItemData m_data;
  private List<Item> m_children;

  public Item(ItemMetaData meta) {
    m_meta = meta;
  }

  /**
  * Produces deep copy, excluding content
  */
  public Item(Item cpy) {
    m_children = new ArrayList<Item>();
    for (Item ch : cpy.m_children) {
      m_children.add(new Item(ch));
    }
    m_meta = new ItemMetaData(cpy.m_meta);
    m_content = null;
    m_data = null;
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

  public void removeChild(Item item) {
    for (int i = 0; i < m_children.size(); ++i) {
      if (m_children.get(i).m_path.equals(item.m_path)) {
        m_children.remove(i);
        break;
      }
    }
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
