// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.resources;

import com.recursiveloop.cms.jcrmodel.RlJcrFieldType;
import com.recursiveloop.cms.JcrDao;
import com.recursiveloop.cms.ItemType;
import com.recursiveloop.cms.exceptions.NoSuchFieldException;
import com.recursiveloop.cms.exceptions.NoSuchTypeException;
import com.recursiveloop.cms.exceptions.NoSuchResourceException;
import com.recursiveloop.cms.exceptions.UnmarshalException;
import javax.jcr.RepositoryException;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonObject;
import javax.servlet.annotation.WebServlet;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.logging.Level;


public class RcTypesImpl implements RcTypes {
  private final static Logger m_logger = Logger.getLogger(RcTypesImpl.class.getName());

  @Inject
  JcrDao m_dao;

  @Override
  public JsonObject getNames() {
    JsonObjectBuilder obj = Json.createObjectBuilder();
    JsonArrayBuilder arr = Json.createArrayBuilder();

    Collection<String> names = m_dao.getTypeList();
    for (String name : names) {
      arr.add(name);
    }

    obj.add("typeNames", arr);

    return obj.build();
  }

  @Override
  public JsonObject getType(String name) {
    try {
      ItemType type = m_dao.getType(name);

      return type.toJson();
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error retrieving type");
      throw ex;
    }
  }

  @Override
  public Response insertType(JsonObject json) throws
    UnmarshalException, RepositoryException {

    try {
      ItemType type = new ItemType(json);
      m_dao.insertNewType(type);

      return Response.ok().build();
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error inserting type");
      throw ex;
    }
  }

  @Override
  public Response deleteType(String name) throws
    RepositoryException, NoSuchResourceException {

    try {
      m_dao.deleteType(name);

      return Response.ok().build();
    }
    catch (NoSuchTypeException ex) {
      m_logger.log(Level.WARNING, "Error deleting type");
      throw new NoSuchResourceException();
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error deleting type");
      throw ex;
    }
  }

  @Override
  public Response updateField(String name, String field, JsonObject json) throws
    RepositoryException, NoSuchResourceException {

    try {
      ItemType type = m_dao.getType(name);
      type.removeField(field);
      type.addField(json);

      m_dao.updateType(type);

      return Response.ok().build();
    }
    catch (NoSuchTypeException ex) {
      m_logger.log(Level.WARNING, "Error updating field");
      throw new NoSuchResourceException();
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error updating field");
      throw ex;
    }
  }

  @Override
  public Response insertField(String name, JsonObject json) throws
    RepositoryException, NoSuchResourceException {

    try {
      ItemType type = m_dao.getType(name);
      type.addField(json);

      m_dao.updateType(type);

      return Response.ok().build();
    }
    catch (NoSuchTypeException ex) {
      m_logger.log(Level.WARNING, "Error inserting field");
      throw new NoSuchResourceException();
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error inserting field");
      throw ex;
    }
  }

  @Override
  public Response deleteField(String name, String field) throws
    RepositoryException, NoSuchResourceException {

    try {
      m_dao.deleteField(name, field);

      return Response.ok().build();
    }
    catch (NoSuchTypeException | NoSuchFieldException ex) {
      m_logger.log(Level.WARNING, "Error deleting field");
      throw new NoSuchResourceException();
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error deleting field");
      throw ex;
    }
  }
}
