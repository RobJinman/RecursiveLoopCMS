/**
* @module App
* @requires ngRoute
* @requires ngCookies
* @requires jlUtil
* @requires jlBackend
* @requires jlLayout
* @requires jlScroll
*/
var app = angular.module("App", [
  "ui.bootstrap",
  "ngRoute",
  "ngCookies",
  "jlUtil",
  "jlScroll",
  "jlDirectives"])

  .config(["$logProvider", function($logProvider) {
    $logProvider.debugEnabled(true);
  }])

  .config(["$routeProvider", "$locationProvider", function($routeProvider, $locationProvider) {
    $routeProvider.when("/index", {
      templateUrl: "templates/views/index_vw.html",
      controller: "IndexCtrl",
      controllerAs: "ctrl"
    })
    .otherwise({
      redirectTo: "/index"
    });

//    $locationProvider.html5Mode(true);
  }]);
