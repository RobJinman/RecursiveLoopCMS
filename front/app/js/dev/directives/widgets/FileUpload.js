// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

angular.module("Directives")

.directive("rlFileupload", [function() {
  function link($scope, $element) {
    var params = $scope.field.widgetParams;

    $element.on("change", function(event) {
      var reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);

      reader.onloadend = function() {
        var i = reader.result.indexOf("base64,");
        var str = reader.result;

        if (i != -1) {
          str = reader.result.substr(i + "base64,".length);
        }

        $scope.item.data[$scope.field.fieldName] = str;
        $scope.$apply();
      }

      $scope.$apply();
    });

    $scope.$watch("item.data." + $scope.field.fieldName, function() {
      var data = $scope.item.data[$scope.field.fieldName];

      if (data) {
        var mimeType = params.mimeType || "other";
        if (mimeType.match(/^image/)) {
          $scope.preview = "data:" + mimeType + ";base64," + data;
        }
        else {
          $scope.preview = "img/no-preview.png";
        }
      }
      else {
        $scope.preview = "img/no-file.png";
      }
    });
  }

  return {
    link: link,
    restrict: "E",
    templateUrl: "templates/partials/widgets/fileUpload.html",
    replace: true,
    scope: {
      item: "=rlItem",
      field: "=rlField",
      dataType: "=rlDataType"
    }
  };
}]);
