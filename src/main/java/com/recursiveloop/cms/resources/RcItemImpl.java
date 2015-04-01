// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.resources;

import com.recursiveloop.cms.JcrDao;
import com.recursiveloop.cms.StringItem;
import com.recursiveloop.cms.NoSuchTypeException;
import com.recursiveloop.cms.NoSuchItemException;
import com.recursiveloop.cms.InvalidItemException;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.logging.Logger;
import java.util.logging.Level;


public class RcItemImpl implements RcItem {
  private final static Logger m_logger = Logger.getLogger(RcItemImpl.class.getName());

  @Inject
  JcrDao m_dao;

  @Override
  public Response updateItem(JsonObject json) throws ServletException {
    try {
      StringItem item = new StringItem(json);
      m_dao.updateItem(item);
    }
    catch (NoSuchTypeException ex) {
      m_logger.log(Level.WARNING, "Cannot update item of type '" + ex.getTypeName() + "'; no such type");
      return Response.status(Status.CONFLICT).build();
    }
    catch (NoSuchItemException ex) {
      m_logger.log(Level.WARNING, "Cannot update item at '" + ex.getPath() + "'; item does not exist");
      return Response.status(Status.CONFLICT).build();
    }
    catch (InvalidItemException ex) {
      m_logger.log(Level.WARNING, "Failed to update item; received invalid item");
      return Response.status(Status.BAD_REQUEST).build();
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error updating item", ex);
      throw new ServletException("Error updating item", ex);
    }

    return Response.ok().build();
  }

  @Override
  public Response insertItem(JsonObject json) throws ServletException {
    try {
      StringItem item = new StringItem(json);
      m_dao.insertNewItem(item);
    }
    catch (NoSuchTypeException ex) {
      m_logger.log(Level.WARNING, "Cannot insert item of type '" + ex.getTypeName() + "'; no such type");
      return Response.status(Status.CONFLICT).build();
    }
    catch (InvalidItemException ex) {
      m_logger.log(Level.WARNING, "Failed to insert item; received invalid item");
      return Response.status(Status.BAD_REQUEST).build();
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error inserting new item", ex);
      throw new ServletException("Error inserting new item", ex);
    }

    return Response.ok().build();
  }

  @Override
  public Response deleteItem(String path) throws ServletException {
    try {
      m_dao.deleteItem(path);
    }
    catch (NoSuchItemException ex) {
      m_logger.log(Level.WARNING, "Cannot delete item at '" + path + "'; no such item");
      return Response.status(Status.CONFLICT).build();
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error deleting item", ex);
      throw new ServletException("Error deleting item", ex);
    }

    return Response.ok().build();
  }
}
