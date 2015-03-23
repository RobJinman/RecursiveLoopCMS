var app = app || {};

(function(ns) {
  "use strict";

  function EditorPanel(id, tree) {
    var self = this;

    self.id = id;
    self.content = "";
    self.current = {};

    if (typeof tree !== "undefined" && tree !== null) {
      tree.selectItem = function(elem) {
        var data = $(elem).closest(".node-wrap").find(".item-data");
        var type = data.attr("data-item-type");
        var path = data.attr("data-item-path");

        if (type === "folder") {
          return;
        }

        $.ajax({
          url: "ajax/panelContent.xhtml?item=" + encodeURIComponent(path)
        }).done(function(data) {
          self.content = data;
          self.update();
        });

        self.update();
      };

      self.tree = tree;
    }
  }

  EditorPanel.prototype.currentItem = function(name, path, type) {
    this.current = {
      name: name,
      path: path,
      typeName: type
    };
  };

  EditorPanel.prototype.update = function() {
    $("#" + this.id).html(this.content);

    var data = $("#item-data");

    this.current = {
      name: data.attr("data-item-name"),
      path: data.attr("data-item-path"),
      typeName: data.attr("data-item-type")
    };

    $(".datepicker").datepicker();
    $(".spinner").spinner();
  };

  EditorPanel.prototype.save = function() {
    var dat = {};
    $.each($("#panel-form").serializeArray(), function() {
      dat[this.name] = this.value;
    });

    var obj = {
      name: this.current.name,
      path: this.current.path,
      typeName: this.current.typeName,
      data: dat
    };

    $.ajax({
      url: "ajax/commit",
      method: "POST",
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      data: JSON.stringify(obj)
    }).done(function(data) {
      // TODO
    });
  };

  ns.EditorPanel = EditorPanel;
})(app);
