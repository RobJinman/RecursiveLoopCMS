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
