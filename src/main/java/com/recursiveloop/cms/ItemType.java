// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms;

import com.recursiveloop.cms.jcrmodel.RlJcrItemType;
import com.recursiveloop.cms.jcrmodel.RlJcrFieldType;
import java.util.List;


public class ItemType {
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
    return m_type.getPath();
  }

  public List<RlJcrFieldType> getFields() {
    return m_type.getFields();
  }
}
