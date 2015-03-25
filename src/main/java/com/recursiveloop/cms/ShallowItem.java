package com.recursiveloop.cms;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Property;
import javax.json.JsonObject;
import java.util.List;
import java.util.ArrayList;


public class ShallowItem {
  private String m_name;
  private String m_path;
  private String m_typeName;
  private List<ShallowItem> m_children;

  /**
  * Does not add children
  */
  public ShallowItem(Node node) throws RepositoryException {
    m_name = node.getName();
    m_path = node.getPath();
    m_typeName = "folder";

    if (node.hasProperty("type")) {
      Property prop = node.getProperty("type");
      m_typeName = prop.getString();
    }

    m_children = new ArrayList<ShallowItem>();
  }

  /**
  * Does not add children
  */
  public ShallowItem(JsonObject json) {
    m_name = json.getString("name");
    m_path = json.getString("path");
    m_typeName = json.getString("typeName");
    m_children = new ArrayList<ShallowItem>();
  }

  public ShallowItem(ShallowItem cpy) {
    m_name = cpy.m_name;
    m_path = cpy.m_path;
    m_typeName = cpy.m_typeName;
    m_children = new ArrayList<ShallowItem>();

    for (ShallowItem ch : cpy.m_children) {
      m_children.add(new ShallowItem(ch));
    }
  }

  public void writeTo(Node node) throws InvalidItemException, RepositoryException {
    node.setProperty("type", m_typeName);
  }

  public final String getName() {
    return m_name;
  }

  public final String getPath() {
    return m_path;
  }

  public final String getTypeName() {
    return m_typeName;
  }

  public final List<ShallowItem> getChildren() {
    return m_children;
  }

  public final void addChild(ShallowItem ch) {
    m_children.add(ch);
  }

  public final void removeChild(ShallowItem item) {
    for (int i = 0; i < m_children.size(); ++i) {
      if (m_children.get(i).m_path.equals(item.m_path)) {
        m_children.remove(i);
        break;
      }
    }
  }
}
