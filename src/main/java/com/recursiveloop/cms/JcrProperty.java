package com.recursiveloop.cms;


public class JcrProperty {
  public int type;    // Values defined in javax.jcr.PropertyType
  public Object data;

  JcrProperty(int type, Object data) {
    this.type = type;
    this.data = data;
  }
}
