// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

var rl = rl || {};

(function(ns) {
  function _makeClone(A, B) {
    A.fieldName = B.fieldName;
    A.type = B.type;
    A.required = B.required;
    A.widget = B.widget;
    A.defaultValue = B.defaultValue;

    A.parserParams = {};
    for (var pp in B.parserParams) {
      A.parserParams[pp] = B.parserParams[pp];
    }

    A.widgetParams = {};
    for (var wp in B.widgetParams) {
      A.widgetParams[wp] = B.widgetParams[wp];
    }
  };

  ns.Field = function(cpy) {
    if (cpy) {
      _makeClone(this, cpy);
      return;
    }

    this.fieldName = "";
    this.type = 0;
    this.required = false;
    this.widget = "textedit";
    this.defaultValue = "";
    this.parserParams = {};
    this.widgetParams = {};
  };
})(rl);
