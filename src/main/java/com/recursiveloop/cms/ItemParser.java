// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms;

import com.recursiveloop.cms.jcrmodel.RlJcrFieldType;
import com.recursiveloop.cms.jcrmodel.RlJcrParserParam;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jcr.PropertyType;
import java.util.List;


@Stateless
public class ItemParser {
  @Inject
  BinaryParser m_binaryParser;

  @Inject
  BooleanParser m_booleanParser;

  @Inject
  DateParser m_dateParser;

  @Inject
  DoubleParser m_doubleParser;

  @Inject
  LongParser m_longParser;

  public BinaryItem parse(StringItem strItem, ItemType itemType)
    throws InvalidItemException {

    BinaryItem binItem = new BinaryItem(strItem);

    for (RlJcrFieldType field : itemType.getFields()) {
      List<RlJcrParserParam> params = field.getParserParams();
      String name = field.getName();
      int type = field.getJcrType();
      String strVal = strItem.getProperty(name);
      Object data = null;

      switch (type) {
        case PropertyType.BINARY:
          data = m_binaryParser.parse(strVal, params);
          break;
        case PropertyType.BOOLEAN:
          data = m_booleanParser.parse(strVal, params);
          break;
        case PropertyType.DATE:
          data = m_dateParser.parse(strVal, params);
          break;
        case PropertyType.DOUBLE:
          data = m_doubleParser.parse(strVal, params);
          break;
        case PropertyType.LONG:
          data = m_longParser.parse(strVal, params);
          break;
        case PropertyType.STRING:
          data = strVal;
          break;
      }

      binItem.addProperty(name, new JcrProperty(type, data));
    }

    return binItem;
  }

  public StringItem stringify(BinaryItem binItem, ItemType itemType)
    throws InvalidItemException {

    StringItem strItem = new StringItem(binItem);

    for (RlJcrFieldType field : itemType.getFields()) {
      List<RlJcrParserParam> params = field.getParserParams();
      String name = field.getName();
      int type = field.getJcrType();
      Object binVal = binItem.getProperty(name).data; // May be null
      String prop = null;

      switch (type) {
        case PropertyType.BINARY:
          prop = m_binaryParser.stringify(binVal, params);
          break;
        case PropertyType.BOOLEAN:
          prop = m_booleanParser.stringify(binVal, params);
          break;
        case PropertyType.DATE:
          prop = m_dateParser.stringify(binVal, params);
          break;
        case PropertyType.DOUBLE:
          prop = m_doubleParser.stringify(binVal, params);
          break;
        case PropertyType.LONG:
          prop = m_longParser.stringify(binVal, params);
          break;
        case PropertyType.STRING:
          prop = (String)binVal;
          break;
      }

      strItem.addProperty(name, prop);
    }

    return strItem;
  }
}
