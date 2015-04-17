// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.exceptions;

import java.io.Serializable;


public class BadQueryStringException extends Exception implements Serializable {
  private static final long serialVersionUID = 1L;

  private String m_queryString;

  public BadQueryStringException(String queryString) {
    m_queryString = queryString;
  }

  public BadQueryStringException(String queryString, String msg) {
    super(msg);
    m_queryString = queryString;
  }

  public String getQueryString() {
    return m_queryString;
  }
}
