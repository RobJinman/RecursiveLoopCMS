package com.recursiveloop.cms;

import java.io.Serializable;


public class NoSuchTypeException extends Exception implements Serializable {
  private static final long serialVersionUID = 1L;
  private String m_name;

  public NoSuchTypeException(String typeName) {
    super();
    m_name = typeName;
  }

  public NoSuchTypeException(String msg, String typeName) {
    super(msg);
    m_name = typeName;
  }

  public NoSuchTypeException(String msg, Exception e, String typeName) {
    super(msg, e);
    m_name = typeName;
  }

  public String getTypeName() {
    return m_name;
  }
}
