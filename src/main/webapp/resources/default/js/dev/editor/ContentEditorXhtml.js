var app = app || {};

(function(ns) {
  "use strict";

  var PANEL_ID = "panel";
  var TREE_WIDGET_ID = "itemTree";

  function ContentEditorXhtml() {
    var self = this;

    var _id = PANEL_ID;
    var _content = "";
    var _current = {};
    var _tree = null;

    var _setCurrentItem = function(name, path, type) {
      _current = {
        name: name,
        path: path,
        typeName: type
      };
    };

    var _update = function() {
      $("#" + _id).html(_content);

      var data = $("#item-data");

      _current = {
        name: data.attr("data-item-name"),
        path: data.attr("data-item-path"),
        typeName: data.attr("data-item-type")
      };

      $(".datepicker").datepicker();
      $(".spinner").spinner();
    };

    /**
    * @method init
    */
    self.init = function() {
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
    * @method btnUpdateItemClick
    */
    self.btnUpdateItemClick = function() {
      var content = {};
      $.each($("#frm-item-content").serializeArray(), function() {
        content[this.name] = this.value;
      });

      var obj = {
        name: _current.name,
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
        name: info.name,
        path: info.parent + "/" + info.name,
        typeName: info.type,
        data: content
      };

      $.ajax({
        url: "/ajax/repository/item",
        method: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(obj)
      }).done(function(data) {
        // TODO
      });
    };

    /**
    * @method mnuInsertItemClick
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
      });
    };
  }

  ns.contentEditorXhtml = new ContentEditorXhtml();
})(app);
