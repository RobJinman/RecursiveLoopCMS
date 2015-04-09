/**
* (CONTROLLER) The main controller
*
* @namespace App
* @class MainCtrl
* @constructor
*/
angular.module("Controllers").controller("MainCtrl", [
"layout", "session", "$route",
function(layout, session, $route) {

  this.layout = layout;

  this.logout = function() {
    session.logout().then(function() {
      $route.reload();
    });
  };
}]);
