// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

//===========================================
// MODULE: Layout
//===========================================
angular.module("Layout", [])

//===========================================
// SERVICE: layout
//===========================================
.factory("layout", [function() {
    var layout = {
      blocks: {},
      bodyClass: "",
      navpath: []
    };

    return layout;
}])

//===========================================
// DIRECTIVE: rlBreadcrumb
//===========================================
.directive("rlBreadcrumb", ["layout", function(layout) {
  function link($scope) {
    $scope.layout = layout;
  }

  return {
    link: link,
    restrict: "E",
    replace: true,
    templateUrl: "templates/partials/breadcrumb.html"
  };
}])

//===========================================
// DIRECTIVE: rlBlockInsertion
//===========================================
.directive("rlBlockInsertion", ["layout", function(layout) {
  function link($scope, $element, $attrs) {
    layout.blocks[$attrs.rlBlockInsertion] = $element;
  }

  return {
    restrict: 'A',
    link: link
  };
}])

//===========================================
// DIRECTIVE: rlBlockReplacement
//===========================================
.directive("rlBlockReplacement", ["layout", function(layout) {
  function link($scope, $element, $attrs) {
    $element.detach();
    var block = layout.blocks[$attrs.rlBlockReplacement];

    if (!block) {
      return;
    }

    block.append($element);

    $scope.$on('$destroy', function () {
      $element.remove();
    });
  }

  return {
    restrict: 'A',
    link: link
  };
}]);
