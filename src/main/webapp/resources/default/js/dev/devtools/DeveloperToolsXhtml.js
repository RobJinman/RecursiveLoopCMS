var app = app || {};

(function(ns) {
  "use strict";

  var PANEL_ID = "panel";

  function DeveloperToolsXhtml() {
    var self = this;

    var _id = PANEL_ID;
    var _content = "";
    var _fieldDef = null;

    var _update = function() {
      $("#" + _id).html(_content);
    };

    self.init = function() {
      _fieldDef = $("#field-def");
      _fieldDef.hide();
    };

    self.btnNewFieldClick = function() {
      _fieldDef.show();
    };

    self.btnEditFieldClick = function(elem) {
      var data = $(elem).closest(".field-buttons").find(".field-data");
      var name = data.attr("data-field-name");
      var type = data.attr("data-field-type");
      var widget = data.attr("data-field-widget");
      var required = data.attr("data-field-required");
      var def = data.attr("data-field-default");

      console.log("Edit field: " + name + ", " + type + ", " + widget + ", " + required + ", " + def);
    };

    self.btnDeleteFieldClick = function(elem) {
      console.log("Delete field. " + elem.id);
    };

    self.mnuNewTypeClick = function() {
      console.log("New type");
    };

    self.mnuTypeClick = function(type) {
      console.log("type: " + type);

      $.ajax({
        url: "/devtools/ajax/panelContent_updateType.xhtml?type=" + encodeURIComponent(type)
      }).done(function(data) {
        _content = data;
        _update();
      });
    };
  }

  ns.developerToolsXhtml = new DeveloperToolsXhtml();
})(app);
