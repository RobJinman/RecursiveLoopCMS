package com.recursiveloop.cms;

import java.io.Serializable;


public class NoSuchItemException extends Exception implements Serializable {
  private static final long serialVersionUID = 1L;

  public NoSuchItemException() {
    super();
  }

  public NoSuchItemException(String msg) {
    super(msg);
  }

  public NoSuchItemException(String msg, Exception e) {
    super(msg, e);
  }
}
