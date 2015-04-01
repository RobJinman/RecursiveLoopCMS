// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.jcrmodel;

import org.apache.jackrabbit.ocm.mapper.impl.annotation.Field;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Collection;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;
import java.util.List;
import java.util.ArrayList;


@Node(jcrType = "rlt:itemType")
public class RlJcrItemType {
  private String m_rlPath;
  @Collection(jcrName = "rl:fields", jcrType = "rlt:fieldTypeGroup", jcrElementName = "rl:field")
  public List<RlJcrFieldType> fields;

  public RlJcrItemType() {
    this.fields = new ArrayList<RlJcrFieldType>();
  }

  public RlJcrItemType(List<RlJcrFieldType> fields) {
    this.fields = fields;
  }

  public RlJcrItemType(RlJcrItemType cpy) {
    m_rlPath = cpy.m_rlPath;

    this.fields = new ArrayList<RlJcrFieldType>();
    for (RlJcrFieldType f : cpy.fields) {
      this.fields.add(new RlJcrFieldType(f));
    }
  }

  @Field(jcrName = "rl:path", path = true)
  public String getPath() {
    return m_rlPath;
  }

  public void setPath(String path) {
    m_rlPath = path;
  }

  public List<RlJcrFieldType> getFields() {
    return this.fields;
  }

  public void setFields(List<RlJcrFieldType> fields) {
    this.fields = new ArrayList<RlJcrFieldType>();

    for (RlJcrFieldType f : fields) {
      this.fields.add(new RlJcrFieldType(f));
    }
  }

  public void addField(RlJcrFieldType field) {
    if (this.fields == null) {
      this.fields = new ArrayList<RlJcrFieldType>();
    }

    this.fields.add(field);
  }
}
