package com.recursiveloop.cms.models;

import javax.ws.rs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;


public class MUploadForm {
  public MUploadForm() {}

  private byte[] m_fileData;
  private String m_fieldName;

  public String getFieldName() {
    return m_fieldName;
  }

  @FormParam("fieldName")
  public void setFieldName(String fieldName) {
    m_fieldName = fieldName;
  }

  public byte[] getFileData() {
    return m_fileData;
  }

  @FormParam("selectedFile")
  @PartType("application/octet-stream")
  public void setFileData(byte[] fileData) {
    m_fileData = fileData;
  }
}
