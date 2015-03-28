// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.jcrmodel;

import org.apache.jackrabbit.ocm.mapper.impl.annotation.Field;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Collection;
import java.util.List;
import java.util.ArrayList;


@Node(jcrType = "rlt:fieldType")
public class RlJcrFieldType {
  private String m_rlPath;
  private String m_rlName;
  private int m_rlJcrType;
  private String m_rlWidget;
  private boolean m_rlRequired;
  private String m_rlDefault;
  @Collection(jcrName = "rl:parserParams", jcrType = "rlt:parserParams", jcrElementName = "rl:parserParam")
  public List<RlJcrParserParam> parserParams;

  public RlJcrFieldType() {}

  public RlJcrFieldType(String name, int type, boolean required) {
    m_rlPath = null;
    m_rlName = name;
    m_rlJcrType = type;
    m_rlWidget = "textarea";
    m_rlRequired = required;
    m_rlDefault = "";
    this.parserParams = new ArrayList<RlJcrParserParam>();
  }

  public RlJcrFieldType(String name, int type, boolean required, String def) {
    m_rlPath = null;
    m_rlName = name;
    m_rlJcrType = type;
    m_rlWidget = "textarea";
    m_rlRequired = required;
    m_rlDefault = def;
    this.parserParams = new ArrayList<RlJcrParserParam>();
  }

  public RlJcrFieldType(String name, int type, boolean required, String def, String widget) {
    m_rlPath = null;
    m_rlName = name;
    m_rlJcrType = type;
    m_rlWidget = widget;
    m_rlRequired = required;
    m_rlDefault = def;
    this.parserParams = new ArrayList<RlJcrParserParam>();
  }

  public RlJcrFieldType(RlJcrFieldType cpy) {
    m_rlPath = cpy.m_rlPath;
    m_rlName = cpy.m_rlName;
    m_rlJcrType = cpy.m_rlJcrType;
    m_rlWidget = cpy.m_rlWidget;
    m_rlRequired = cpy.m_rlRequired;
    m_rlDefault = cpy.m_rlDefault;

    this.parserParams = new ArrayList<RlJcrParserParam>();
    for (RlJcrParserParam p : cpy.parserParams) {
      this.parserParams.add(new RlJcrParserParam(p));
    }
  }

  @Field(jcrName = "rl:path", path = true)
  public String getPath() {
    return m_rlPath;
  }

  public void setPath(String path) {
    m_rlPath = path;
  }

  @Field(jcrName = "rl:name")
  public String getName() {
    return m_rlName;
  }

  public void setName(String name) {
    m_rlName = name;
  }

  @Field(jcrName = "rl:jcrType")
  public int getJcrType() {
    return m_rlJcrType;
  }

  public void setJcrType(int jcrType) {
    m_rlJcrType = jcrType;
  }

  @Field(jcrName = "rl:required")
  public boolean getRequired() {
    return m_rlRequired;
  }

  public void setRequired(boolean required) {
    m_rlRequired = required;
  }

  @Field(jcrName = "rl:default")
  public String getDefault() {
    return m_rlDefault;
  }

  public void setDefault(String def) {
    m_rlDefault = def;
  }

  @Field(jcrName = "rl:widget")
  public String getWidget() {
    return m_rlWidget;
  }

  public void setWidget(String widget) {
    m_rlWidget = widget;
  }

  public List<RlJcrParserParam> getParserParams() {
    return this.parserParams;
  }

  public void setParserParams(List<RlJcrParserParam> params) {
    this.parserParams = params;
  }

  public void setParserParam(String name, String value) {
    this.parserParams.add(new RlJcrParserParam(name, value));
  }

  public String getParserParam(String name) {
    for (RlJcrParserParam p : this.parserParams) {
      if (name.equals(p.getName())) {
        return p.getValue();
      }
    }

    return null;
  }
}
