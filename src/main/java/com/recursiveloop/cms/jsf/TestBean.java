// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.jsf;

import com.recursiveloop.cms.ItemType;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;


@Named(value = "testBean")
@SessionScoped
public class TestBean implements Serializable {
  private ItemType m_type;

  @PostConstruct
  public void init() {
    m_type = new ItemType();
  }

  public ItemType getType() {
    return m_type;
  }
}
