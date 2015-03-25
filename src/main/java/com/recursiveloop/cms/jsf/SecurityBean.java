package com.recursiveloop.cms.jsf;

import com.recursiveloop.webcommon.annotations.Config;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import javax.enterprise.context.SessionScoped;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.inject.Named;
import javax.inject.Inject;
import javax.annotation.PostConstruct;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;
import java.io.Serializable;


@Named(value = "securityBean")
@SessionScoped
public class SecurityBean implements Serializable {
  private static final Logger m_logger = Logger.getLogger(SecurityBean.class.getName());

  private String m_username = "";
  private String m_password = "";

  @Inject @Config("homePage")
  String m_homePage;

  private String m_originalUrl = null;

  @PostConstruct
  public void init() {
    m_originalUrl = (String)getExternalContext().getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

    if (m_originalUrl == null) {
      m_originalUrl = m_homePage;
    }
  }

  public void setUsername(String username) {
    m_username = username;
  }

  public String getUsername() {
    return m_username;
  }

  public void setPassword(String password) {
    m_password = password;
  }

  public String getPassword() {
    return m_password;
  }

  public void logout() throws IOException {
    FacesContext context = FacesContext.getCurrentInstance();

    try {
      getHttpServletRequest().logout();
      getHttpServletRequest().getSession().invalidate();

      getExternalContext().redirect(context.getViewRoot().getViewId());
    }
    catch (ServletException ex) {
      m_logger.log(Level.INFO, "Logout failed", ex);
      context.addMessage(null, new FacesMessage("Logout failed"));
    }
  }

  public void login() throws IOException {
    try {
      if (isLoggedIn()) {
        logout();
      }

      getHttpServletRequest().login(m_username, m_password);
      getExternalContext().redirect(m_originalUrl);
    }
    catch (ServletException ex) {
      m_logger.log(Level.INFO, "Login failed", ex);
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login failed"));
    }
  }

  public boolean isLoggedIn() {
    return getHttpServletRequest().getUserPrincipal() != null;
  }

  private ExternalContext getExternalContext() {
    FacesContext context = FacesContext.getCurrentInstance();
    return context.getExternalContext();
  }

  private HttpServletRequest getHttpServletRequest() {
    return (HttpServletRequest)getExternalContext().getRequest();
  }
}
