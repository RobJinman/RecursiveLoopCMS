// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

angular.module("Converters").directive("integer", function() {
  function link($scope, $element, $attr, ngModel) {
    function toInteger(text) {
      return parseInt(text);
    }

    function toString(value) {
      return value.toString();
    };

    ngModel.$parsers.push(toInteger);
    ngModel.$formatters.push(toString);
  }

  return {
    restrict: "A",
    require: "ngModel",
    link: link
  };
});
