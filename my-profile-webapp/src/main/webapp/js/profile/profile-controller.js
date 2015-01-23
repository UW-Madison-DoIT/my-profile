'use strict';

(function() {
  var app = angular.module('portal.profile.controllers', []);

  app.controller('ContactInformationController', ['$localStorage','$scope', 'profileService', function($localStorage, $scope, profileService) {
    $scope.contactInfo = [];
    profileService.getContactInfo().then(function(result){
        $scope.contactInfo = result.data.contactInfo;
    });
    
  } ]);
  
  app.controller('BasicInformationController', ['$localStorage','$scope', 'profileService', function($localStorage, $scope, profileService) {
      $scope.basicInfo = [];
      profileService.getBasicInfo().then(function(result){
          $scope.basicInfo = result.data.basicInfo;
      });
      
    } ]);

})();
