// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.jsf;

import com.recursiveloop.cms.JcrDao;
import com.recursiveloop.cms.ItemType;
import com.recursiveloop.cms.jcrmodel.RlJcrFieldType;
import com.recursiveloop.cms.exceptions.NoSuchTypeException;
import com.recursiveloop.cms.exceptions.InvalidTypeException;
import javax.jcr.RepositoryException;
import javax.inject.Named;
import javax.inject.Inject;
import javax.enterprise.context.SessionScoped;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.Serializable;


@Named(value = "itemTypeBean")
@SessionScoped
public class ItemTypeBean implements Serializable {
  private static final Logger m_logger = Logger.getLogger(ItemTypeBean.class.getName());

  @Inject
  JcrDao m_dao;

  private boolean m_isNew;
  private ItemType m_type;

  public ItemType getType(String name) {
    ItemType type = m_dao.getType(name);

    m_type = new ItemType(type);
    m_isNew = false;

    return m_type;
  }

  public ItemType getNewType() {
    m_type = new ItemType();
    m_isNew = true;

    return m_type;
  }

  public void commitChanges()
    throws RepositoryException, NoSuchTypeException, InvalidTypeException {

    if (m_isNew) {
      m_dao.insertNewType(m_type);
    }
    else {
      m_dao.updateType(m_type);
    }
  }
}
