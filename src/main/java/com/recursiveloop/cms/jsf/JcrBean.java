package com.recursiveloop.cms.jsf;

import com.recursiveloop.cms.JcrDao;
import com.recursiveloop.cms.ShallowItem;
import com.recursiveloop.cms.StringItem;
import com.recursiveloop.cms.ItemType;

import javax.inject.Named;
import javax.inject.Inject;
import javax.enterprise.context.RequestScoped;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.logging.Level;


@Named(value = "jcrBean")
@RequestScoped
public class JcrBean {
  private static final Logger m_logger = Logger.getLogger(JcrBean.class.getName());

  @Inject
  JcrDao m_dao;

  public ShallowItem getShallowItemTree() {
    return m_dao.getShallowItemTree();
  }

  public ShallowItem getShallowItem(String path) {
    return m_dao.getShallowItem(path);
  }

  public StringItem getStringItem(String path) {
    try {
      return m_dao.getStringItem(path);
    }
    catch (Exception ex) {
      m_logger.log(Level.WARNING, "Error retrieving item from repository", ex);
    }

    return null;
  }

  public Collection<String> getTypeList() {
    return m_dao.getTypeList();
  }

  public ItemType getType(String name) {
    return m_dao.getType(name);
  }
}
