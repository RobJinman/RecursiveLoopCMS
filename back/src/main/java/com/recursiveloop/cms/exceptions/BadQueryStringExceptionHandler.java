// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.json.Json;
import javax.json.JsonObjectBuilder;


@Provider
public class BadQueryStringExceptionHandler implements ExceptionMapper<BadQueryStringException> {
  @Override
  public Response toResponse(BadQueryStringException ex) {
    JsonObjectBuilder json = Json.createObjectBuilder();
    json.add("queryString", ex.getQueryString())
      .add("message", ex.getMessage());

    return Response.status(Status.BAD_REQUEST).entity(json.build()).build();
  }
}
