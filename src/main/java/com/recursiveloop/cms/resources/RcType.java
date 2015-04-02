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
import javax.ws.rs.GET;
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
  public Response insertType(JsonObject json) throws Exception;

  @GET
  @Path("{name}")
  public JsonObject getType(@PathParam("name") String name) throws Exception;

  @DELETE
  @Path("{name}")
  public Response deleteType(@PathParam("name") String name) throws Exception;

  @PUT
  @Path("{name}/field/{field}")
  public Response updateField(@PathParam("name") String name, @PathParam("field") String field, JsonObject json) throws Exception;

  @POST
  @Path("{name}/field")
  public Response insertField(@PathParam("name") String name, JsonObject json) throws Exception;

  @DELETE
  @Path("{name}/field/{field}")
  public Response deleteField(@PathParam("name") String name, @PathParam("field") String field) throws Exception;
}
