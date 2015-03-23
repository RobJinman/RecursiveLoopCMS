var app = app || {};

(function(ns) {
  "use strict";

  var PANEL_ID = "panel";
  var TREE_WIDGET_ID = "itemTree";

  ns.contentEditorXhtml = {
    _panel: null,
    _init: function() {
      this._panel = new ns.EditorPanel(PANEL_ID, ns.trees[TREE_WIDGET_ID]);
    },
    save: function() {
      this._panel.save();
    },
    insertNew: function() {
      console.log($("#opts-menu").attr("data-item-path"));
    }
  };

  $(function() {
    ns.onLoad(ns.contentEditorXhtml._init.bind(ns.contentEditorXhtml));
  });
})(app);
