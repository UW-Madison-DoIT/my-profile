'use strict';

(function() {
    var app = angular.module('portal.profile.service', []);
    
    app.factory('contactService', function($http, miscService) {
      var contactInfoPromise = $http.get('/profile/samples/contact-info.json');
    
      var getContactInfo = function() {
          return contactInfoPromise.success(
                  function(data, status) { //success function
                      return data.contactInfo;
                  }).error(function(data, status) { // failure function
                  miscService.redirectUser(status, "Get contact info");
                });
              }
    
      return {
        getContactInfo : getContactInfo
      }
    });
})();