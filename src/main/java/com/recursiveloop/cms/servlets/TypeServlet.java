// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.servlets;

import com.recursiveloop.cms.JcrDao;
import com.recursiveloop.cms.ItemType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.inject.Inject;
import java.io.InputStream;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;


@WebServlet(name = "TypeServlet", urlPatterns = {"/ajax/repository/type"})
public class TypeServlet extends HttpServlet {
  private static final Logger m_logger = Logger.getLogger(TypeServlet.class.getName());

  @Inject
  JcrDao m_dao;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

  }
}
