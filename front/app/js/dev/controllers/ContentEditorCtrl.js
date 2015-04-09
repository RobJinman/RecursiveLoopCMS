/**
* (CONTROLLER) The controller for the content editor view
*
* @namespace App
* @class ContentEditorCtrl
* @constructor
*/
angular.module("Controllers")

.controller("ModalInstanceCtrl", [
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

.controller("ContentEditorCtrl", [
"layout", "backend", "$rootScope", "$modal",
function(layout, backend, $rootScope, $modal) {
  var ST_UPDATE = 0;
  var ST_CREATE_NEW = 1;

  var self = this;

  var _activeTreeNode = null;
  var _pageLoaded = false;
  var _optsMenu = $("#opts-menu");
  var _optsMenuItem = null;
  var _state = ST_UPDATE;

  layout.bodyClass = "editor-body";
  self.itemTree = {};
  self.item = null;
  self.type = null;
  self.typeList = [];

  self.onNewFolderClick = function() {
    var modalInstance = $modal.open({
      templateUrl: "templates/partials/editor/newFolderModal.html",
      controller: "ModalInstanceCtrl as ctrl",
      resolve: {
        path: function() {
          return _optsMenuItem.path;
        }
      }
    });

    modalInstance.result.then(
      function(path) {
        console.log("Creating new folder '" + path + "'.");
      },
      function() {
        console.log("Cancelled");
      }
    );
  };

  _optsMenu.css({ position: "fixed" });
  _optsMenu.hide();

  _optsMenu.mouseleave(function() {
    $(this).menu("destroy");
    $(this).hide();
  });

  backend.getTypeList().then(
    function(success) {
      self.typeList = success.data;
    },
    function(err) {
      console.log(err);
    }
  );

  self.onSaveClick = function() {
    // TODO
    console.log("Saving...");
  };

  self.buttonCaption = function() {
    return _state == ST_UPDATE ? "Save changes" : "Save new";
  };

  self.chooseWidget = function(field) {
    return "templates/partials/widgets/" + field.widget + ".html";
  };

  self.onItemSelection = function(item, elem) {
    if (item.typeName !== 'folder') {
      if (_activeTreeNode) {
        _activeTreeNode.removeClass("item-active");
      }

      _activeTreeNode = elem;
      _activeTreeNode.addClass("item-active");

      backend.getItem(item.path).then(
        function(success) {
          self.item = success.data;
          _state = ST_UPDATE;
        },
        function(err) {
          console.log(err);
        }
      );

      backend.getType(item.typeName).then(
        function(success) {
          self.type = success.data;
        },
        function(err) {
          console.log(err);
        }
      );
    }
  };

  self.onOptionsSelection = function(item, elem) {
    _optsMenuItem = item;

    _optsMenu.show();
    _optsMenu.menu().position({
      my: "left top",
      at: "right bottom",
      of: elem
    });
    _optsMenu.menu("refresh");
  };

  backend.getItemTree().then(
    function(success) {
      self.itemTree = success.data;
    },
    function(err) {
      console.log(err);
    }
  );
}]);
