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


@Path("/repository/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RcItem {
  @POST
  public Response insertItem(JsonObject json) throws ServletException;

  @PUT
  public Response updateItem(JsonObject json) throws ServletException;

  @DELETE
  @Path("{path}")
  public Response deleteItem(@PathParam("path") String path) throws ServletException;
}
