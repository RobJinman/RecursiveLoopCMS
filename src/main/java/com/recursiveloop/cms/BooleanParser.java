// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms;

import com.recursiveloop.cms.jcrmodel.RlJcrParserParam;
import java.util.List;


public class BooleanParser extends FieldParser {
  @Override
  public Object parse(String str, List<RlJcrParserParam> paramList) throws InvalidItemException {
    return new Boolean(str);
  }

  @Override
  public String stringify(Object obj, List<RlJcrParserParam> paramList) throws InvalidItemException {
    if (obj == null) {
      return "false";
    }

    return ((Boolean)(obj)).toString();
  }
}
