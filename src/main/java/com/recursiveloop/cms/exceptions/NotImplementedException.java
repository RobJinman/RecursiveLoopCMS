// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.exceptions;

import java.io.Serializable;


public class NotImplementedException extends UnsupportedOperationException implements Serializable {
  private static final long serialVersionUID = 1L;
  private String m_class;
  private String m_method;

  public NotImplementedException(String className, String methodName) {
    super();
    m_class = className;
    m_method = methodName;
  }

  public NotImplementedException(String msg, String className, String methodName) {
    super(msg);
    m_class = className;
    m_method = methodName;
  }

  public NotImplementedException(String msg, Exception e, String className, String methodName) {
    super(msg, e);
    m_class = className;
    m_method = methodName;
  }

  public String getClassName() {
    return m_class;
  }

  public String getMethodName() {
    return m_method;
  }
}
