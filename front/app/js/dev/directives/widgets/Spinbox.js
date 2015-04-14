// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

angular.module("Directives").directive("rlSpinbox", ["$parse", function($parse) {
  function link($scope, $element, $attrs) {
    $element.find(".spinner").spinner({
      spin: function(event, ui) {
        var expr = $(this).attr("data-ng-model");
        var setter = $parse(expr).assign;

        setter($scope, ui.value.toString());
      }
    }, 0);
  }

  return {
    link: link,
    restrict: "E",
    templateUrl: "templates/partials/widgets/spinbox.html",
    scope: {
      item: "=rlItem",
      field: "=rlField"
    }
  };
}]);
