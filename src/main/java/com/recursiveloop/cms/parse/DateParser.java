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
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;


public class DateParser extends FieldParser {
  @Override
  public Object parse(String str, List<RlJcrParserParam> paramList) throws InvalidItemException {
    Map<String, String> params = paramsAsMap(paramList);

    String dateFormat = params.get("dateFormat");
    dateFormat = dateFormat != null ? dateFormat : "dd/MM/yyyy HH:mm";

    try {
      Calendar calendar = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
      calendar.setTime(sdf.parse(str));

      return calendar;
    }
    catch (ParseException ex) {
      throw new InvalidItemException("Error parsing string '" + str + "' into Calendar", ex);
    }
  }

  @Override
  public String stringify(Object obj, List<RlJcrParserParam> paramList) throws InvalidItemException {
    if (obj == null) {
      return "";
    }

    Map<String, String> params = paramsAsMap(paramList);

    String dateFormat = params.get("dateFormat");
    dateFormat = dateFormat != null ? dateFormat : "dd/MM/yyyy HH:mm";

    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    return sdf.format(((Calendar)(obj)).getTime());
  }
}
