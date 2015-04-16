// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.parse;

import com.recursiveloop.cms.exceptions.InvalidItemException;
import com.recursiveloop.cms.jcrmodel.RlJcrParserParam;
import com.recursiveloop.cms.JcrDao;
import javax.xml.bind.DatatypeConverter;
import javax.inject.Inject;
import javax.jcr.Binary;
import java.io.ByteArrayInputStream;
import java.util.List;


public class BinaryParser extends FieldParser {
  @Inject
  JcrDao m_dao;

  @Override
  public Object parse(String str, List<RlJcrParserParam> paramList) throws InvalidItemException {
    Binary bin = null;

    try {
      byte[] bytes = DatatypeConverter.parseBase64Binary(str);
      ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

      bin = m_dao.createBinary(stream);
    }
    catch (Exception ex) {
      throw new InvalidItemException("Error parsing binary string", ex);
    }

    return bin;
  }

  @Override
  public String stringify(Object obj, List<RlJcrParserParam> paramList) throws InvalidItemException {
    String str = "";

    try {
      Binary bin = (Binary)obj;
      int sz = (int)bin.getSize();
      byte[] bytes = new byte[sz];

      for (int i = 0, n = 0; i != -1; i = bin.read(bytes, n)) {
        n += i;
      }

      str = DatatypeConverter.printBase64Binary(bytes);
    }
    catch (Exception ex) {
      throw new InvalidItemException("Error stringifying binary object", ex);
    }

    return str;
  }
}
