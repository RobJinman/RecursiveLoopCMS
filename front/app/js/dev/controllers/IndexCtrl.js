// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

angular.module("Controllers").controller("IndexCtrl", [
"layout",
function(layout) {
  layout.bodyClass = "index-body";
  layout.navpath = [
    { href: "/#/index", title: "CMS Home" }
  ];
}]);
