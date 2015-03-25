package com.recursiveloop.cms;

import javax.jcr.RepositoryException;
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

  public Item getItemTree() {
    return m_dao.getItemTree();
  }

  public Item getItem(String path) {
    return m_dao.getItem(path);
  }

  public Item fetchFullItem(String path) {
    return m_dao.fetchFullItem(path);
  }

  public Collection<String> getTypeList() {
    return m_dao.getTypeList();
  }

  public Type fetchType(String name) {
    return m_dao.fetchType(name);
  }
}
