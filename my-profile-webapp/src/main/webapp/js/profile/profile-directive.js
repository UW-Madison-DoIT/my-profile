'use strict';

(function() {
  var app = angular.module('portal.profile.directives', []);

  app.directive('contactInfo', function() {
      return {
        restrict : 'E',
        templateUrl : 'partials/contact-info.html'
      }
    });
})();
