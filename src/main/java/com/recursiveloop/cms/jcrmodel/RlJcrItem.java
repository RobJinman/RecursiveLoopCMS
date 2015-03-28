// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.jcrmodel;

import javax.jcr.RepositoryException;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Field;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;


/**
* This class represents the type rlt:item. Inheriting from this class will ensure
* conformity to the rlt:item type when inserted into the repository via OCM.
*/
@Node(jcrType = "rlt:item")
public abstract class RlJcrItem {
  private String m_rlPath;
  private String m_rlType;
  private int m_rlStatus;

  public RlJcrItem() {
    m_rlPath = null;
    m_rlType = "folder";
    m_rlStatus = 0;
  }

  public RlJcrItem(String type) {
    m_rlType = type;
  }

  public RlJcrItem(RlJcrItem cpy) {
    m_rlPath = cpy.m_rlPath;
    m_rlType = cpy.m_rlType;
    m_rlStatus = cpy.m_rlStatus;
  }

  @Field(jcrName = "rl:path", path = true)
  public String getPath() {
    return m_rlPath;
  }

  public void setPath(String path) {
    m_rlPath = path;
  }

  @Field(jcrName = "rl:type")
  public String getTypeName() {
    return m_rlType;
  }

  public void setTypeName(String type) {
    m_rlType = type;
  }

  @Field(jcrName = "rl:status")
  public int getStatus() {
    return m_rlStatus;
  }

  public void setStatus(int status) {
    m_rlStatus = status;
  }

  public void write(javax.jcr.Node node) throws RepositoryException {
    node.setProperty("rl:path", m_rlPath);
    node.setProperty("rl:type", m_rlType);
    node.setProperty("rl:status", m_rlStatus);
  }
}
