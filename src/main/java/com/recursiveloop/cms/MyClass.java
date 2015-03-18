package com.recursiveloop.cms;

import com.recursiveloop.webcommon.annotations.Config;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;


@Named(value = "myClass")
@ViewScoped
public class MyClass {
  @Inject @Config("myString")
  String m_string;
}
