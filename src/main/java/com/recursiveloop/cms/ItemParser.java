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
    throws InvalidItemException, NoSuchTypeException {

    BinaryItem binItem = new BinaryItem(strItem);

    for (RlJcrFieldType field : itemType.getFields()) {
      List<RlJcrParserParam> params = field.getParserParams();
      String name = field.getName();
      String type = field.getJcrType();
      String strVal = strItem.getProperty(name);
      JcrProperty prop = null;

      if (type.equals(PropertyType.TYPENAME_BINARY)) {
        prop = new JcrProperty(PropertyType.BINARY, m_binaryParser.parse(strVal, params));
      }
      else if (type.equals(PropertyType.TYPENAME_BOOLEAN)) {
        prop = new JcrProperty(PropertyType.BOOLEAN, m_booleanParser.parse(strVal, params));
      }
      else if (type.equals(PropertyType.TYPENAME_DATE)) {
        prop = new JcrProperty(PropertyType.DATE, m_dateParser.parse(strVal, params));
      }
      else if (type.equals(PropertyType.TYPENAME_DOUBLE)) {
        prop = new JcrProperty(PropertyType.DOUBLE, m_doubleParser.parse(strVal, params));
      }
      else if (type.equals(PropertyType.TYPENAME_LONG)) {
        prop = new JcrProperty(PropertyType.LONG, m_longParser.parse(strVal, params));
      }
      else if (type.equals(PropertyType.TYPENAME_STRING)) {
        prop = new JcrProperty(PropertyType.STRING, strVal);
      }
      else {
        throw new NoSuchTypeException(type);
      }

      binItem.addProperty(name, prop);
    }

    return binItem;
  }

  public StringItem stringify(BinaryItem binItem, ItemType itemType)
    throws InvalidItemException, NoSuchTypeException {

    StringItem strItem = new StringItem(binItem);

    for (RlJcrFieldType field : itemType.getFields()) {
      List<RlJcrParserParam> params = field.getParserParams();
      String name = field.getName();
      String type = field.getJcrType();
      Object binVal = binItem.getProperty(name).data;
      String prop = null;

      if (type.equals(PropertyType.TYPENAME_BINARY)) {
        prop = m_binaryParser.stringify(binVal, params);
      }
      else if (type.equals(PropertyType.TYPENAME_BOOLEAN)) {
        prop = m_booleanParser.stringify(binVal, params);
      }
      else if (type.equals(PropertyType.TYPENAME_DATE)) {
        prop = m_dateParser.stringify(binVal, params);
      }
      else if (type.equals(PropertyType.TYPENAME_DOUBLE)) {
        prop = m_doubleParser.stringify(binVal, params);
      }
      else if (type.equals(PropertyType.TYPENAME_LONG)) {
        prop = m_longParser.stringify(binVal, params);
      }
      else if (type.equals(PropertyType.TYPENAME_STRING)) {
        prop = (String)binVal;
      }
      else {
        throw new NoSuchTypeException(type);
      }

      strItem.addProperty(name, prop);
    }

    return strItem;
  }
}
