var app = app || {};

(function(ns) {
  "use strict";

  var PANEL_ID = "panel";

  function DeveloperToolsXhtml() {
    var self = this;

    var _id = PANEL_ID;
    var _content = "";
    var _fieldDefNew = null;
    var _fieldDefUpdate = null;
    var _typeName = null;

    var _update = function() {
      $("#" + _id).html(_content);
    };

    var _extractFieldData = function(datElem) {
      return {
        name: datElem.attr("data-field-name"),
        type: parseInt(datElem.attr("data-field-type")),
        widget: datElem.attr("data-field-widget"),
        required: (datElem.attr("data-field-required") === "true"),
        default: datElem.attr("data-field-default") || ""
      };
    };

    var _loadPanel = function() {
      $.ajax({
        url: "/devtools/ajax/panelContent_updateType.xhtml?type=" + encodeURIComponent(_typeName)
      }).done(function(data) {
        _content = data;
        _update();
        _fieldDefNew.hide();
        _fieldDefUpdate.hide();
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    /**
    * @method init
    */
    self.init = function() {
      _fieldDefUpdate = new ns.FieldDef($("#field-def-update"));
      _fieldDefNew = new ns.FieldDef($("#field-def-new"));

      _fieldDefUpdate.hide();
      _fieldDefNew.hide();
    };

    /**
    * @method btnNewFieldClick
    */
    self.btnNewFieldClick = function() {
      _fieldDefUpdate.hide();

      _fieldDefNew.populate({});
      _fieldDefNew.show();
    };

    /**
    * @method btnEditFieldClick
    */
    self.btnEditFieldClick = function(elem) {
      _fieldDefNew.hide();

      var data = _extractFieldData($(elem).closest("tr").find(".field-data"));

      _fieldDefUpdate.populate(data);
      _fieldDefUpdate.show();
    };

    /**
    * @method btnDeleteFieldClick
    */
    self.btnDeleteFieldClick = function(elem) {
      var data = _extractFieldData($(elem).closest("tr").find(".field-data"));

      $.ajax({
        url: "/ajax/repository/type/" + encodeURIComponent(_typeName) + "/field/" + encodeURIComponent(data.name),
        method: "DELETE"
      }).done(function(data) {
        _loadPanel();
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    /**
    * @method btnUpdateFieldClick
    */
    self.btnUpdateFieldClick = function() {
      var data = {
        name: _typeName,
        fields: {}
      };

      $("tr .field-data").each(function() {
        var d = _extractFieldData($(this));
        data.fields[d.name] = d;
      });

      var field = _fieldDefUpdate.getData();
      data.fields[field.name] = field;

      console.log(data);

      $.ajax({
        url: "/ajax/repository/type",
        method: "PUT",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
      }).done(function() {
        _loadPanel();
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    /**
    * @method btnCancelFieldClick
    */
    self.btnCancelFieldClick = function () {
      _fieldDefNew.hide();
      _fieldDefUpdate.hide();
    };

    /**
    * @method btnSaveNewFieldClick
    */
    self.btnSaveNewFieldClick = function() {
      var data = {
        name: _typeName,
        fields: {}
      };

      $("tr .field-data").each(function() {
        var d = _extractFieldData($(this));
        data.fields[d.name] = d;
      });

      var field = _fieldDefNew.getData();
      data.fields[field.name] = field;

      console.log(data);

      $.ajax({
        url: "/ajax/repository/type",
        method: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
      }).done(function() {
        _loadPanel();
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    /**
    * @method mnuNewTypeClick
    */
    self.mnuNewTypeClick = function() {
      console.log("New type");
    };

    /**
    * @method mnuTypeClick
    */
    self.mnuTypeClick = function(type) {
      _typeName = type;
      _loadPanel();
    };
  }

  ns.developerToolsXhtml = new DeveloperToolsXhtml();
})(app);
