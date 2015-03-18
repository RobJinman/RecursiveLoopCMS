$(function() {
  var ul = $(".tree li:has(ul)"); // Find nodes with children
  ul.addClass("parent-li");
  ul.find("> ul > li").hide()     // Hide children
  ul.find("> span").prepend("<i class='glyphicon glyphicon-folder-open'></i>");

  $(".tree li.parent-li > span").on("click", function() {
    var children = $(this).parent("li.parent-li").find("> ul > li");

    if (children.is(":visible")) {
      children.hide("fast");
    }
    else {
      children.show("fast");
    }
  });
});
