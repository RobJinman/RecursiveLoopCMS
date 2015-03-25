package com.recursiveloop.cms;

import com.recursiveloop.cms.jcrbeans.FieldDef;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jcr.PropertyType;


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

  public BinaryItem parse(StringItem strItem, Type itemType)
    throws InvalidItemException, NoSuchTypeException {

    BinaryItem binItem = new BinaryItem(strItem);

    for (FieldDef field : itemType.getFields()) {
      String name = field.getName();
      String type = field.getType();
      String strVal = strItem.getProperty(name);
      JcrProperty prop = null;

      if (type.equals(PropertyType.TYPENAME_BINARY)) {
        prop = new JcrProperty(PropertyType.BINARY, m_binaryParser.parse(strVal));
      }
      else if (type.equals(PropertyType.TYPENAME_BOOLEAN)) {
        prop = new JcrProperty(PropertyType.BOOLEAN, m_booleanParser.parse(strVal));
      }
      else if (type.equals(PropertyType.TYPENAME_DATE)) {
        prop = new JcrProperty(PropertyType.DATE, m_dateParser.parse(strVal));
      }
      else if (type.equals(PropertyType.TYPENAME_DOUBLE)) {
        prop = new JcrProperty(PropertyType.DOUBLE, m_doubleParser.parse(strVal));
      }
      else if (type.equals(PropertyType.TYPENAME_LONG)) {
        prop = new JcrProperty(PropertyType.LONG, m_longParser.parse(strVal));
      }
      else if (type.equals(PropertyType.TYPENAME_STRING)) {
        prop = new JcrProperty(PropertyType.STRING, strVal);
      }
      else {
        throw new NoSuchTypeException();
      }

      binItem.addProperty(name, prop);
    }

    return binItem;
  }

  public StringItem stringify(BinaryItem binItem, Type itemType)
    throws InvalidItemException, NoSuchTypeException {

    StringItem strItem = new StringItem(binItem);

    for (FieldDef field : itemType.getFields()) {
      String name = field.getName();
      String type = field.getType();
      Object binVal = binItem.getProperty(name).data;
      String prop = null;

      if (type.equals(PropertyType.TYPENAME_BINARY)) {
        prop = m_binaryParser.stringify(binVal);
      }
      else if (type.equals(PropertyType.TYPENAME_BOOLEAN)) {
        prop = m_booleanParser.stringify(binVal);
      }
      else if (type.equals(PropertyType.TYPENAME_DATE)) {
        prop = m_dateParser.stringify(binVal);
      }
      else if (type.equals(PropertyType.TYPENAME_DOUBLE)) {
        prop = m_doubleParser.stringify(binVal);
      }
      else if (type.equals(PropertyType.TYPENAME_LONG)) {
        prop = m_longParser.stringify(binVal);
      }
      else if (type.equals(PropertyType.TYPENAME_STRING)) {
        prop = (String)binVal;
      }
      else {
        throw new NoSuchTypeException();
      }

      strItem.addProperty(name, prop);
    }

    return strItem;
  }
}
