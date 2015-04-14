// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

var rl = rl || {};

rl.Item = function() {
  this.children = [];
  this.path = "/rl:items";
  this.typeName = "folder";
  this.itemName = "";
  this.status = 0;
  this.data = {};
};
