package com.recursiveloop.cms;

import com.recursiveloop.cms.jcrbeans.Definition;
import com.recursiveloop.cms.jcrbeans.FieldDef;

import java.util.List;


public class Type {
  private Definition m_definition;

  public Type() {
    m_definition = null;
  }

  public Type(Type cpy) {
    if (cpy.m_definition != null) {
      m_definition = new Definition(cpy.m_definition);
    }
  }

  public Type(Definition def) {
    m_definition = def;
  }

  public boolean isNull() {
    return m_definition == null;
  }

  public String getName() {
    return m_definition.getName();
  }

  public String getPath() {
    return m_definition.getPath();
  }

  public List<FieldDef> getFields() {
    return m_definition.getFields();
  }
}
