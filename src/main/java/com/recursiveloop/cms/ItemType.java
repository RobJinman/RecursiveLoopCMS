// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms;

import com.recursiveloop.cms.exceptions.InvalidTypeException;
import com.recursiveloop.cms.jcrmodel.RlJcrItemType;
import com.recursiveloop.cms.jcrmodel.RlJcrFieldType;
import com.recursiveloop.cms.jcrmodel.RlJcrParserParam;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;


public class ItemType {
  private static final Logger m_logger = Logger.getLogger(ItemType.class.getName());

  private String m_name;
  private RlJcrItemType m_type;

  public ItemType() {
    m_name = "unknown";
    m_type = null;
  }

  public ItemType(String name) {
    m_name = name;
    m_type = null;
  }

  public ItemType(ItemType cpy) {
    m_name = cpy.m_name;
    m_type = new RlJcrItemType(cpy.m_type);
  }

  public ItemType(String name, RlJcrItemType type) {
    m_name = name;
    m_type = type;
  }

  public ItemType(JsonObject data) throws InvalidTypeException {
    m_type = new RlJcrItemType();

    try {
      m_name = data.getString("typeName");

      JsonObject fieldsData = data.getJsonObject("fields");
      Iterator<Map.Entry<String, JsonValue>> i = fieldsData.entrySet().iterator();

      while (i.hasNext()) {
        Map.Entry<String, JsonValue> pair = i.next();

        String fieldName = pair.getKey();
        JsonObject fieldData = fieldsData.getJsonObject(fieldName);
        addField(fieldData);
      }
    }
    catch (NullPointerException|ClassCastException ex) {
      throw new InvalidTypeException("Error constructing type from JSON object; property missing?", ex);
    }
  }

  public JsonObject toJson() {
    JsonObjectBuilder fields = Json.createObjectBuilder();
    for (RlJcrFieldType f : m_type.getFields()) {
      JsonObjectBuilder parserParams = Json.createObjectBuilder();
      for (RlJcrParserParam pp : f.getParserParams()) {
        parserParams.add(pp.getName(), pp.getValue());
      }

      JsonObjectBuilder widgetParams = Json.createObjectBuilder();
/*
      for (RlJcrParserParam wp : f.getWidgetParams()) {
        widgetParams.add(wp.getName(), wp.getValue());
      }
*/
      fields.add(f.getName(), Json.createObjectBuilder()
        .add("fieldName", f.getName())
        .add("type", f.getJcrType())
        .add("widget", f.getWidget())
        .add("required", f.getRequired())
        .add("defaultValue", f.getDefault())
        .add("parserParams", parserParams)
        .add("widgetParams", widgetParams));
    }

    JsonObject data = Json.createObjectBuilder()
      .add("typeName", m_name)
      .add("fields", fields)
      .build();

    return data;
  }

  public boolean isNull() {
    return m_type == null;
  }

  public String getName() {
    return m_name;
  }

  public void setName(String name) {
    m_name = name;
  }

  public String getPath() {
    if (isNull()) {
      return null;
    }

    return m_type.getPath();
  }

  public RlJcrItemType getType() {
    return m_type;
  }

  public List<RlJcrFieldType> getFields() {
    if (isNull()) {
      return null;
    }

    return m_type.getFields();
  }

  public RlJcrFieldType getField(String name) {
    if (isNull()) {
      return null;
    }

    for (RlJcrFieldType f : m_type.getFields()) {
      if (f.getName().equals(name)) {
        return f;
      }
    }

    return null;
  }

  public void addField(RlJcrFieldType field) {
    if (isNull()) {
      m_type = new RlJcrItemType();
    }

    m_type.addField(field);
  }

  public void addField(JsonObject json) {
    if (isNull()) {
      m_type = new RlJcrItemType();
    }

    RlJcrFieldType field = new RlJcrFieldType();
    field.setName(json.getString("fieldName"));
    field.setJcrType(json.getInt("type"));
    field.setWidget(json.getString("widget"));
    field.setRequired(json.getBoolean("required"));
    field.setDefault(json.getString("defaultValue"));

    JsonObject parserParamsData = json.getJsonObject("parserParams");
    Iterator<Map.Entry<String, JsonValue>> j = parserParamsData.entrySet().iterator();

    while (j.hasNext()) {
      Map.Entry<String, JsonValue> pair_ = j.next();
      field.setParserParam(pair_.getKey(), parserParamsData.getString(pair_.getKey()));
    }

    m_type.addField(field);
  }

  public void removeField(String name) {
    if (isNull()) {
      return;
    }

    List<RlJcrFieldType> fields = m_type.getFields();
    for (int i = 0; i < fields.size(); ++i) {
      RlJcrFieldType f = fields.get(i);

      if (f.getName().equals(name)) {
        fields.remove(i);
        return;
      }
    }
  }
}
