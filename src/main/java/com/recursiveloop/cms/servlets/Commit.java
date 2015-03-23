package com.recursiveloop.cms.servlets;

import com.recursiveloop.cms.JcrDao;
import com.recursiveloop.cms.Item;

import javax.json.JsonReader;
import javax.json.JsonObject;
import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.inject.Inject;
import java.io.InputStream;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;


public class Commit extends HttpServlet {
  private static final Logger m_logger = Logger.getLogger(Commit.class.getName());

  @Inject
  JcrDao m_dao;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    try (
      InputStream is = request.getInputStream();
      JsonReader rdr = Json.createReader(is)) {

      JsonObject root = rdr.readObject();
      Item item = new Item(root);

      m_dao.commitItem(item);
    }

    response.setStatus(response.SC_OK);
  }
}
