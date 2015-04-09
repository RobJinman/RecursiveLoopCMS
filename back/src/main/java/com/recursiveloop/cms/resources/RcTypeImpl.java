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


public class RcTypeImpl implements RcType {
  private final static Logger m_logger = Logger.getLogger(RcTypeImpl.class.getName());

  @Inject
  JcrDao m_dao;

  @Override
  public Response doNothing() {
    return Response.ok().build();
  }

  @Override
  public JsonObject getNames() throws Exception {
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
  public JsonObject getType(String name) throws Exception {
    try {
      ItemType type = m_dao.getType(name);

      return type.toJson();
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error getting type '" + name + "'", ex);
      throw ex;
    }
  }

  @Override
  public Response insertType(JsonObject json) throws Exception {
    try {
      ItemType type = new ItemType(json);
      m_dao.insertNewType(type);
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error inserting type", ex);
      throw ex;
    }

    return Response.ok().build();
  }

  @Override
  public Response deleteType(String name) throws Exception {
    try {
      m_dao.deleteType(name);
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error deleting type", ex);
      throw ex;
    }

    return Response.ok().build();
  }

  @Override
  public Response updateField(String name, String field, JsonObject json) throws Exception {
    try {
      ItemType type = m_dao.getType(name);
      type.removeField(field);
      type.addField(json);

      m_dao.updateType(type);
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error updating field '" + field + "' of type '" + name + "'", ex);
      throw ex;
    }

    return Response.ok().build();
  }

  @Override
  public Response insertField(String name, JsonObject json) throws Exception {
    try {
      ItemType type = m_dao.getType(name);
      type.addField(json);

      m_dao.updateType(type);
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error inserting field of type '" + name + "'", ex);
      throw ex;
    }

    return Response.ok().build();
  }

  @Override
  public Response deleteField(String name, String field) throws Exception {
    try {
      m_dao.deleteField(name, field);
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error deleting field '" + field + "' of type '" + name + "'", ex);
      throw ex;
    }

    return Response.ok().build();
  }
}
