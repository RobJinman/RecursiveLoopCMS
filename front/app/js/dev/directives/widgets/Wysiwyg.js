// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

angular.module("Directives").directive("rlWysiwyg", [function() {
  function link($scope, $element, $attrs) {

  }

  return {
    link: link,
    restrict: "E",
    templateUrl: "templates/partials/widgets/wysiwyg.html",
    scope: {
      item: "=rlItem",
      field: "=rlField"
    }
  };
}]);
