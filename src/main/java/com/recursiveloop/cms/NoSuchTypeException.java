package com.recursiveloop.cms;

import java.io.Serializable;


public class NoSuchTypeException extends Exception implements Serializable {
  private static final long serialVersionUID = 1L;

  public NoSuchTypeException() {
    super();
  }

  public NoSuchTypeException(String msg) {
    super(msg);
  }

  public NoSuchTypeException(String msg, Exception e) {
    super(msg, e);
  }
}
