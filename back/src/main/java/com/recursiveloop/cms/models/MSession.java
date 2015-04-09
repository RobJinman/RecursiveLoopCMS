// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import java.util.List;
import java.util.ArrayList;


@XmlAccessorType(XmlAccessType.FIELD)
public class MSession {
  @XmlElement(name = "username")
  private String m_username;

  @XmlElement(name = "roles")
  private List<String> m_roles;

  public MSession() {
    m_username = null;
    m_roles = new ArrayList<String>();
  }

  public MSession(String username, List<String> roles) {
    m_username = username;
    m_roles = roles;
  }

  public void setUsername(String username) {
    m_username = username;
  }

  public String getUsername() {
    return m_username;
  }

  public void setRoles(List<String> roles) {
    m_roles = roles;
  }

  public List<String> getRoles() {
    return m_roles;
  }
}
