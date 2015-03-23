var app = app || {};

/**
* @module app
*/
(function(ns) {
  "use strict";

  var _initialised = false;
  var _onLoad = [];

  /**
  * When called after page load like so
  * $(function() { app.onLoad(fn); });
  * this ensures that app.init() runs before fn.
  *
  * @method onLoad
  */
  ns.onLoad = function(fn) {
    if (_initialised = true) {
      fn();
    }
    else {
      _onLoad.push(fn);
    }
  };

  var init = function() {
    // Do stuff ...

    for (fn in _onLoad) {
      fn();
    }

    _initialised = true;
  };

  $(function() {
    init();
  });
})(app);
