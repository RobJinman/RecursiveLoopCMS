// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.resources;

import com.recursiveloop.cms.models.MCredentials;
import com.recursiveloop.cms.models.MSession;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;


@Path("/session")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RcSession {
  /**
  * For CORS compliance
  */
  @OPTIONS
  public Response doNothing();

  /**
  * Begin a new authenticated session
  */
  @POST
  public MSession create(@Context HttpServletRequest request, MCredentials credentials) throws Exception;

  /**
  * Retrieve the current session
  */
  @GET
  MSession get(@Context HttpServletRequest request) throws Exception;

  /**
  * Delete the current session
  */
  @DELETE
  public Response delete(@Context HttpServletRequest request) throws Exception;
}
