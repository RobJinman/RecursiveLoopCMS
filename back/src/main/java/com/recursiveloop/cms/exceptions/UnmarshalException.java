// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.exceptions;

import java.io.Serializable;


public class UnmarshalException extends Exception implements Serializable {
  private static final long serialVersionUID = 1L;

  public UnmarshalException() {
    super();
  }

  public UnmarshalException(String msg) {
    super(msg);
  }

  public UnmarshalException(String msg, Exception e) {
    super(msg, e);
  }
}
