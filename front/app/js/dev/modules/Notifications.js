// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

//===========================================
// MODULE: Notifications
//===========================================
angular.module("Notifications", [])

.constant("notificationTypes", {
  ERROR: 1,
  INFO: 2,
  SUCCESS: 3
})

//===========================================
// DIRECTIVE: rlNotifications
//===========================================
.directive("rlNotifications", [
"$log", "notificationTypes", "$timeout",
function($log, notificationTypes, $timeout) {

  function link($scope, $element) {
    var promise = null;

    $scope.$on("notification", function($event, type, msg, err) {
      if (err) {
        $log.warn(err);
      }

      switch (type) {
        case notificationTypes.ERROR:
          $scope.notificationType = "error";
          break;
        case notificationTypes.INFO:
          $scope.notificationType = "info";
          break;
        case notificationTypes.SUCCESS:
          $scope.notificationType = "success";
          break;
      }

      $scope.msg = msg;
      $scope.state = "active";

      if (promise) {
        $timeout.cancel(promise);
      }

      promise = $timeout(function() {
        $scope.state = "";
      }, 3000);
    });
  }

  return {
    link: link,
    restrict: "E",
    replace: true,
    templateUrl: "templates/partials/notifications.html",
    scope: {}
  };
}]);
