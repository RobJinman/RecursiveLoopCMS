package com.recursiveloop.cms;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;


public class ItemData {
  private Map<String, JcrProperty> m_data;

  public ItemData() {
    m_data = new HashMap<String, JcrProperty>();
  }

  public void addProperty(String name, JcrProperty value) {
    m_data.put(name, value);
  }

  public Iterator<Map.Entry<String, JcrProperty>> iterator() {
    return m_data.entrySet().iterator();
  }
}
