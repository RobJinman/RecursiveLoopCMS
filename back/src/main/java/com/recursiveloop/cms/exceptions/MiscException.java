// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.exceptions;

import javax.ws.rs.core.Response.StatusType;
import java.io.Serializable;


public class MiscException extends Exception implements Serializable {
  private static final long serialVersionUID = 1L;

  StatusType m_code;

  public MiscException(StatusType code) {
    super();
    m_code = code;
  }

  public MiscException(StatusType code, String msg) {
    super(msg);
    m_code = code;
  }

  public MiscException(StatusType code, String msg, Exception e) {
    super(msg, e);
    m_code = code;
  }

  public StatusType getStatusCode() {
    return m_code;
  }
}
