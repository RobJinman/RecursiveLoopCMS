package com.recursiveloop.cms.jcrbeans;

import org.apache.jackrabbit.ocm.mapper.impl.annotation.Field;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;


@Node
public class FieldDef {
  private String m_path;
  private String m_name;
  private String m_type;
  private String m_widget;
  private boolean m_required;
  private String m_default;

  public FieldDef() {}

  public FieldDef(String name, String type, boolean required) {
    m_path = null;
    m_name = name;
    m_type = type;
    m_widget = "textarea";
    m_required = required;
    m_default = "";
  }

  public FieldDef(String name, String type, boolean required, String def) {
    m_path = null;
    m_name = name;
    m_type = type;
    m_widget = "textarea";
    m_required = required;
    m_default = def;
  }

  public FieldDef(String name, String type, boolean required, String def, String widget) {
    m_path = null;
    m_name = name;
    m_type = type;
    m_widget = widget;
    m_required = required;
    m_default = def;
  }

  public FieldDef(FieldDef cpy) {
    m_path = null;
    m_name = cpy.m_name;
    m_type = cpy.m_type;
    m_widget = cpy.m_widget;
    m_required = cpy.m_required;
    m_default = cpy.m_default;
  }

  @Field(jcrName = "path", path = true)
  public String getPath() {
    return m_path;
  }

  public void setPath(String path) {
    m_path = path;
  }

  @Field(jcrName = "name")
  public String getName() {
    return m_name;
  }

  public void setName(String name) {
    m_name = name;
  }

  @Field(jcrName = "type")
  public String getType() {
    return m_type;
  }

  public void setType(String type) {
    m_type = type;
  }

  @Field(jcrName = "required")
  public boolean getRequired() {
    return m_required;
  }

  public void setRequired(boolean required) {
    m_required = required;
  }

  @Field(jcrName = "default")
  public String getDefault() {
    return m_default;
  }

  public void setDefault(String def) {
    m_default = def;
  }

  @Field(jcrName = "widget")
  public String getWidget() {
    return m_widget;
  }

  public void setWidget(String widget) {
    m_widget = widget;
  }
}
