// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.resources;

import com.recursiveloop.cms.exceptions.UnmarshalException;
import com.recursiveloop.cms.exceptions.NoSuchResourceException;
import com.recursiveloop.cms.exceptions.NoSuchItemException;
import com.recursiveloop.cms.exceptions.NoSuchTypeException;
import com.recursiveloop.cms.exceptions.ReadException;
import com.recursiveloop.cms.exceptions.WriteException;
import com.recursiveloop.cms.exceptions.ParseException;
import com.recursiveloop.cms.exceptions.StringifyException;
import com.recursiveloop.cms.exceptions.MiscException;
import com.recursiveloop.cms.exceptions.ExtStatusCode;
import com.recursiveloop.cms.JcrDao;
import com.recursiveloop.cms.ShallowItem;
import com.recursiveloop.cms.StringItem;
import javax.jcr.RepositoryException;
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
  public ShallowItem get() {
    return getSubtree("/rl:items");
  }

  @Override
  public ShallowItem getSubtree(String path) {
    try {
      return m_dao.getShallowItem(path);
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error retrieving subtree");
      throw ex;
    }
  }

  @Override
  public StringItem getItem(String path) throws
    RepositoryException, NoSuchResourceException, MiscException,
    ReadException, StringifyException {

    try {
      return m_dao.getStringItem(path);
    }
    catch (NoSuchItemException ex) {
      m_logger.log(Level.WARNING, "Error retrieving item");
      throw new NoSuchResourceException();
    }
    catch (NoSuchTypeException ex) {
      m_logger.log(Level.WARNING, "Error retrieving item");
      throw new MiscException(Status.CONFLICT, "Error retrieving item", ex);
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error retrieving item");
      throw ex;
    }
  }

  @Override
  public Response insertItem(JsonObject json) throws
    UnmarshalException, RepositoryException, MiscException,
    ParseException, WriteException {

    try {
      StringItem item = new StringItem(json);
      m_dao.insertNewItem(item);

      return Response.ok().build();
    }
    catch (NoSuchTypeException ex) {
      m_logger.log(Level.WARNING, "Error inserting new item");
      throw new MiscException(ExtStatusCode.UNPROCESSABLE_ENTITY);
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error inserting new item");
      throw ex;
    }
  }

  @Override
  public Response updateItem(JsonObject json) throws
    UnmarshalException, NoSuchResourceException, RepositoryException,
    MiscException, ParseException, WriteException {

    try {
      StringItem item = new StringItem(json);
      m_dao.updateItem(item);

      return Response.ok().build();
    }
    catch (NoSuchItemException ex) {
      m_logger.log(Level.WARNING, "Error updating item");
      throw new NoSuchResourceException();
    }
    catch (NoSuchTypeException ex) {
      m_logger.log(Level.WARNING, "Error updating item");
      throw new MiscException(ExtStatusCode.UNPROCESSABLE_ENTITY);
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error updating item");
      throw ex;
    }
  }

  @Override
  public Response deleteItem(String path) throws
    NoSuchResourceException, RepositoryException {

    try {
      m_dao.deleteItem(path);

      return Response.ok().build();
    }
    catch (NoSuchItemException ex) {
      m_logger.log(Level.WARNING, "Error deleting item");
      throw new NoSuchResourceException();
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error deleting item");
      throw ex;
    }
  }
}
