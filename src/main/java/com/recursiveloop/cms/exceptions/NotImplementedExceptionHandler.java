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


@Provider
public class NotImplementedExceptionHandler implements ExceptionMapper<NotImplementedException> {
  @Override
  public Response toResponse(NotImplementedException ex) {
    return Response.status(Status.NOT_IMPLEMENTED).build();
  }
}
