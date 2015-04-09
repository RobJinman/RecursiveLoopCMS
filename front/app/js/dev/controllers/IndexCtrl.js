/**
* (CONTROLLER) The controller for the index view
*
* @namespace App
* @class IndexCtrl
* @constructor
*/
angular.module("Controllers").controller("IndexCtrl", [
"layout",
function(layout) {
  layout.bodyClass = "index-body";
}]);
