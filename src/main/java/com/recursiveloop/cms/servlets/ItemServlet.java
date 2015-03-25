package com.recursiveloop.cms.servlets;

import com.recursiveloop.cms.JcrDao;
import com.recursiveloop.cms.StringItem;
import com.recursiveloop.cms.NoSuchTypeException;
import com.recursiveloop.cms.NoSuchItemException;
import com.recursiveloop.cms.InvalidItemException;

import javax.json.JsonReader;
import javax.json.JsonObject;
import javax.json.Json;
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


@WebServlet(name = "ItemServlet", urlPatterns = {"/ajax/repository/item"})
public class ItemServlet extends HttpServlet {
  private static final Logger m_logger = Logger.getLogger(ItemServlet.class.getName());

  @Inject
  JcrDao m_dao;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    String action = request.getParameter("action");
    if (action == null) {
      response.setStatus(response.SC_BAD_REQUEST);
      return;
    }

    if (action.equalsIgnoreCase("update")) {
      update(request, response);
    }
    else if (action.equalsIgnoreCase("insert")) {
      insert(request, response);
    }
    else {
      response.setStatus(response.SC_BAD_REQUEST);
    }
  }

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    String path = request.getParameter("path");

    try {
      m_dao.deleteItem(path);
    }
    catch (NoSuchItemException ex) {
      m_logger.log(Level.WARNING, "Cannot delete item at '" + path + "'; item does not exist");
      response.setStatus(response.SC_CONFLICT);
      return;
    }
    catch (Exception ex) {
      throw new ServletException("Error deleting item", ex);
    }
  }

  private void update(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    String itemPath = "[UNKNOWN]";
    String itemType = "[UNKNOWN]";

    try (
      InputStream is = request.getInputStream();
      JsonReader rdr = Json.createReader(is)) {

      JsonObject json = rdr.readObject();
      StringItem item = new StringItem(json);

      itemPath = item.getPath();
      itemType = item.getTypeName();

      m_dao.updateItem(item);
    }
    catch (NoSuchTypeException ex) {
      m_logger.log(Level.WARNING, "Cannot update item of type '" + itemType + "'; no such type");
      response.setStatus(response.SC_CONFLICT);
      return;
    }
    catch (NoSuchItemException ex) {
      m_logger.log(Level.WARNING, "Cannot update item at '" + itemPath + "'; item does not exist");
      response.setStatus(response.SC_CONFLICT);
      return;
    }
    catch (InvalidItemException ex) {
      m_logger.log(Level.WARNING, "Failed to update item; received invalid item");
      response.setStatus(response.SC_BAD_REQUEST);
      return;
    }
    catch (Exception ex) {
      throw new ServletException("Error inserting new item", ex);
    }
  }

  private void insert(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    String itemType = "[UNKNOWN]";

    try (
      InputStream is = request.getInputStream();
      JsonReader rdr = Json.createReader(is)) {

      JsonObject json = rdr.readObject();
      StringItem item = new StringItem(json);

      itemType = item.getTypeName();

      m_dao.insertNewItem(item);
    }
    catch (NoSuchTypeException ex) {
      m_logger.log(Level.WARNING, "Cannot insert item of type '" + itemType + "'; no such type");
      response.setStatus(response.SC_CONFLICT);
      return;
    }
    catch (InvalidItemException ex) {
      m_logger.log(Level.WARNING, "Failed to insert item; received invalid item");
      response.setStatus(response.SC_BAD_REQUEST);
      return;
    }
    catch (Exception ex) {
      throw new ServletException("Error inserting new item", ex);
    }
  }
}
