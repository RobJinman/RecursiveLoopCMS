var app = app || {};

/**
* @module app
*/
(function(ns) {
  "use strict";

  ns.init = function() {
    var self = this;

    self.Tree.makeTrees();
    self.page.init();
  };
})(app);

$(function() {
  app.init();
});
