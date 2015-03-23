package com.recursiveloop.cms;

import javax.jcr.RepositoryException;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;


public class ItemContent {
  private Map<String, String> m_data;

  public ItemContent() {
    m_data = new HashMap<String, String>();
  }

  public ItemContent(Node node) throws RepositoryException {
    m_data = new HashMap<String, String>();
    populate(node);
  }

  /**
  * JSON object should not contain child data. It is a flat
  * list of key-value pairs.
  */
  public ItemContent(JsonObject json) {
    m_data = new HashMap<String, String>();

    Iterator<Map.Entry<String, JsonValue>> i = json.entrySet().iterator();
    while (i.hasNext()) {
      Map.Entry<String, JsonValue> pair = i.next();
      m_data.put(pair.getKey(), json.getString(pair.getKey()));
    }
  }

  public void populate(Node node) throws RepositoryException {
    PropertyIterator props = node.getProperties();

    while (props.hasNext()) {
      Property prop = props.nextProperty();
      m_data.put(prop.getName(), prop.getString());
    }
  }

  public void setData(Map<String, String> data) {
    m_data = data;
  }

  public boolean isEmpty() {
    return m_data.isEmpty();
  }

  public String getProperty(String name) {
    return m_data.get(name);
  }

  public Iterator<Map.Entry<String, String>> iterator() {
    return m_data.entrySet().iterator();
  }
}
