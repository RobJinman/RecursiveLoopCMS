package com.recursiveloop.cms.exceptions;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.Response.Status.Family;


public enum ExtStatusCode implements StatusType {
  UNPROCESSABLE_ENTITY {
    @Override
    public int getStatusCode() {
      return 422;
    }

    @Override
    public String getReasonPhrase() {
      return "Entity contains invalid data";
    }

    @Override
    public Family getFamily() {
      return Family.CLIENT_ERROR;
    }
  }
}
