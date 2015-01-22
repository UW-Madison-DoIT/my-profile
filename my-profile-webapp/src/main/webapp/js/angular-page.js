(function() {
 var app = angular.module('portal', [
    'ngRoute',
    'ngStorage',
    'ui.bootstrap',
    'ngSanitize',
    'ui.sortable',
    'portal.misc.controllers',
    'portal.misc.directives',
    'portal.misc.filters',
    'portal.misc.service',
    'portal.main.controllers',
    'portal.main.service',
    'portal.main.directives',
    'portal.profile.directives',
    'portal.profile.controllers',
    'portal.profile.service'
     ]);
 app.config(['$routeProvider',function($routeProvider, $locationProvider) {
	 $routeProvider.
      when('/settings', {templateUrl: 'partials/settings.html'}).
      otherwise({templateUrl: 'partials/main.html'});
      }
 	]);



})();
