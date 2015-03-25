package com.recursiveloop.cms;

import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;


public class StringItem extends ShallowItem {
  private Map<String, String> m_data;

  /**
  * JSON object should not contain child data. It is a flat
  * list of key-value pairs.
  */
  public StringItem(JsonObject json) {
    super(json);

    m_data = new HashMap<String, String>();

    JsonObject data = json.getJsonObject("data");
    Iterator<Map.Entry<String, JsonValue>> i = data.entrySet().iterator();

    while (i.hasNext()) {
      Map.Entry<String, JsonValue> pair = i.next();
      m_data.put(pair.getKey(), data.getString(pair.getKey()));
    }
  }

  public StringItem(ShallowItem cpy) {
    super(cpy);

    m_data = new HashMap<String, String>();
  }

  public Iterator<Map.Entry<String, String>> iterator() {
    return m_data.entrySet().iterator();
  }

  public String getProperty(String name) {
    return m_data.get(name);
  }

  public void addProperty(String name, String value) {
    m_data.put(name, value);
  }
}
