// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.resources;

import com.recursiveloop.cms.JcrDao;
import com.recursiveloop.cms.ShallowItem;
import com.recursiveloop.cms.StringItem;
import javax.json.JsonObject;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.ByteArrayInputStream;
import java.util.logging.Logger;
import java.util.logging.Level;


public class RcItemTreeImpl implements RcItemTree {
  private final static Logger m_logger = Logger.getLogger(RcItemTreeImpl.class.getName());

  @Inject
  JcrDao m_dao;

  @Override
  public ShallowItem get() throws Exception {
    return getSubtree("/rl:items");
  }

  @Override
  public ShallowItem getSubtree(String path) throws Exception {
    return m_dao.getShallowItem(path);
  }

  @Override
  public StringItem getItem(String path) throws Exception {
    return m_dao.getStringItem(path);
  }

  @Override
  public Response insertItem(JsonObject json) throws Exception {
    try {
      StringItem item = new StringItem(json);
      m_dao.insertNewItem(item);
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error inserting new item", ex);
      throw ex;
    }

    return Response.ok().build();
  }

  @Override
  public Response updateItem(JsonObject json) throws Exception {
    try {
      StringItem item = new StringItem(json);
      m_dao.updateItem(item);
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error updating item", ex);
      throw ex;
    }

    return Response.ok().build();
  }

  @Override
  public Response deleteItem(String path) throws Exception {
    try {
      m_dao.deleteItem(path);
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error deleting item", ex);
      throw ex;
    }

    return Response.ok().build();
  }
}
