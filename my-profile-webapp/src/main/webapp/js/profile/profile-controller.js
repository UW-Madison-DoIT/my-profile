'use strict';

(function() {
  var app = angular.module('portal.profile.controllers', []);

  app.controller('ContactInformationController', ['$localStorage','$scope', 'contactService', function($localStorage, $scope, contactService) {
    $scope.contactInfo = [];
    contactService.getContactInfo().then(function(result){
        $scope.contactInfo = result.data.contactInfo;
    });
    
  } ]);

})();
