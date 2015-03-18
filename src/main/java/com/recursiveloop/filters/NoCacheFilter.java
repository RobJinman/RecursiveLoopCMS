// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.filters;

import javax.faces.application.ResourceHandler;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;
import java.util.logging.Level;


public class NoCacheFilter implements Filter {
  private final static Logger m_logger = Logger.getLogger(NoCacheFilter.class.getName());

  @Override
  public void init(FilterConfig config) throws ServletException {}

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest)req;
    HttpServletResponse response = (HttpServletResponse)res;

    if (!request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) { // Skip CSS/JS/Images/etc
      response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
      response.setHeader("Pragma", "no-cache"); // HTTP 1.0
      response.setDateHeader("Expires", 0); // Proxies
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {}
}
