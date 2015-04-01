// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms;

import com.recursiveloop.cms.jcrmodel.RlJcrFieldType;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import javax.jcr.Node;
import javax.jcr.PropertyType;
import javax.jcr.Binary;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;
import java.util.Calendar;


public class BinaryItem extends ShallowItem {
  private Map<String, JcrProperty> m_data;

  public BinaryItem(Node node, ItemType itemType)
    throws RepositoryException, InvalidItemException {

    super(node);

    m_data = new HashMap<String, JcrProperty>();

    for (RlJcrFieldType field : itemType.getFields()) {
      String name = field.getName();
      int type = field.getJcrType();
      Object data = null;

      if (node.hasProperty(name)) {
        switch (type) {
          case PropertyType.BINARY:
            data = node.getProperty(name).getBinary();
            break;
          case PropertyType.BOOLEAN:
            data = new Boolean(node.getProperty(name).getBoolean());
            break;
          case PropertyType.DATE:
            data = node.getProperty(name).getDate();
            break;
          case PropertyType.DOUBLE:
            data = new Double(node.getProperty(name).getDouble());
            break;
          case PropertyType.LONG:
            data = new Long(node.getProperty(name).getLong());
            break;
          case PropertyType.STRING:
            data = node.getProperty(name).getString();
            break;
        }
      }

      // data may be null
      m_data.put(name, new JcrProperty(type, data));
    }
  }

  public BinaryItem(ShallowItem cpy) {
    super(cpy);

    m_data = new HashMap<String, JcrProperty>();
  }

  @Override
  public void writeTo(Node node) throws InvalidItemException, RepositoryException { // TODO: Validation check
    super.writeTo(node);

    Iterator<Map.Entry<String, JcrProperty>> i = m_data.entrySet().iterator();

    try {
      while (i.hasNext()) {
        Map.Entry<String, JcrProperty> pair = i.next();

        String name = pair.getKey();
        int type = pair.getValue().type;
        Object data = pair.getValue().data;

        switch (type) {
          case PropertyType.BINARY:
            node.setProperty(name, (Binary)data);
            break;
          case PropertyType.BOOLEAN:
            node.setProperty(name, ((Boolean)data).booleanValue());
            break;
          case PropertyType.DATE:
            node.setProperty(name, (Calendar)data);
            break;
          case PropertyType.DOUBLE:
            node.setProperty(name, ((Double)data).doubleValue());
            break;
          case PropertyType.LONG:
            node.setProperty(name, ((Long)data).longValue());
            break;
          case PropertyType.STRING:
            node.setProperty(name, (String)data);
            break;
        }
      }
    }
    catch (ValueFormatException ex) {
      throw new InvalidItemException("Error committing item", ex);
    }
  }

  public void addProperty(String name, JcrProperty prop) {
    m_data.put(name, prop);
  }

  public JcrProperty getProperty(String name) {
    return m_data.get(name);
  }

  public Iterator<Map.Entry<String, JcrProperty>> iterator() {
    return m_data.entrySet().iterator();
  }
}
