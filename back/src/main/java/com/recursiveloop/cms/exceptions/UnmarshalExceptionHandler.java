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
public class UnmarshalExceptionHandler implements ExceptionMapper<UnmarshalException> {
  @Override
  public Response toResponse(UnmarshalException ex) {
    return Response.status(ExtStatusCode.UNPROCESSABLE_ENTITY).build();
  }
}
