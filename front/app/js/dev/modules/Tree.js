angular.module("Tree", [])

.directive("rlTree", ["$compile", function($compile) {
  function compile($element, $attrs) {
    var contents = $element.contents().remove();
    var link;

    return function($scope, $element) {
      var _minimised = true;

      $scope.onNodeClick = function($event, item) {
        if (item.children && item.children.length > 0) {
          _minimised = !_minimised;
        }
        $scope.rlOnSelection()(item, $($event.currentTarget));
      };

      $scope.onOptsClick = function($event, item) {
        $scope.rlOnOptionsSelection()(item, $($event.currentTarget));
      };

      $scope.getCss = function(item) {
        var css = "";
        if (item.children && item.children.length > 0) {
          css += "parent";
        }
        else {
          css += "child";
        }

        if (item.typeName === "folder") {
          css += " itemtype-folder";
        }

        if (!_minimised) {
          css += " item-open";
        }

        return css;
      };

      $scope.isMinimised = function() {
        return _minimised;
      };

      if (!link) {
        link = $compile(contents);
      }

      link($scope, function(element, scope) {
        $element.append(element);
      });
    };
  }

  return {
    restrict: "E",
    replace: true,
    scope: {
      rlRoot: "=",
      rlOnSelection: "&",
      rlOnOptionsSelection: "&"
    },
    templateUrl: "templates/partials/tree.html",
    compile: compile
  };
}]);
