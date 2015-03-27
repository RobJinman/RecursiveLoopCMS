package com.recursiveloop.cms;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;


public class DateParser implements FieldParser {
  @Override
  public Object parse(String str) throws InvalidItemException {
    try {
      Calendar calendar = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat(); // TODO: Specify format
      calendar.setTime(sdf.parse(str));

      return calendar;
    }
    catch (ParseException ex) {
      throw new InvalidItemException("Error parsing string '" + str + "' into Calendar", ex);
    }
  }

  @Override
  public String stringify(Object obj) throws InvalidItemException {
    SimpleDateFormat sdf = new SimpleDateFormat(); // TODO: Specify format
    return sdf.format(((Calendar)(obj)).getTime());
  }
}
