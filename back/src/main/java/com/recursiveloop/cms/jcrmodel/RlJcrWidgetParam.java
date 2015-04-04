// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.jcrmodel;

import org.apache.jackrabbit.ocm.mapper.impl.annotation.Field;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;


@Node(jcrType = "rlt:widgetParam")
public class RlJcrWidgetParam {
  private String m_rlName;
  private String m_rlValue;

  public RlJcrWidgetParam() {}

  public RlJcrWidgetParam(RlJcrWidgetParam cpy) {
    m_rlName = cpy.m_rlName;
    m_rlValue = cpy.m_rlValue;
  }

  public RlJcrWidgetParam(String name, String value) {
    m_rlName = name;
    m_rlValue = value;
  }

  @Field(jcrName = "rl:name")
  public String getName() {
    return m_rlName;
  }

  public void setName(String name) {
    m_rlName = name;
  }

  @Field(jcrName = "rl:value")
  public String getValue() {
    return m_rlValue;
  }

  public void setValue(String value) {
    m_rlValue = value;
  }
}
