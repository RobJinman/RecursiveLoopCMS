var app = app || {};

(function(ns) {
  "use strict";

  var PANEL_ID = "panel";

  function DeveloperToolsXhtml() {
    var self = this;

    var _id = PANEL_ID;
    var _content = "";
    var _current = {
      type: null,
      field: null,

      fieldData: {
        fName: ko.observable(null),
        fType: ko.observable(0),
        fWidget: ko.observable("textedit"),
        fRequired: ko.observable(false),
        fDefault: ko.observable("")
      }
    };

    var _update = function() {
      $("#" + _id).html(_content);

      var frmFieldDef = $("#frm-field-def");
      if (frmFieldDef.length > 0) {
        ko.applyBindings(_current.fieldData, frmFieldDef[0]);
      }
    };

    var _loadPanel = function() {
      var url = "/devtools/ajax/panelContent.xhtml";

      if (_current.type != null) {
        url += "?type=" + encodeURIComponent(_current.type);
      }

      if (_current.field !== null) {
        url += "&field=" + encodeURIComponent(_current.field);
      }

      if (_current.fieldData.fType() != null) {
        url += "&jcrtype=" + encodeURIComponent(_current.fieldData.fType());
      }

      if (_current.fieldData.fWidget() !== null) {
        url += "&widget=" + encodeURIComponent(_current.fieldData.fWidget());
      }

      console.log("url: " + url);

      $.ajax({
        url: url
      }).done(function(data) {
        _content = data;
        _update();
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    var _extractData = function() {
      var data = {
        name: _current.fieldData.fName(),
        type: parseInt(_current.fieldData.fType()),
        widget: _current.fieldData.fWidget(),
        required: _current.fieldData.fRequired(),
        default: _current.fieldData.fDefault(),

        parserParams: {},
        widgetParams: {}
      };

      $.each($("#frm-parser-params").serializeArray(), function() {
        data.parserParams[this.name] = this.value;
      });

      $.each($("#frm-widget-params").serializeArray(), function() {
        data.widgetParams[this.name] = this.value;
      });

      return data;
    };

    /**
    * @method init
    */
    self.init = function() {
    };

    /**
    * @method btnNewFieldClick
    */
    self.btnNewFieldClick = function() {
      _current.field = "[new]";
      _loadPanel();
    };

    /**
    * @method btnEditFieldClick
    */
    self.btnEditFieldClick = function(field) {
      _current.field = field;
      _loadPanel();
    };

    /**
    * @method btnDeleteFieldClick
    */
    self.btnDeleteFieldClick = function(field) {
      $.ajax({
        url: "/ajax/repository/type/" + encodeURIComponent(_current.type) + "/field/" + encodeURIComponent(field),
        method: "DELETE"
      }).done(function(data) {
        if (field === _current.field) {
          _current.field = null;
        }
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
      var data = _extractData();

      console.log(data);

      $.ajax({
        url: "/ajax/repository/type/" + encodeURIComponent(_current.type) + "/field/" + encodeURIComponent(_current.field),
        method: "PUT",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
      }).done(function() {
        _current.field = null;
        _loadPanel();
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    /**
    * @method onJcrTypeSelection
    */
    self.onJcrTypeSelection = function(elem) {
      _current.fieldData.fType($(elem).val());
      _loadPanel();
    };

    /**
    * @method onWidgetSelection
    */
    self.onWidgetSelection = function(elem) {
      _current.fieldData.fWidget($(elem).val());
      _loadPanel();
    };

    /**
    * @method btnCancelFieldClick
    */
    self.btnCancelFieldClick = function () {
      _current.field = null;
      _loadPanel();
    };

    /**
    * @method btnSaveNewFieldClick
    */
    self.btnSaveNewFieldClick = function() {
      var data = _extractData();

      console.log(data);

      $.ajax({
        url: "/ajax/repository/type/" + encodeURIComponent(_current.type) + "/field",
        method: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
      }).done(function() {
        _current.field = null;
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
      var typeName = $("#txtNewType").val();
      var data = {
        name: typeName,
        fields: {}
      };

      $.ajax({
        url: "/ajax/repository/type",
        method: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
      }).done(function() {
        _current.type = typeName;
        _loadPanel();
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    /**
    * @method mnuTypeClick
    */
    self.mnuTypeClick = function(type) {
      _current.type = type;
      _loadPanel();
    };
  }

  ns.developerToolsXhtml = new DeveloperToolsXhtml();
})(app);
