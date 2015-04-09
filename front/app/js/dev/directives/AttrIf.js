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
