package com.recursiveloop.cms;


public interface FieldParser {
  public Object parse(String str) throws InvalidItemException;
  public String stringify(Object obj) throws InvalidItemException;
}
