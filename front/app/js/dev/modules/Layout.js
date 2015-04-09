angular.module("Layout", ["jlUtil"])
  .factory("layout", [function() {
      var layout = {
        blocks: {},
        bodyClass: ""
      };

      return layout;
  }])

  /**
  * (DIRECTIVE) Defines a containing element into which another may be inserted.
  *
  * @namespace Layout
  * @class rlBlockInsertion
  * @constructor
  * @param {Angular service} util
  * @param {Angular service} _layout
  */
  .directive("rlBlockInsertion", ["util", "layout", function(util, layout) {
    function link($scope, $element, $attrs) {
      var element = util.element($element);
      layout.blocks[$attrs.rlBlockInsertion] = element;
    }

    return {
      restrict: 'A',
      link: link
    };
  }])

  /**
  * (DIRECTIVE)
  *
  * @namespace Layout
  * @class rlBlockReplacement
  * @constructor
  * @param {Angular service} util
  * @param {Angular service} layout
  */
  .directive("rlBlockReplacement", ["util", "layout", function(util, layout) {
    function link($scope, $element, $attrs) {
      var element = util.element($element);

      element.detach();
      var block = layout.blocks[$attrs.rlBlockReplacement];

      if (!block) {
        return;
      }

      block.append(element);

      $scope.$on('$destroy', function () {
        element.remove();
      });
    }

    return {
      restrict: 'A',
      link: link
    };
  }]);
