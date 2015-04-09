/**
* (CONTROLLER) The controller for the developer tools view
*
* @namespace App
* @class DeveloperToolsCtrl
* @constructor
*/
angular.module("Controllers").controller("DeveloperToolsCtrl", [
"layout",
function(layout) {
  layout.bodyClass = "devtools-body";
}]);
