'use strict';

(function() {
  var app = angular.module('portal.profile.directives', []);

  app.directive('contactInfo', function() {
      return {
        restrict : 'E',
        templateUrl : 'partials/contact-info.html'
      }
    });
  
  app.directive('basicInfo', function() {
      return {
        restrict : 'E',
        templateUrl : 'partials/basic-info.html'
      }
    });
  
  app.directive('emergencyInfo', function() {
      return {
        restrict : 'E',
        templateUrl : 'partials/emergency-info.html'
      }
    });
  
})();
