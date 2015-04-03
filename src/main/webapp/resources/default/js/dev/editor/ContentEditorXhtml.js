var app = app || {};

(function(ns) {
  "use strict";

  var TREE_WIDGET_ID = "itemTree";

  function ContentEditorXhtml() {
    var self = this;

    var _sidebarContent = "";
    var _content = "";
    var _current = {};
    var _tree = null;

    var _setCurrentItem = function(itemName, path, type) {
      _current = {
        itemName: itemName,
        path: path,
        typeName: type
      };
    };

    var _reloadSidebar = function() {
      $.ajax({
        url: "ajax/sidebarContent.xhtml"
      }).done(function(data) {
        _sidebarContent = data;
        _update();
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    var _update = function() {
      $("#panel").html(_content);
      $("#sidebar").html(_sidebarContent);

      var data = $("#item-data");

      _current = {
        itemName: data.attr("data-item-name"),
        path: data.attr("data-item-path"),
        typeName: data.attr("data-item-type")
      };

      $(".datepicker").datepicker();
      $(".spinner").spinner();
      $('[data-toggle="popover"]').popover();

      ns.Tree.makeTrees();

      _tree = ns.trees[TREE_WIDGET_ID];

      if (typeof _tree !== "undefined" && _tree !== null) {
        _tree.selectItem = function(elem) {
          var data = $(elem).closest(".node-wrap").find(".item-data");
          var type = data.attr("data-item-type");
          var path = data.attr("data-item-path");

          if (type === "folder") {
            return;
          }

          $.ajax({
            url: "ajax/panelContent_updateItem.xhtml?item=" + encodeURIComponent(path)
          }).done(function(data) {
            _content = data;
            _update();
          });

          _update();
        };
      }
    };

    /**
    * @method init
    */
    self.init = function() {
      _reloadSidebar();
    };

    /**
    * @method btnUpdateItemClick
    */
    self.btnUpdateItemClick = function() {
      var content = {};
      $.each($("#frm-item-content").serializeArray(), function() {
        content[this.name] = this.value;
      });

      var obj = {
        itemName: _current.itemName,
        path: _current.path,
        typeName: _current.typeName,
        data: content
      };

      console.log(obj);

      $.ajax({
        url: "/ajax/repository/item",
        method: "PUT",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(obj)
      }).done(function(data) {
        // TODO
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    /**
    * @method btnNewItemClick
    */
    self.btnNewItemClick = function() {
      var content = {};
      $.each($("#frm-item-content").serializeArray(), function() {
        content[this.name] = this.value;
      });

      var info = {};
      $.each($("#frm-item-info").serializeArray(), function() {
        info[this.name] = this.value;
      });

      var obj = {
        itemName: info.itemName,
        path: info.parent + "/" + info.itemName,
        typeName: info.type,
        data: content
      };

      $.ajax({
        url: "/ajax/repository/item",
        method: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(obj)
      }).done(function(data) {
        // TODO
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    /**
    * @method insertNewFolder
    */
    self.insertNewFolder = function() {
      var dat = $("#opts-menu");
      var path = dat.attr("data-item-path");
      var folderName = $("#txt-folder-name").val();

      var obj = {
        itemName: folderName,
        path: path + "/" + folderName,
        typeName: "folder",
        data: {}
      };

      $.ajax({
        url: "/ajax/repository/item",
        method: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(obj)
      }).done(function(data) {
        // TODO
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    /**
    * @method mnuInsertNewItemClick
    */
    self.mnuInsertNewItemClick = function(type) {
      var dat = $("#opts-menu");
      var path = dat.attr("data-item-path");

      $.ajax({
        url: "ajax/panelContent_newItem.xhtml?type=" + encodeURIComponent(type) + "&parent=" + encodeURIComponent(path)
      }).done(function(data) {
        _content = data;
        _update();
      });
    };

    /**
    * @method mnuRenameItemClick
    */
    self.mnuRenameItemClick = function() {

    };

    /**
    * @method mnuMoveItemClick
    */
    self.mnuMoveItemClick = function() {

    };

    /**
    * @method mnuDeleteItemClick
    */
    self.mnuDeleteItemClick = function() {
      var dat = $("#opts-menu");
      var path = dat.attr("data-item-path");

      $.ajax({
        url: "/ajax/repository/item/" + encodeURIComponent(path),
        method: "DELETE"
      }).done(function(data) {
        // TODO
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };
  }

  ns.contentEditorXhtml = new ContentEditorXhtml();
})(app);
