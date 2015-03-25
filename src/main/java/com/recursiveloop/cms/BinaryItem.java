package com.recursiveloop.cms;

import com.recursiveloop.cms.jcrbeans.FieldDef;

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

  public BinaryItem(Node node, Type itemType) throws RepositoryException, InvalidItemException {
    super(node);

    // TODO: Validation checks ...

    m_data = new HashMap<String, JcrProperty>();

    for (FieldDef field : itemType.getFields()) {
      String name = field.getName();
      String type = field.getType();

      if (type.equals(PropertyType.TYPENAME_BINARY)) {
        Binary data = node.getProperty(name).getBinary();
        m_data.put(name, new JcrProperty(PropertyType.BINARY, data));
      }
      else if (type.equals(PropertyType.TYPENAME_BOOLEAN)) {
        Boolean data = new Boolean(node.getProperty(name).getBoolean());
        m_data.put(name, new JcrProperty(PropertyType.BOOLEAN, data));
      }
      else if (type.equals(PropertyType.TYPENAME_DATE)) {
        Calendar data = node.getProperty(name).getDate();
        m_data.put(name, new JcrProperty(PropertyType.DATE, data));
      }
      else if (type.equals(PropertyType.TYPENAME_DOUBLE)) {
        Double data = new Double(node.getProperty(name).getDouble());
        m_data.put(name, new JcrProperty(PropertyType.DOUBLE, data));
      }
      else if (type.equals(PropertyType.TYPENAME_LONG)) {
        Long data = new Long(node.getProperty(name).getLong());
        m_data.put(name, new JcrProperty(PropertyType.LONG, data));
      }
      else if (type.equals(PropertyType.TYPENAME_STRING)) {
        String data = node.getProperty(name).getString();
        m_data.put(name, new JcrProperty(PropertyType.STRING, data));
      }
    }
  }

  public BinaryItem(ShallowItem cpy) {
    super(cpy);

    m_data = new HashMap<String, JcrProperty>();
  }

  @Override
  public void writeTo(Node node) throws InvalidItemException, RepositoryException {
    super.writeTo(node);

    Iterator<Map.Entry<String, JcrProperty>> i = m_data.entrySet().iterator();

    try {
      while (i.hasNext()) {
        Map.Entry<String, JcrProperty> pair = i.next();

        String name = pair.getKey();
        int type = pair.getValue().type;
        Object data = pair.getValue().data;

        switch (type) {
          case PropertyType.BINARY:   node.setProperty(name, (Binary)data);                   break;
          case PropertyType.BOOLEAN:  node.setProperty(name, ((Boolean)data).booleanValue()); break;
          case PropertyType.DATE:     node.setProperty(name, (Calendar)data);                 break;
          case PropertyType.DOUBLE:   node.setProperty(name, ((Double)data).doubleValue());   break;
          case PropertyType.LONG:     node.setProperty(name, ((Long)data).longValue());       break;
          case PropertyType.STRING:   node.setProperty(name, (String)data);                   break;
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
