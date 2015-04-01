// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.resources;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.servlet.ServletException;


@Path("/repository/type")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RcType {
  @POST
  public Response insertType(JsonObject json) throws ServletException;

  @PUT
  public Response updateType(JsonObject json) throws ServletException;

  @DELETE
  @Path("{name}")
  public Response deleteType(@PathParam("name") String name) throws ServletException;

  @DELETE
  @Path("{name}/field/{field}")
  public Response deleteField(@PathParam("name") String name, @PathParam("field") String field) throws ServletException;
}
