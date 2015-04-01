var app = app || {};

(function(ns) {
  "use strict";

  var DEFAULT_NAME = "";
  var DEFAULT_TYPE = 1;
  var DEFAULT_WIDGET = "textedit";
  var DEFAULT_REQUIRED = true;
  var DEFAULT_DEFAULT = "";

  ns.FieldDef = function(elem) {
    var self = this;

    var _element = $(elem);
    var _eName = _element.find("#field-def-name");
    var _eType = _element.find("#field-def-type");
    var _eWidget = _element.find("#field-def-widget");
    var _eRequired = _element.find("#field-def-required");
    var _eDefault = _element.find("#field-def-default");

    var _eParserParamsWrap = _element.find("#frm-parser-params-wrap");
    var _eWidgetParamsWrap = _element.find("#frm-widget-params-wrap");

    var _fetchParserParamsForm = function(type) {
      $.ajax({
        url: "/devtools/ajax/parserParams.xhtml?type=" + encodeURIComponent(type)
      }).done(function(data) {
        _eParserParamsWrap.html(data);
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    var _fetchWidgetParamsForm = function(widget) {
      $.ajax({
        url: "/devtools/ajax/widgetParams.xhtml?widget=" + encodeURIComponent(widget)
      }).done(function(data) {
        _eWidgetParamsWrap.html(data);
      }).fail(function(xhr, status, error){
        console.log("Status: " + status + " Error: " + error);
        console.log(xhr);
      });
    };

    _eType.on("change", function() { _fetchParserParamsForm($(this).val()); });
    _eWidget.on("change", function() { _fetchWidgetParamsForm($(this).val()); });

    var _name = DEFAULT_NAME;
    var _type = DEFAULT_TYPE;
    var _widget = DEFAULT_WIDGET;
    var _required = DEFAULT_REQUIRED;
    var _default = DEFAULT_DEFAULT;
    var _parserParams = {};
    var _widgetParams = {};

    var _putChanges = function() {
      _eName.val(_name);
      _eType.val(_type);
      _eWidget.val(_widget);
      _eRequired[0].checked = _required;
      _eDefault.val(_default);
    };

    var _pullChanges = function() {
      _name = _eName.val();
      _type = parseInt(_eType.val());
      _widget = _eWidget.val();
      _required = _eRequired[0].checked;
      _default = _eDefault.val();
      _parserParams = {};
      _widgetParams = {};

      $.each($("#frm-parser-params").serializeArray(), function() {
        _parserParams[this.name] = this.value;
      });

      $.each($("#frm-widget-params").serializeArray(), function() {
        _widgetParams[this.name] = this.value;
      });
    };

    /**
    * @method show
    */
    self.show = function() {
      _element.show();

      _pullChanges();

      _fetchParserParamsForm(_type);
      _fetchWidgetParamsForm(_widget);
    };

    /**
    * @method hide
    */
    self.hide = function() {
      _element.hide();
    };

    /**
    * @method populate
    */
    self.populate = function(data) {
      _name = (typeof data["name"] !== "undefined") ? data["name"] : DEFAULT_NAME;
      _type = (typeof data["type"] !== "undefined") ? data["type"] : DEFAULT_TYPE;
      _widget = (typeof data["widget"] !== "undefined") ? data["widget"] : DEFAULT_WIDGET;
      _required = (typeof data["required"] !== "undefined") ? data["required"] : DEFAULT_REQUIRED;
      _default = (typeof data["default"] !== "undefined") ? data["default"] : DEFAULT_DEFAULT;

      _putChanges();
    };

    /**
    * @method getData
    */
    self.getData = function() {
      _pullChanges();

      return {
        name: _name,
        type: _type,
        widget: _widget,
        required: _required,
        default: _default,
        parserParams: _parserParams,
        widgetParams: _widgetParams
      };
    };
  };
})(app);
