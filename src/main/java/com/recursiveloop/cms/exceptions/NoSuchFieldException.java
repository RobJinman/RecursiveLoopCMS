// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.exceptions;

import java.io.Serializable;


public class NoSuchFieldException extends Exception implements Serializable {
  private static final long serialVersionUID = 1L;
  private String m_typeName;
  private String m_fieldName;

  public NoSuchFieldException(String typeName, String fieldName) {
    super();
    m_typeName = typeName;
    m_fieldName = fieldName;
  }

  public NoSuchFieldException(String msg, String typeName, String fieldName) {
    super(msg);
    m_typeName = typeName;
    m_fieldName = fieldName;
  }

  public NoSuchFieldException(String msg, Exception e, String typeName, String fieldName) {
    super(msg, e);
    m_typeName = typeName;
    m_fieldName = fieldName;
  }

  public String getTypeName() {
    return m_typeName;
  }

  public String getFieldName() {
    return m_fieldName;
  }
}
