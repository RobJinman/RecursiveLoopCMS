// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms;


public class JcrProperty {
  public int type;    // Values defined in javax.jcr.PropertyType
  public Object data;

  JcrProperty(int type, Object data) {
    this.type = type;
    this.data = data;
  }
}
