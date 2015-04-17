// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.resources;

import com.recursiveloop.cms.exceptions.UnmarshalException;
import com.recursiveloop.cms.exceptions.NoSuchResourceException;
import javax.jcr.RepositoryException;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.servlet.ServletException;


@Path("/repository/types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RcTypes {
  /**
  * Retrieve the full list of type names
  */
  @GET
  public JsonObject getNames();

  /**
  * Retrieve type by name
  */
  @GET
  @Path("{name}")
  public JsonObject getType(@PathParam("name") String name);

  /**
  * Insert new type
  */
  @POST
  public Response insertType(JsonObject type) throws UnmarshalException, RepositoryException;

  /**
  * Delete type by name
  */
  @DELETE
  @Path("{name}")
  public Response deleteType(@PathParam("name") String name) throws RepositoryException, NoSuchResourceException;

  /**
  * Update a field on specified type
  */
  @PUT
  @Path("{name}/fields/{field}")
  public Response updateField(@PathParam("name") String name, @PathParam("field") String field, JsonObject json)
    throws RepositoryException, NoSuchResourceException;

  /**
  * Insert a new field on specified type
  */
  @POST
  @Path("{name}/fields")
  public Response insertField(@PathParam("name") String name, JsonObject json) throws RepositoryException, NoSuchResourceException;

  /**
  * Delete a new field on specified type
  */
  @DELETE
  @Path("{name}/fields/{field}")
  public Response deleteField(@PathParam("name") String name, @PathParam("field") String field) throws RepositoryException, NoSuchResourceException;
}
