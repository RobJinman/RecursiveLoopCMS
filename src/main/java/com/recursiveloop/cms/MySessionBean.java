package com.recursiveloop.cms;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;


@Named(value = "mySessionBean")
@SessionScoped
public class MySessionBean implements Serializable {
  private String m_message = "Default message";

  public void setMessage(String msg) {
    m_message = msg;
  }

  public String getMessage() {
    return m_message;
  }
}
