package com.recursiveloop.cms;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Property;


public class ItemMetaData {
  private String m_name;
  private String m_path;
  private String m_typeName;

  public ItemMetaData(Node node) throws RepositoryException {
    m_name = node.getName();
    m_path = node.getPath();
    m_typeName = "folder";

    if (node.hasProperty("type")) {
      Property prop = node.getProperty("type");
      m_typeName = prop.getString();
    }
  }
}
