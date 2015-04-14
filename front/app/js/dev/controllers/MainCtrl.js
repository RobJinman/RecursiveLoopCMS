// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

angular.module("Controllers").controller("MainCtrl", [
"layout", "session", "$route",
function(layout, session, $route) {

  this.layout = layout;

  this.logout = function() {
    session.logout().then(function() {
      $route.reload();
    });
  };
}]);
