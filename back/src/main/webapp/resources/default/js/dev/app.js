var app = app || {};

/**
* @module app
*/
(function(ns) {
  "use strict";

  ns.init = function() {
    var self = this;

    self.Tree.makeTrees();

    if (typeof self.current !== "undefined") {
      self.page = self[self.current];
      self.page.init();
    }
  };
})(app);

$(function() {
  app.init();
});
