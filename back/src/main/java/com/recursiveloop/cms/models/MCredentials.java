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
import java.util.regex.Pattern;


@XmlAccessorType(XmlAccessType.FIELD)
public class MCredentials {
  private static final int MIN_PASSWORD_LEN = 8;
  private static final int MAX_PASSWORD_LEN = 32;
  private static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9_]{4,16}");

  @XmlElement(name = "username")
  private String m_username;

  @XmlElement(name = "password")
  private String m_password;

  public MCredentials() {
    m_username = "";
    m_password = "";
  }

  public void setUsername(String username) {
    m_username = username;
  }

  public String getUsername() {
    return m_username;
  }

  public void setPassword(String password) {
    m_password = password;
  }

  public String getPassword() {
    return m_password;
  }

  public boolean areValid() {
    return m_username != null
      && USERNAME_PATTERN.matcher(m_username).matches()
      && m_password != null
      && m_password.length() >= MIN_PASSWORD_LEN
      && m_password.length() <= MAX_PASSWORD_LEN;
  }
}
