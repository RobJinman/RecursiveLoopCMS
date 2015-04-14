// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

angular.module("Directives").directive("rlAttrIf", [function() {
  function link($scope, $element) {
    var attrName = $scope.rlAttrIf["name"];
    var value = $scope.rlAttrIf["value"];
    var expr = $scope.rlAttrIf["if"];

    if (expr) {
      if (value) {
        $($element).attr(attrName, value);
      }
      else {
        $($element).attr(attrName);
      }
    }
  }

  return {
    link: link,
    restrict: "A",
    scope: {
      rlAttrIf: "="
    }
  };
}]);
