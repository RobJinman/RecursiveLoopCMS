app.factory("session", ["backend", "$q", "$rootScope", "$location", function(backend, $q, $rootScope, $location) {
  function User() {
    this.isLoggedIn = false;
    this.username = null;
    this.roles = [];
  }
  var _user = new User();

  var _session = {
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

    logout: function() {
      return backend.logout().then(function(response) {
        _user = new User();
        return response;
      }).then(function() {
        $rootScope.$broadcast("logout");
      });
    },

    userHasRole: function(role) {
      return _user.roles.indexOf(role) != -1;
    },

    getUserData: function() {
      return _user;
    },

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
