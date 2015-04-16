// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

var rl = rl || {};

(function(ns) {
  ns.Item = function(type) {
    this.children = [];
    this.path = "/rl:items";
    this.typeName = "folder";
    this.itemName = "";
    this.status = 0;
    this.data = {};

    if (type) {
      for (var key in type.fields) {
        var f = type.fields[key];
        this.data[f.fieldName] = f.defaultValue || "";
      }
    }
  };
})(rl);
