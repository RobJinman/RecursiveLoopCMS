// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

//===========================================
// MODULE: DeveloperTools
//===========================================
angular.module("DeveloperTools", [])

//===========================================
// CONTROLLER: DeveloperToolsCtrl
//===========================================
.controller("DeveloperToolsCtrl", [
"layout", "backend", "$modal", "notificationTypes", "$rootScope",
function(layout, backend, $modal, notificationTypes, $rootScope) {
  layout.bodyClass = "devtools-body";
  layout.navpath = [
    { href: "/#/index", title: "CMS Home" },
    { href: "/#/devtools", title: "Developer Tools" }
  ];

  var self = this;
  self.typeList = null;
  self.sidebarLoading = false;
  self.ST_OTHER = 0;
  self.ST_NEW_FIELD = 1;
  self.ST_UPDATE_FIELD = 2;
  self.current = {
    type: null,
    field: new rl.Field(),
    state: self.ST_OTHER
  };
  self.newTypeName = ""; // Sidebar new item data-binding
  self.jcrTypes = {
    byName: {},
    byValue: {}
  };

  //===========================================
  // _loadJcrTypes
  //===========================================
  var _loadJcrTypes = function() {
    backend.getJcrTypesByName().then(
      function(success) {
        self.jcrTypes.byName = success.data;
      },
      function(err) {
        $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
      }
    );

    backend.getJcrTypesByValue().then(
      function(success) {
        self.jcrTypes.byValue = success.data;
      },
      function(err) {
        $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
      }
    );
  };

  _loadJcrTypes();

  //===========================================
  // _loadSidebar
  //===========================================
  var _loadSidebar = function() {
    self.sidebarLoading = true;

    backend.getTypeList().then(
      function(success) {
        self.typeList = success.data;
        self.sidebarLoading = false;
      },
      function(err) {
        $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
      }
    );
  };

  _loadSidebar();

  //===========================================
  // saveBtnCaption
  //===========================================
  self.saveBtnCaption = function() {
    return self.current.state === self.ST_NEW_FIELD ? "Save new" : "Save changes";
  };

  //===========================================
  // onTypeSelection
  //===========================================
  self.onTypeSelection = function(typeName) {
    self.current.state = self.ST_OTHER;

    backend.getType(typeName).then(
      function(success) {
        self.current.type = success.data;
      },
      function(err) {
        $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
      }
    );
  };

  //===========================================
  // onNewFieldClick
  //===========================================
  self.onNewFieldClick = function() {
    self.current.state = self.ST_NEW_FIELD;
    self.current.field = new rl.Field();
  };

  //===========================================
  // onEditFieldClick
  //===========================================
  self.onEditFieldClick = function(field) {
    self.current.state = self.ST_UPDATE_FIELD;
    self.current.field = new rl.Field(field);
  };

  //===========================================
  // onDeleteFieldClick
  //===========================================
  self.onDeleteFieldClick = function(field) {
    $modal.open({
      templateUrl: "templates/partials/confirmationModal.html",
    }).result.then(function() {
      backend.deleteField(self.current.type.typeName, field.fieldName).then(
        function(success) {
          // Refresh
          self.onTypeSelection(self.current.type.typeName);
          if (field.fieldName !== self.current.field.fieldName) {
            self.onEditFieldClick(self.current.field);
          }

          $rootScope.$broadcast("notification", notificationTypes.SUCCESS, "Operation successful");
        },
        function(err) {
          $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
        }
      );
    });
  };

  //===========================================
  // onNewTypeClick
  //===========================================
  self.onNewTypeClick = function() {
    backend.createType(self.newTypeName).then(
      function(success) {
        self.current.type = success.data;
        self.onTypeSelection(self.current.type.typeName);
        _loadSidebar();
        self.newTypeName = "";

        $rootScope.$broadcast("notification", notificationTypes.SUCCESS, "Operation successful");
      },
      function(err) {
        $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
      }
    );
  };

  //===========================================
  // onDeleteTypeClick
  //===========================================
  self.onDeleteTypeClick = function(typeName, $event) {
    $modal.open({
      templateUrl: "templates/partials/confirmationModal.html",
    }).result.then(function() {
      backend.deleteType(typeName).then(
        function(success) {
          _loadSidebar();
          $rootScope.$broadcast("notification", notificationTypes.SUCCESS, "Operation successful");
        },
        function(err) {
          $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
        }
      );
    });

    $event.stopPropagation();
  };

  //===========================================
  // onSaveFieldClick
  //===========================================
  self.onSaveFieldClick = function() {
    if (self.current.state === self.ST_UPDATE_FIELD) {
      backend.updateField(self.current.type.typeName, self.current.field).then(
        function(success) {
          self.onTypeSelection(self.current.type.typeName); // Refresh
          $rootScope.$broadcast("notification", notificationTypes.SUCCESS, "Operation successful");
        },
        function(err) {
          $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
        }
      );
    }
    else if (self.current.state === self.ST_NEW_FIELD) {
      backend.insertField(self.current.type.typeName, self.current.field).then(
        function(success) {
          self.onTypeSelection(self.current.type.typeName); // Refresh
          $rootScope.$broadcast("notification", notificationTypes.SUCCESS, "Operation successful");
        },
        function(err) {
          $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
        }
      );
    }
  };

  //===========================================
  // onCancelFieldClick
  //===========================================
  self.onCancelFieldClick = function() {
    self.current.state = self.ST_OTHER;
  };
}]);
