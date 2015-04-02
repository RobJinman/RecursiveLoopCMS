// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms.parse;

import com.recursiveloop.cms.exceptions.InvalidItemException;
import com.recursiveloop.cms.jcrmodel.RlJcrParserParam;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public abstract class FieldParser {
  public abstract Object parse(String str, List<RlJcrParserParam> paramList) throws InvalidItemException;

  /**
  * Must handle null values appropriately (e.g. return empty string)
  */
  public abstract String stringify(Object obj, List<RlJcrParserParam> paramList) throws InvalidItemException;

  protected Map<String, String> paramsAsMap(List<RlJcrParserParam> paramList) {
    Map<String, String> map = new HashMap<String, String>();

    for (RlJcrParserParam p : paramList) {
      map.put(p.getName(), p.getValue());
    }

    return map;
  }
}
