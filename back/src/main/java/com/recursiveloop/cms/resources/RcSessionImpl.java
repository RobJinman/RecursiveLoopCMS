// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.resources;

import com.recursiveloop.cms.models.MCredentials;
import com.recursiveloop.cms.models.MSession;
import com.recursiveloop.cms.exceptions.AuthenticationFailureException;
import com.recursiveloop.webcommon.annotations.Config;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.inject.Inject;
import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.security.Principal;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;


public class RcSessionImpl implements RcSession {
  private final static Logger m_logger = Logger.getLogger(RcSessionImpl.class.getName());

  @Inject
  @Config("authentication_roles")
  String m_roleList;

  private String[] m_roles;

  @PostConstruct
  public void init() {
    m_roles = m_roleList.split(",");
  }

  @Override
  public Response doNothing() {
    return Response.ok().build();
  }

  @Override
  public MSession create(HttpServletRequest request, MCredentials credentials) throws Exception {
    try {
      if (request.getUserPrincipal() != null) {
        delete(request);
      }

      request.getSession(true);
      request.login(credentials.getUsername(), credentials.getPassword());
    }
    catch (ServletException ex) {
      m_logger.log(Level.INFO, "Login failed", ex);
      throw new AuthenticationFailureException();
    }

    return getUserSession(request);
  }

  @Override
  public MSession get(HttpServletRequest request) throws Exception {
    return getUserSession(request);
  }

  @Override
  public Response delete(HttpServletRequest request) throws Exception {
    try {
      request.logout();
      request.getSession(true).invalidate();
    }
    catch (ServletException ex) {
      m_logger.log(Level.INFO, "Logout failed", ex);
      throw ex;
    }

    return Response.ok().build();
  }

  private MSession getUserSession(HttpServletRequest request) {
    Principal principal = request.getUserPrincipal();

    if (principal == null) {
      return new MSession();
    }

    List<String> roles = new ArrayList<String>();

    for (int i = 0; i < m_roles.length; ++i) {
      if (request.isUserInRole(m_roles[i])) {
        roles.add(m_roles[i]);
      }
    }

    return new MSession(principal.getName(), roles);
  }
}
