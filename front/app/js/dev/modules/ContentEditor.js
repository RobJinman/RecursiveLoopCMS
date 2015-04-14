// This file is property of Recursive Loop Ltd.
//
// Author: Rob Jinman
// Web: http://recursiveloop.org
// Copyright Recursive Loop Ltd 2015
// Copyright Rob Jinman 2015

//===========================================
// MODULE: ContentEditor
//===========================================
angular.module("ContentEditor", [])

//===========================================
// DIRECTIVE: rlEditorDropdown
//===========================================
.directive("rlEditorDropdown", [function() {
  function link($scope, $element, $attr) {
    var _optsMenu = $element;
    $scope.item = null;

    _optsMenu.css({ position: "fixed" });
    _optsMenu.hide();

    _optsMenu.mouseleave(function() {
      $(this).menu("destroy");
      $(this).hide();
    });

    $element.on("open", function(event, item, elem) {
      $scope.item = item;

      _optsMenu.show();
      _optsMenu.menu().position({
        my: "left top",
        at: "right bottom",
        of: elem
      });
      _optsMenu.menu("refresh");
    });
  }

  return {
    link: link,
    restrict: "E",
    replace: true,
    templateUrl: "templates/partials/editor/editorDropdown.html",
    scope: {
      typeNames: "=rlTypeList",
      fnNew: "&rlOnNew",
      fnRename: "&rlOnRename",
      fnMove: "&rlOnMove",
      fnDelete: "&rlOnDelete"
    }
  };
}])

//===========================================
// CONTROLLER: NewFolderModalCtrl
//===========================================
.controller("NewFolderModalCtrl", [
"$scope", "$modalInstance", "path",
function($scope, $modalInstance, path) {
  var self = this;

  self.path = path;
  self.folderName = "";

  self.ok = function() {
    $modalInstance.close(self.path + "/" + self.folderName);
  };

  self.cancel = function() {
    $modalInstance.dismiss("cancel");
  };
}])

//===========================================
// CONTROLLER: ContentEditorCtrl
//===========================================
.controller("ContentEditorCtrl", [
"layout", "backend", "$modal", "$rootScope", "$timeout", "notificationTypes",
function(layout, backend, $modal, $rootScope, $timeout, notificationTypes) {
  var self = this;

  var _activeTreeNode = null;
  var _pageLoaded = false;

  layout.bodyClass = "editor-body";
  layout.navpath = [
    { href: "/#/index", title: "CMS Home" },
    { href: "/#/editor", title: "Content Editor" }
  ];

  self.ST_OTHER = -1;
  self.ST_UPDATE = 0;
  self.ST_CREATE_NEW = 1;
  self.state = self.ST_OTHER;
  self.itemTree = {};
  self.item = null;
  self.type = null;
  self.typeList = null;
  self.sidebarLoading = false;

  //===========================================
  // _getTypeList
  //===========================================
  var _getTypeList = function() {
    backend.getTypeList().then(
      function(success) {
        self.typeList = success.data;
      },
      function(err) {
        $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
      }
    );
  };

  //===========================================
  // _loadTree
  //===========================================
  var _loadTree = function() {
    $rootScope.$broadcast("save");
    self.sidebarLoading = true;

    backend.getItemTree().then(
      function(success) {
        self.itemTree = success.data;
        self.sidebarLoading = false;

        $timeout(function() {
          $rootScope.$broadcast("restore");
        }, 0);
      },
      function(err) {
        $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
      }
    );
  };

  _getTypeList();
  _loadTree();

  //===========================================
  // _newFolder
  //===========================================
  var _newFolder = function(path) {
    var modalInstance = $modal.open({
      templateUrl: "templates/partials/editor/newFolderModal.html",
      controller: "NewFolderModalCtrl as ctrl",
      resolve: {
        path: function() {
          return path;
        }
      }
    });

    modalInstance.result.then(
      function(path) {
        backend.createFolder(path).then(
          function(success) {
            _loadTree();
            $rootScope.$broadcast("notification", notificationTypes.SUCCESS, "Operation successful");
          },
          function(err) {
            $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
          }
        );
      },
      function() {}
    );
  };

  //===========================================
  // onCancelClick
  //===========================================
  self.onCancelClick = function() {
    self.state = self.ST_OTHER;
  }

  //===========================================
  // onSaveClick
  //===========================================
  self.onSaveClick = function() {
    switch (self.state) {
      case self.ST_UPDATE:
        backend.updateItem(self.item).then(
          function(success) {
            self.item = null;
            self.type = null;
            self.state = self.ST_OTHER;

            _loadTree();
            $rootScope.$broadcast("notification", notificationTypes.SUCCESS, "Operation successful");
          },
          function(err) {
            $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
          }
        );
        break;
      case self.ST_CREATE_NEW:
        self.item.path += "/" + self.item.itemName;

        backend.createItem(self.item).then(
          function(success) {
            self.item = null;
            self.type = null;
            self.state = self.ST_OTHER;

            _loadTree();
            $rootScope.$broadcast("notification", notificationTypes.SUCCESS, "Operation successful");
          },
          function(err) {
            $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
          }
        );
        break;
    }
  };

  //===========================================
  // buttonCaption
  //===========================================
  self.buttonCaption = function() {
    return self.state === self.ST_UPDATE ? "Save changes" : "Save new";
  };

  //===========================================
  // onItemSelection
  //===========================================
  self.onItemSelection = function(item, elem) {
    if (item.typeName !== 'folder') {
      self.state = self.ST_OTHER;

      if (_activeTreeNode) {
        _activeTreeNode.removeClass("item-active");
      }

      _activeTreeNode = elem;
      _activeTreeNode.addClass("item-active");

      backend.getItem(item.path).then(
        function(success) {
          self.item = success.data;

          backend.getType(item.typeName).then(
            function(success) {
              self.type = success.data;
              self.state = self.ST_UPDATE;
            }
          );
        },
        function(err) {
          $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
        }
      );
    }
  };

  //===========================================
  // onDeleteItemClick
  //===========================================
  self.onDeleteItemClick = function(item) {
    backend.deleteItem(item.path).then(
      function(success) {
        _loadTree();
        $rootScope.$broadcast("notification", notificationTypes.SUCCESS, "Operation successful");
      },
      function(err) {
        $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
      }
    );
  };

  //===========================================
  // onMoveItemClick
  //===========================================
  self.onMoveItemClick = function(item) {
    console.log("Moving item " + item.itemName + " at " + item.path);
  };

  //===========================================
  // onRenameItemClick
  //===========================================
  self.onRenameItemClick = function(item) {
    console.log("Renaming item " + item.itemName + " at " + item.path);
  };

  //===========================================
  // onNewItemClick
  //===========================================
  self.onNewItemClick = function(item, typeName) {
    if (typeName === "folder") {
      _newFolder(item.path);
      return;
    }

    backend.getType(typeName).then(
      function(success) {
        self.type = success.data;

        self.item = new rl.Item();
        self.item.path = item.path;
        self.item.typeName = typeName;
        self.state = self.ST_CREATE_NEW;

        $rootScope.$broadcast("notification", notificationTypes.SUCCESS, "Operation successful");
      },
      function(err) {
        $rootScope.$broadcast("notification", notificationTypes.ERROR, "Server error", err);
      }
    );
  };

  //===========================================
  // onOptionsSelection
  //===========================================
  self.onOptionsSelection = function(item, elem) {
    $("#opts-menu").trigger("open", [item, elem]);
  };
}]);
