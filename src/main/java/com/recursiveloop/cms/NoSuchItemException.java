// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms;

import java.io.Serializable;


public class NoSuchItemException extends Exception implements Serializable {
  private static final long serialVersionUID = 1L;
  private String m_path;

  public NoSuchItemException(String path) {
    super();
    m_path = path;
  }

  public NoSuchItemException(String msg, String path) {
    super(msg);
    m_path = path;
  }

  public NoSuchItemException(String msg, Exception e, String path) {
    super(msg, e);
    m_path = path;
  }

  public String getPath() {
    return m_path;
  }
}
