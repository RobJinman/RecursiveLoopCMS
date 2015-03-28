// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015


package com.recursiveloop.cms;

import com.recursiveloop.cms.jcrmodel.RlJcrParserParam;
import java.util.List;


public class LongParser extends FieldParser {
  @Override
  public Object parse(String str, List<RlJcrParserParam> paramList) throws InvalidItemException {
    try {
      return new Long(str);
    }
    catch (NumberFormatException ex) {
      throw new InvalidItemException("Error parsing string '" + str + "' as Long", ex);
    }
  }

  @Override
  public String stringify(Object obj, List<RlJcrParserParam> paramList) throws InvalidItemException {
    return ((Long)(obj)).toString();
  }
}
