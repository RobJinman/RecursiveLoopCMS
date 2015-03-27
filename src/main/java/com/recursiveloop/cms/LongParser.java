package com.recursiveloop.cms;


public class LongParser implements FieldParser {
  @Override
  public Object parse(String str) throws InvalidItemException {
    try {
      return new Long(str);
    }
    catch (NumberFormatException ex) {
      throw new InvalidItemException("Error parsing string '" + str + "' as Long", ex);
    }
  }

  @Override
  public String stringify(Object obj) throws InvalidItemException {
    return ((Long)(obj)).toString();
  }
}
