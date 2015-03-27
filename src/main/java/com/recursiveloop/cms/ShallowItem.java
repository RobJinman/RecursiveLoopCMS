package com.recursiveloop.cms;

import com.recursiveloop.jcrutils.RlJcrItem;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.json.JsonObject;
import java.util.List;
import java.util.ArrayList;


public class ShallowItem extends RlJcrItem {
  private String m_name;
  private List<ShallowItem> m_children;

  /**
  * Does not add children
  */
  public ShallowItem(Node node) throws RepositoryException, InvalidItemException {
    super("folder");
    super.setPath(node.getPath());

    m_name = node.getName();

    if (node.hasProperty("rl:type")) {
      try {
        Property prop = node.getProperty("rl:type");
        super.setTypeName(prop.getString());
      }
      catch (PathNotFoundException ex) {
        throw new InvalidItemException("Error constucting item from JCR node; property missing", ex);
      }
    }

    m_children = new ArrayList<ShallowItem>();
  }

  /**
  * Does not add children
  */
  public ShallowItem(JsonObject json) throws InvalidItemException {
    try {
      super.setPath(json.getString("path"));
      super.setTypeName(json.getString("typeName"));

      m_name = json.getString("name");
      m_children = new ArrayList<ShallowItem>();
    }
    catch (NullPointerException|ClassCastException ex) {
      throw new InvalidItemException("Error constructing item from JSON object; property missing?", ex);
    }
  }

  public ShallowItem(ShallowItem cpy) {
    super(cpy);

    m_name = cpy.m_name;
    m_children = new ArrayList<ShallowItem>();

    for (ShallowItem ch : cpy.m_children) {
      m_children.add(new ShallowItem(ch));
    }
  }

  public final String getName() {
    return m_name;
  }

  public final List<ShallowItem> getChildren() {
    return m_children;
  }

  public final void addChild(ShallowItem ch) {
    m_children.add(ch);
  }

  public final void removeChild(ShallowItem item) {
    for (int i = 0; i < m_children.size(); ++i) {
      if (m_children.get(i).getPath().equals(item.getPath())) {
        m_children.remove(i);
        break;
      }
    }
  }
}
