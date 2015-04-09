/**
* @module App
* @requires ui.bootstrap
* @requires ngRoute
* @requires ngCookies
* @requires jlUtil
* @requires jlBackend
* @requires jlLayout
* @requires jlScroll
* @requires jlDirectives
* @requires Controllers
* @requires Directives
* @requires Backend
* @requires Layout
* @requires Tree
*/
var app = angular.module("App", [
  "ui.bootstrap",
  "ngRoute",
  "ngCookies",
  "jlUtil",
  "jlScroll",
  "jlDirectives",
  "Controllers",
  "Directives",
  "Backend",
  "Layout",
  "Tree"])

  .config(["$logProvider", function($logProvider) {
    $logProvider.debugEnabled(true);
  }])

  .config(["backendProvider", function(backendProvider) {
    backendProvider.url = "http://localhost:9090/api";
  }])

  .config(["$httpProvider", function($httpProvider) {
    $httpProvider.defaults.withCredentials = true;
  }])

  .config(["$routeProvider", function($routeProvider) {
    $routeProvider.when("/index", {
      templateUrl: "templates/views/index_vw.html",
      controller: "IndexCtrl as ctrl",
      resolve: {
        auth: ["session", function(session) {
          return session.authResolver(["Administrator", "Developer", "Author"], "/login");
        }]
      }
    }).when("/editor", {
      templateUrl: "templates/views/editor_vw.html",
      controller: "ContentEditorCtrl as ctrl",
      resolve: {
        auth: ["session", function(session) {
          return session.authResolver(["Administrator", "Author"], "/index");
        }]
      }
    }).when("/devtools", {
      templateUrl: "templates/views/devtools_vw.html",
      controller: "DeveloperToolsCtrl as ctrl",
      resolve: {
        auth: ["session", function(session) {
          return session.authResolver(["Administrator", "Developer"], "/index");
        }]
      }
    }).when("/login", {
      templateUrl: "templates/views/login_vw.html",
      controller: "LoginCtrl as ctrl"
    })
    .otherwise({
      redirectTo: "/index"
    });

//    $locationProvider.html5Mode(true);
  }]);

angular.module("Controllers", []);
angular.module("Directives", []);
