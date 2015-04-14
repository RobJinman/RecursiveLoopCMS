// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms;

import com.recursiveloop.cms.exceptions.InvalidItemException;
import com.recursiveloop.cms.jcrmodel.RlJcrItem;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.json.JsonObject;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import java.util.List;
import java.util.ArrayList;


@XmlAccessorType(XmlAccessType.FIELD)
public class ShallowItem extends RlJcrItem {
  @XmlElement(name = "itemName")
  private String m_name;
  @XmlElement(name = "children")
  private List<ShallowItem> m_children;

  public ShallowItem() {
    m_name = null;
    m_children = new ArrayList<ShallowItem>();
  }

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

      m_name = json.getString("itemName");
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

  public final void setName(String name) {
    m_name = name;
  }

  public final List<ShallowItem> getChildren() {
    return m_children;
  }

  public final void setChildren(List<ShallowItem> children) {
    m_children = children;
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

  public void writeTo(Node node) throws InvalidItemException, RepositoryException {
    write(node);
  }
}
