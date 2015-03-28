// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms;

import java.io.Serializable;


public class InvalidItemException extends Exception implements Serializable {
  private static final long serialVersionUID = 1L;

  public InvalidItemException() {
    super();
  }

  public InvalidItemException(String msg) {
    super(msg);
  }

  public InvalidItemException(String msg, Exception e) {
    super(msg, e);
  }
}
