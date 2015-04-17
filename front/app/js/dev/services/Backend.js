// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

var rl = rl || {};

//===========================================
// SERVICE: Backend
//===========================================
rl.Backend = function($http, url) {
  var _url = url;

  this.getUrl = function() {
    return _url;
  };

  //===========================================
  // login
  //
  // Start a new session on the server
  //===========================================
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

  //===========================================
  // getSession
  //
  // Retrieve the current session from the server
  //===========================================
  this.getSession = function() {
    var config = {
      method: "GET",
      url: _url + "/session"
    };

    return $http(config);
  };

  //===========================================
  // logout
  //
  // End the current session on the server
  //===========================================
  this.logout = function() {
    var config = {
      method: "DELETE",
      url: _url + "/session"
    };

    return $http(config);
  };

  //===========================================
  // getItemTree
  //===========================================
  this.getItemTree = function() {
    var config = {
      method: "GET",
      url: _url + "/repository/itemTree"
    };

    return $http(config);
  };

  //===========================================
  // getItem
  //===========================================
  this.getItem = function(path) {
    var config = {
      method: "GET",
      url: _url + "/repository/itemTree/item/" + encodeURIComponent(path)
    };

    return $http(config);
  };

  //===========================================
  // getType
  //===========================================
  this.getType = function(typeName) {
    var config = {
      method: "GET",
      url: _url + "/repository/types/" + encodeURIComponent(typeName)
    };

    return $http(config);
  };

  //===========================================
  // getTypeList
  //===========================================
  this.getTypeList = function() {
    var config = {
      method: "GET",
      url: _url + "/repository/types"
    };

    return $http(config);
  };

  //===========================================
  // updateItem
  //===========================================
  this.updateItem = function(item) {
    var config = {
      method: "PUT",
      url: _url + "/repository/itemTree/item/" + encodeURIComponent(item.path),
      data: item
    };

    return $http(config);
  };

  //===========================================
  // moveItem
  //===========================================
  this.moveItem = function(oldPath, item) {
    console.log(oldPath);
    console.log(item);

    var config = {
      method: "PUT",
      url: _url + "/repository/itemTree/item/" + encodeURIComponent(oldPath),
      data: item
    };

    return $http(config);
  };

  //===========================================
  // renameItem
  //===========================================
  this.renameItem = function(oldName, item) {
    var parent = item.path.substr(0, item.path.lastIndexOf("/"));
    var oldPath = parent + "/" + oldName;

    console.log(oldPath);
    console.log(item);

    var config = {
      method: "PUT",
      url: _url + "/repository/itemTree/item/" + encodeURIComponent(oldPath),
      data: item
    };

    return $http(config);
  };

  //===========================================
  // createItem
  //===========================================
  this.createItem = function(item) {
    var config = {
      method: "POST",
      url: _url + "/repository/itemTree",
      data: item
    };

    return $http(config);
  };

  //===========================================
  // createFolder
  //===========================================
  this.createFolder = function(path) {
    var item = new rl.Item();
    item.itemName = /[^/]*$/.exec(path)[0];
    item.path = path;

    var config = {
      method: "POST",
      url: _url + "/repository/itemTree",
      data: item
    };

    return $http(config);
  };

  //===========================================
  // deleteItem
  //===========================================
  this.deleteItem = function(path) {
    var config = {
      method: "DELETE",
      url: _url + "/repository/itemTree/item/" + encodeURIComponent(path)
    };

    return $http(config);
  };

  //===========================================
  // createType
  //===========================================
  this.createType = function(typeName) {
    var type = new rl.Type(typeName);

    var config = {
      method: "POST",
      url: _url + "/repository/types",
      data: type
    };

    return $http(config);
  };

  //===========================================
  // deleteType
  //===========================================
  this.deleteType = function(typeName) {
    var config = {
      method: "DELETE",
      url: _url + "/repository/types/" + encodeURIComponent(typeName)
    };

    return $http(config);
  };

  //===========================================
  // getJcrTypesByName
  //===========================================
  this.getJcrTypesByName = function() {
    var config = {
      method: "GET",
      url: _url + "/repository/jcrTypes?key=name"
    };

    return $http(config);
  };

  //===========================================
  // getJcrTypesByValue
  //===========================================
  this.getJcrTypesByValue = function() {
    var config = {
      method: "GET",
      url: _url + "/repository/jcrTypes?key=value"
    };

    return $http(config);
  };

  //===========================================
  // insertField
  //===========================================
  this.insertField = function(typeName, field) {
    var config = {
      method: "POST",
      url: _url + "/repository/types/" + encodeURIComponent(typeName) + "/fields",
      data: field
    };

    return $http(config);
  };

  //===========================================
  // updateField
  //===========================================
  this.updateField = function(typeName, field) {
    var config = {
      method: "PUT",
      url: _url + "/repository/types/" + encodeURIComponent(typeName) + "/fields/" + encodeURIComponent(field.fieldName),
      data: field
    };

    return $http(config);
  };

  //===========================================
  // deleteField
  //===========================================
  this.deleteField = function(typeName, fieldName) {
    var config = {
      method: "DELETE",
      url: _url + "/repository/types/" + encodeURIComponent(typeName) + "/fields/" + encodeURIComponent(fieldName)
    };

    return $http(config);
  };
};

//===========================================
// MODULE: Services
//===========================================
angular.module("Services")
  .provider("backend", [function backendProvider() {
    var self = this;

    self.url = "localhost";

    self.$get = ["$http", function($http) {
      return new rl.Backend($http, self.url);
    }];
  }]);
