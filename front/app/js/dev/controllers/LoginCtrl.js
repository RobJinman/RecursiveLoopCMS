// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

angular.module("Controllers").controller("LoginCtrl", [
"layout", "session", "$location", "$rootScope", "notificationTypes",
function(layout, session, $location, $rootScope, notificationTypes) {
  var self = this;

  layout.bodyClass = "login-body";
  layout.navpath = [
    { href: "/#/index", title: "CMS Home" }
  ];

  self.username = "";
  self.password = "";

  self.login = function() {
    session.authenticate(self.username, self.password).then(
      function(success) {
        $location.path("/index");
      },
      function(err) {
        self.password = "";
        $rootScope.$broadcast("notification", notificationTypes.ERROR, "Authentication Failed", err);
      }
    );
  };
}]);
