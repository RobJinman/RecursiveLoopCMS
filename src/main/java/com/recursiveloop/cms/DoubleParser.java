package com.recursiveloop.cms;


public class DoubleParser implements FieldParser {
  @Override
  public Object parse(String str) throws InvalidItemException {
    try {
      return new Double(str);
    }
    catch (NumberFormatException ex) {
      throw new InvalidItemException("Error parsing string '" + str + "' as Double", ex);
    }
  }

  @Override
  public String stringify(Object obj) throws InvalidItemException {
    return ((Double)(obj)).toString();
  }
}
