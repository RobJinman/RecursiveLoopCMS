var rl = rl || {};

/**
* (SERVICE) Encapsulates server communication
*
* @namespace Backend
* @class backend
* @static
* @param {Angular service} $http
* @param {String} url The base URL for REST services
*/
rl.Backend = function($http, url) {
  var _url = url;

  /**
  * @method getUrl
  * @return {String} The base URL for REST services
  */
  this.getUrl = function() {
    return _url;
  };

  /**
  * Start a session on the server
  *
  * @method login
  * @param {String username, String password} user
  * @return {Promise} Promise from $http service
  */
  this.login = function(username, password) {
    var config = {
      method: "POST",
      url: _url + "/session",
      data: {
        username: username,
        password: password
      }
    };

    return $http(config);
  };

  /**
  * Get a session on the server
  *
  * @method getSession
  * @return {Promise} Promise from $http service
  */
  this.getSession = function() {
    var config = {
      method: "GET",
      url: _url + "/session"
    };

    return $http(config);
  };

  /**
  * Delete a session on the server
  *
  * @method logout
  * @return {Promise} Promise from $http service
  */
  this.logout = function() {
    var config = {
      method: "DELETE",
      url: _url + "/session"
    };

    return $http(config);
  };

  this.getItemTree = function() {
    var config = {
      method: "GET",
      url: _url + "/repository/itemTree"
    };

    return $http(config);
  };

  this.getItem = function(path) {
    var config = {
      method: "GET",
      url: _url + "/repository/itemTree/item/" + encodeURIComponent(path)
    };

    return $http(config);
  };

  this.getType = function(typeName) {
    var config = {
      method: "GET",
      url: _url + "/repository/types/" + encodeURIComponent(typeName)
    };

    return $http(config);
  };

  this.getTypeList = function() {
    var config = {
      method: "GET",
      url: _url + "/repository/types"
    };

    return $http(config);
  };
};

/**
* Encapsulates server communication
*
* @module App
* @submodule Backend
*/
angular.module("Backend", [])
  /**
  * (PROVIDER) Provider for backend service
  *
  * @namespace Backend
  * @class backendProvider
  * @static
  */
  .provider("backend", [function backendProvider() {
    var self = this;

    /**
    * Base URL for REST services
    *
    * @property url
    * @type String
    */
    self.url = "localhost";

    self.$get = ["$http", function($http) {
      return new rl.Backend($http, self.url);
    }];
  }]);
