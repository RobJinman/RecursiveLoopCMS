// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

angular.module("Controllers").controller("LoginCtrl", [
"layout", "session", "$location",
function(layout, session, $location) {
  var self = this;

  layout.bodyClass = "login-body";

  self.username = "";
  self.password = "";
  self.errorMessage = "";

  self.login = function() {
    session.authenticate(self.username, self.password).then(
      function(success) {
        $location.path("/index");
      },
      function(err) {
        self.errorMessage = "Authentication failed";
      }
    );
  };
}]);
