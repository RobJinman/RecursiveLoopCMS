// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.exceptions;

import java.io.Serializable;


public class StringifyException extends Exception implements Serializable {
  private static final long serialVersionUID = 1L;

  public StringifyException() {
    super();
  }

  public StringifyException(String msg) {
    super(msg);
  }

  public StringifyException(String msg, Exception e) {
    super(msg, e);
  }
}
