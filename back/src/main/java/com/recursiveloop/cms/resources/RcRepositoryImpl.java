// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.resources;

import com.recursiveloop.cms.exceptions.BadQueryStringException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.jcr.PropertyType;
import java.util.logging.Logger;
import java.util.logging.Level;


public class RcRepositoryImpl implements RcRepository {
  private final static Logger m_logger = Logger.getLogger(RcRepositoryImpl.class.getName());

  @Override
  public JsonObject getJcrTypeNames(HttpServletRequest request) throws BadQueryStringException {
    String key = request.getParameter("key"); // Expects one of 'name' or 'value'
    if (key == null) {
      key = "name";
    }

    JsonObjectBuilder obj = Json.createObjectBuilder();

    if (key.equals("name")) {
      return obj.add("binary", PropertyType.BINARY)
        .add("boolean", PropertyType.BOOLEAN)
        .add("date", PropertyType.DATE)
        .add("double", PropertyType.DOUBLE)
        .add("long", PropertyType.LONG)
        .add("string", PropertyType.STRING).build();
    }
    else if (key.equals("value")) {
      return obj.add(Integer.toString(PropertyType.BINARY), "binary")
        .add(Integer.toString(PropertyType.BOOLEAN), "boolean")
        .add(Integer.toString(PropertyType.DATE), "date")
        .add(Integer.toString(PropertyType.DOUBLE), "double")
        .add(Integer.toString(PropertyType.LONG), "long")
        .add(Integer.toString(PropertyType.STRING), "string").build();
    }
    else {
      throw new BadQueryStringException(request.getQueryString());
    }
  }
}
