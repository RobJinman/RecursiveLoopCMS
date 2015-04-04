// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.exceptions;

import java.io.Serializable;


public class NoSuchTypeException extends Exception implements Serializable {
  private static final long serialVersionUID = 1L;
  private String m_name;

  public NoSuchTypeException(String typeName) {
    super();
    m_name = typeName;
  }

  public NoSuchTypeException(String msg, String typeName) {
    super(msg);
    m_name = typeName;
  }

  public NoSuchTypeException(String msg, Exception e, String typeName) {
    super(msg, e);
    m_name = typeName;
  }

  public String getTypeName() {
    return m_name;
  }
}
