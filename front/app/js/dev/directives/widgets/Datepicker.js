// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

angular.module("Directives")
.directive("rlDatepicker", ["$filter", function($filter) {
  function link($scope, $element, $attrs) {
    $scope.open = function($event) {
      $event.preventDefault();
      $event.stopPropagation();

      $scope.opened = true;
    };

    $scope.format = $scope.field.widgetParams.dateFormat || "dd/MM/yyyy";

    $scope.dt = $scope.item.data[$scope.field.fieldName];

    $scope.onChange = function() {
      if (typeof $scope.dt === "string") {
        return;
      }

      var obj = $scope.dt;
      var str = $filter("date")(obj, $scope.format);

      $scope.item.data[$scope.field.fieldName] = str;
    };
  }

  return {
    link: link,
    restrict: "E",
    templateUrl: "templates/partials/widgets/datepicker.html",
    scope: {
      item: "=rlItem",
      field: "=rlField"
    }
  };
}]);
