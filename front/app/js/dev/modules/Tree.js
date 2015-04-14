// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

//===========================================
// MODULE: Tree
//===========================================
angular.module("Tree", [])

//===========================================
// DIRECTIVE: rlTree
//===========================================
.directive("rlTree", ["$compile", function($compile) {

  //===========================================
  // compile
  //===========================================
  function compile($element, $attrs) {
    var contents = $element.contents().remove();
    var link;

    //===========================================
    // link
    //===========================================
    return function($scope, $element) {

      //===========================================
      // _isRoot
      //===========================================
      var _isRoot = function() {
        return $element.parents(".tree").length === 0;
      };

      if (_isRoot()) {
        var _populateStateMap_r = function(stateMap, item, tree) {
          var chTree = tree.children("ul");

          stateMap[item.path] = tree.children("ul").is(":visible");

          var childTrees = tree.find(">ul>li>.tree");
          for (var i = 0; i < item.children.length; ++i) {
            _populateStateMap_r(stateMap, item.children[i], $(childTrees[i]));
          }
        };

        //===========================================
        // $scope.$on save
        //===========================================
        $scope.$on("save", function() {
          // Maps item paths to whether tree is minimised (string->boolean)
          $scope.stateMap = {};

          _populateStateMap_r($scope.stateMap, $scope.rlRoot, $element);
        });

        //===========================================
        // $scope.$on restore
        //===========================================
        $scope.$on("restore", function() {
          for (var i in $scope.stateMap) {
            if ($scope.stateMap[i] === true) {
              $scope.$broadcast("open-tree", i);
            }
          }
        });
      }

      //===========================================
      // $scope.$on open-tree
      //===========================================
      $scope.$on("open-tree", function($event, itemPath) {
        if ($scope.rlRoot.path === itemPath) {
          $element.children("ul").show();
        }
      });

      //===========================================
      // $scope.onNodeClick
      //===========================================
      $scope.onNodeClick = function($event, item) {
        if (item.children && item.children.length > 0) {
          if ($element.children("ul").is(":visible")) {
            $element.find("ul").hide("fast");
          }
          else {
            $element.children("ul").show("fast");
          }
        }
        $scope.rlOnSelection()(item, $($event.currentTarget));
      };

      //===========================================
      // $scope.onOptsClick
      //===========================================
      $scope.onOptsClick = function($event, item) {
        $scope.rlOnOptionsSelection()(item, $($event.currentTarget));
      };

      //===========================================
      // $scope.getCss
      //===========================================
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

        if ($element.children("ul").is(":visible")) {
          css += " item-open";
        }

        return css;
      };

      if (!link) {
        link = $compile(contents);
      }

      link($scope, function(element, scope) {
        $element.append(element);
      });

      $element.children("ul").hide();
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
