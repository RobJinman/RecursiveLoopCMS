package com.recursiveloop.cms;


public class BooleanParser implements FieldParser {
  @Override
  public Object parse(String str) throws InvalidItemException {
    return new Boolean(str);
  }

  @Override
  public String stringify(Object obj) throws InvalidItemException {
    return ((Boolean)(obj)).toString();
  }
}
