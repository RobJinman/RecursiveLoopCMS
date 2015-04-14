// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

angular.module("Directives").directive("rlCredentials", ["session", function(session) {
  function link($scope, $element, $attrs) {
    var onChange = function() {
      $scope.username = session.getUserData().username;

      if ($scope.username === null) {
        $scope.username = "";
      }
    };

    onChange();

    $scope.$on("login", onChange);
    $scope.$on("authorise", onChange);
    $scope.$on("logout", onChange);

    $scope.getState = function(role) {
      return session.userHasRole(role) ? "badge-active" : "badge-inactive";
    };
  }

  return {
    link: link,
    restrict: "E",
    templateUrl: "templates/partials/credentials.html",
    scope: {}
  };
}]);
