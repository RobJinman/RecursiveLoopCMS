/**
* (CONTROLLER) The controller for the login view
*
* @namespace App
* @class LoginCtrl
* @constructor
*/
angular.module("Controllers").controller("LoginCtrl", [
"layout", "session", "$location",
function(layout, session, $location) {
  var self = this;

  layout.bodyClass = "login-body";

  self.username = "";
  self.password = "";
  self.errorMessage = "";

  self.login = function() {
    session.authenticate(self.username, self.password).then(
      function(success) {
        $location.path("/index");
      },
      function(err) {
        self.errorMessage = "Authentication failed";
      }
    );
  };
}]);
