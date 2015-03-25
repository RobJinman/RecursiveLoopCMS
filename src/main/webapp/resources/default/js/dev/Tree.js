var app = app || {};

(function(ns) {
  "use strict";

  function Tree(id) {
    this.id = id;

    this.selectItem = function(type, id) {};
  };

  Tree.makeTrees = function() {
    ns.trees = {};
    $(".tree").each(function() {
      ns.trees[this.id] = new ns.Tree(this.id);
    });

    var par = $(".tree li:has(ul)");
    par.addClass("parent-li");

    var ch = par.find("> ul > li");
    ch.hide();

    $(".tree .parent-li > .node-wrap > .node").on("click", function() {
      var children = $(this).closest(".parent-li").find("> ul > li");

      if (children.is(":visible")) {
        children.hide("fast");
      }
      else {
        children.show("fast");
      }
    });

    var optsMenu = $("#opts-menu");
    optsMenu.css({ position: "fixed" });
    optsMenu.hide();

    $(".opts").on("click", function() {
      var data = $(this).closest(".node-wrap").find(".item-data");
      optsMenu.attr("data-item-path", data.attr("data-item-path"));

      optsMenu.show();
      optsMenu.menu().position({
        my: "left top",
        at: "right bottom",
        of: $(this)
      });
      optsMenu.menu("refresh");
    });

    optsMenu.mouseleave(function() {
      $(this).menu("destroy");
      $(this).hide();
    });
  };

  ns.Tree = Tree;
})(app);
