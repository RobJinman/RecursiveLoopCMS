// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.resources;

import com.recursiveloop.cms.JcrDao;
import com.recursiveloop.cms.ItemType;
import com.recursiveloop.cms.NoSuchTypeException;
import com.recursiveloop.cms.NoSuchFieldException;
import com.recursiveloop.cms.InvalidTypeException;
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


public class RcTypeImpl implements RcType {
  private final static Logger m_logger = Logger.getLogger(RcTypeImpl.class.getName());

  @Inject
  JcrDao m_dao;

  @Override
  public Response updateType(JsonObject json) throws ServletException {
    try {
      ItemType type = new ItemType(json);
      m_dao.updateType(type);
    }
    catch (NoSuchTypeException ex) {
      m_logger.log(Level.WARNING, "Cannot update item of type '" + ex.getTypeName() + "'; no such type");
      return Response.status(Status.CONFLICT).build();
    }
    catch (InvalidTypeException ex) {
      m_logger.log(Level.WARNING, "Failed to update type; received invalid type");
      return Response.status(Status.BAD_REQUEST).build();
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error updating type", ex);
      throw new ServletException("Error updating type", ex);
    }

    return Response.ok().build();
  }

  @Override
  public Response insertType(JsonObject json) throws ServletException {
    try {
      ItemType type = new ItemType(json);
      m_dao.insertNewType(type);
    }
    catch (InvalidTypeException ex) {
      m_logger.log(Level.WARNING, "Failed to insert type; received invalid type");
      return Response.status(Status.BAD_REQUEST).build();
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error inserting type", ex);
      throw new ServletException("Error inserting type", ex);
    }

    return Response.ok().build();
  }

  @Override
  public Response deleteType(String name) throws ServletException {
    try {
      m_dao.deleteType(name);
    }
    catch (NoSuchTypeException ex) {
      m_logger.log(Level.WARNING, "Cannot delete type '" + name + "'; type does not exist");
      return Response.status(Status.CONFLICT).build();
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error deleting type", ex);
      throw new ServletException("Error deleting type", ex);
    }

    return Response.ok().build();
  }

  @Override
  public Response deleteField(String name, String field) throws ServletException {
    try {
      m_dao.deleteField(name, field);
    }
    catch (NoSuchTypeException ex) {
      m_logger.log(Level.WARNING, "Cannot delete type '" + name + "'; type does not exist");
      return Response.status(Status.CONFLICT).build();
    }
    catch (NoSuchFieldException ex) {
      m_logger.log(Level.WARNING, "Cannot delete field '" + field + "' of type '" + name + "'; field does not exist");
      return Response.status(Status.CONFLICT).build();
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error deleting field", ex);
      throw new ServletException("Error deleting field", ex);
    }

    return Response.ok().build();
  }
}
