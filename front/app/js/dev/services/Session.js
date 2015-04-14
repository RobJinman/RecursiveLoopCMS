// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

//===========================================
// MODULE: Services
//===========================================
angular.module("Services")

//===========================================
// SERVICE: session
//===========================================
.factory("session", ["backend", "$q", "$rootScope", "$location",
function(backend, $q, $rootScope, $location) {

  function User() {
    this.isLoggedIn = false;
    this.username = null;
    this.roles = [];
  }
  var _user = new User();

  var _session = {
    //===========================================
    // authenticate
    //===========================================
    authenticate: function(username, password) {
      return backend.login(username, password).then(function(response) {
        _user.isLoggedIn = true;
        _user.username = response.data.username;
        _user.roles = response.data.roles;

        return response;
      }).then(function() {
        $rootScope.$broadcast("login");
      });
    },

    //===========================================
    // authorise
    //===========================================
    authorise: function(roles) {
      return backend.getSession().then(function(response) {
        if (typeof response.data.username === "undefined" || response.data.username === null) {
          _user = new User();

          return $q.reject(response);
        }
        else {
          _user.isLoggedIn = true;
          _user.username = response.data.username;
          _user.roles = response.data.roles;

          for (var i = 0; i < roles.length; ++i) {
            if (_user.roles.indexOf(roles[i]) != -1) {
              return response;
            }
          }

          return $q.reject(response);
        }
      }).then(function() {
        $rootScope.$broadcast("authorise");
      });
    },

    //===========================================
    // logout
    //===========================================
    logout: function() {
      return backend.logout().then(function(response) {
        _user = new User();
        return response;
      }).then(function() {
        $rootScope.$broadcast("logout");
      });
    },

    //===========================================
    // userHasRole
    //===========================================
    userHasRole: function(role) {
      return _user.roles.indexOf(role) != -1;
    },

    //===========================================
    // getUserData
    //===========================================
    getUserData: function() {
      return _user;
    },

    //===========================================
    // authResolver
    //===========================================
    authResolver: function(roles, onFail) {
      return _session.authorise(roles).then(
        function(success) {},
        function(err) {
          $location.path(onFail);
          $location.replace();
          return $q.reject(err);
        }
      );
    }
  };

  return _session;
}]);
