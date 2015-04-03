var app = app || {};

(function(ns) {
  "use strict";

  function DeveloperToolsXhtml() {
    var self = this;

    var _panelContent = "";

    var _view = ko.mapping.fromJS({
      fieldName: null,
      type: 0,
      widget: "textedit",
      required: false,
      defaultValue: "",

      parserParams: {},
      widgetParams: {}
    });

    var _current = {
      itemType: null,
      fieldName: null
    };

    var _update = function() {
      $("#panel").html(_panelContent);

      $("#tab-parser-params").css("min-height", $("#tab-field-def").height());
      $("#tab-widget-params").css("min-height", $("#tab-field-def").height());

      if ($("#frm-field-def").length > 0 && !ko.dataFor($("#frm-field-def")[0])) {
        ko.applyBindings(_view, $("#frm-field-def")[0]);
      }
      if ($("#frm-parser-params").length > 0 && !ko.dataFor($("#frm-parser-params")[0])) {
        ko.applyBindings(_view, $("#frm-parser-params")[0]);
      }
      if ($("#frm-widget-params").length > 0 && !ko.dataFor($("#frm-widget-params")[0])) {
        ko.applyBindings(_view, $("#frm-widget-params")[0]);
      }
    };

    /**
    * Each argument is optional, but preceeding arguments must be given.
    *
    * itemType: Only the field list is displayed
    * itemType, fieldName: The form is displayed with the correct tabs visible
    * itemType, fieldName, fieldType: The tab for type fieldType is displayed instead
    * itemType, fieldName, fieldType, fieldWidget: The tab for fieldWidget is displayed instead
    */
    var _loadPanel = function(itemType, fieldName, fieldType, fieldWidget) {
      var url = "/devtools/ajax/panelContent.xhtml";

      if (itemType != null) {
        url += "?type=" + encodeURIComponent(itemType);
      }

      if (fieldName !== null) {
        url += "&field=" + encodeURIComponent(fieldName);
      }

      if (fieldType != null) {
        url += "&jcrtype=" + encodeURIComponent(fieldType);
      }

      if (fieldWidget !== null) {
        url += "&widget=" + encodeURIComponent(fieldWidget);
      }

      console.log(url);

      $.ajax({
        url: url
      }).done(function(data) {
        _panelContent = data;
        _update();
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    var _extractData = function() {
      var data = ko.mapping.toJS(_view);

      data.type = parseInt(data.type);
/*
      data.parserParams = {};
      data.widgetParams = {};

      $.each($("#frm-parser-params").serializeArray(), function() {
        data.parserParams[this.name] = this.value;
      });

      $.each($("#frm-widget-params").serializeArray(), function() {
        data.widgetParams[this.name] = this.value;
      });*/

      return data;
    };

    var _populate = function(data) {
      var field = data.fields[_current.fieldName];
      ko.mapping.fromJS(field, _view);
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
      _current.fieldName = "[new]";
      _loadPanel(_current.itemType, _current.fieldName, null, null);
    };

    /**
    * @method btnEditFieldClick
    */
    self.btnEditFieldClick = function(field) {
      $.ajax({
        url: "/ajax/repository/type/" + encodeURIComponent(_current.itemType),
        method: "GET"
      }).done(function(data) {
        _current.fieldName = field;
        _loadPanel(_current.itemType, _current.fieldName, null, null);
        _populate(data);
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    /**
    * @method btnDeleteFieldClick
    */
    self.btnDeleteFieldClick = function(field) {
      $.ajax({
        url: "/ajax/repository/type/" + encodeURIComponent(_current.itemType) + "/field/" + encodeURIComponent(field),
        method: "DELETE"
      }).done(function(data) {
        if (field === _current.fieldName) {
          _current.fieldName = null;
          _loadPanel(_current.itemType, _current.fieldName, null, null);
        }
        else {
          _loadPanel(_current.itemType, _current.fieldName, _view.type(), _view.widget());
        }
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
/*
      $.ajax({
        url: "/ajax/repository/type/" + encodeURIComponent(_current.itemType) + "/field/" + encodeURIComponent(_current.fieldName),
        method: "PUT",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
      }).done(function() {
        _current.fieldName = null;
        _loadPanel(_current.itemType, _current.fieldName, null, null);
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });*/
    };

    /**
    * @method btnSaveNewFieldClick
    */
    self.btnSaveNewFieldClick = function() {
      var data = _extractData();
      console.log(data);
/*
      $.ajax({
        url: "/ajax/repository/type/" + encodeURIComponent(_current.itemType) + "/field",
        method: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
      }).done(function() {
        _current.fieldName = null;
        _loadPanel(_current.itemType, null, null, null);
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });*/
    };

    /**
    * @method onJcrTypeSelection
    */
    self.onJcrTypeSelection = function(elem) { // TODO Subscribe to observer instead of using onchange
      _loadPanel(_current.itemType, _current.fieldName, _view.type(), _view.widget());
    };

    /**
    * @method onWidgetSelection
    */
    self.onWidgetSelection = function(elem) {
      _loadPanel(_current.itemType, _current.fieldName, _view.type(), _view.widget());
    };

    /**
    * @method btnCancelFieldClick
    */
    self.btnCancelFieldClick = function () {
      _current.fieldName = null;
      _loadPanel(_current.itemType, null, null, null);
    };

    /**
    * @method mnuNewTypeClick
    */
    self.mnuNewTypeClick = function() {
      var typeName = $("#txtNewType").val();
      var data = {
        typeName: typeName,
        fields: {}
      };

      $.ajax({
        url: "/ajax/repository/type",
        method: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
      }).done(function() {
        _current.itemType = typeName;
        _current.fieldName = null;
        _loadPanel(_current.itemType, null, null, null);
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    /**
    * @method mnuTypeClick
    */
    self.mnuTypeClick = function(type) {
      _current.itemType = type;
      _current.fieldName = null;
      _loadPanel(_current.itemType, null, null, null);
    };
  }

  ns.developerToolsXhtml = new DeveloperToolsXhtml();
})(app);
