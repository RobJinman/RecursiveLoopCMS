/**
* (CONTROLLER) The main controller
*
* @namespace App
* @class MainCtrl
* @constructor
*/
app.controller("MainCtrl", [function() {
  this.square = function(i) {
    return i * i;
  };
}]);
