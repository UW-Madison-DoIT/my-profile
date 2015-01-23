'use strict';

(function() {
    var app = angular.module('portal.profile.service', []);
    
    app.factory('profileService', function($http, miscService) {
      var contactInfoPromise = $http.get('/profile/samples/contact-info.json');
      var basicInfoPromise = $http.get('/profile/samples/basic-info.json');
    
      var getContactInfo = function() {
          return contactInfoPromise.success(
             function(data, status) { //success function
                 return data.contactInfo;
             }).error(function(data, status) { // failure function
             miscService.redirectUser(status, "Get contact info");
          });
      }
      
      var getBasicInfo = function() {
          return basicInfoPromise.success(
             function(data, status) { //success function
                 return data.basicInfo;
             }).error(function(data, status) { // failure function
             miscService.redirectUser(status, "Get basic info");
          });
      }
    
      return {
        getContactInfo : getContactInfo,
        getBasicInfo   : getBasicInfo
      }
    });
})();