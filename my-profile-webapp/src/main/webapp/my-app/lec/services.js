'use strict';

define(['angular'], function(angular) {
    var app = angular.module('my-app.lec.services', []);
    
    app.factory('optionService', function($http, miscService) {
        
        var getOptions = function(key) {
            return $http.get('/profile/json/' + key + '.json', {cache: true}).success(
                function(data, status) { //success function
                    return data;
                }).error(function(data, status) { // failure function
                miscService.redirectUser(status, "Get options for " + key + " info");
             });
        }
        
        return {
            getOptions : getOptions
        };
    });
    
    app.factory('lecService', function($http, miscService) {
        
        //local contact
        var getLocalContactInfo = function() {
            return $http.get('/profile/api/localContactInfo/get.json').success(
               function(data, status) { //success function
                   return data;
               }).error(function(data, status) { // failure function
               miscService.redirectUser(status, "Get local contact info");
            });
        }
        
        var saveLocalContactInfo = function (contactInfo) {
            contactInfo.lastModified = null;
            return $http.post('/profile/api/localContactInfo/set',contactInfo).success(
                function(data, status) { //success function
                    return data;
                }).error(function(data, status) { // failure function
                miscService.redirectUser(status, "Get local contact info");
             });
        };
        
        //emergency
        var getEmergencyContactInfo = function() {
            return $http.get('/profile/api/emergencyContactInfo/get.json').success(
               function(data, status) { //success function
                   return data;
               }).error(function(data, status) { // failure function
               miscService.redirectUser(status, "Get emergency contact info");
            });
        }

        var saveEmergencyContactInfo = function (emergencyInfo) {
            angular.forEach(emergencyInfo, function(value, key, obj){
                value.lastModified = null;
            });
            return $http.post('/profile/api/emergencyContactInfo/set',emergencyInfo).success(
                function(data, status) { //success function
                    return data;
                }).error(function(data, status) { // failure function
                miscService.redirectUser(status, "Set emergency contact info");
             });
        };
        
        //search
        
        var searchUsers = function(firstName, lastName){
          var searchTerms = {"firstName":firstName, "lastName":lastName};
          return $http.get('/profile/api/localContactInfo/searchUsers?searchTerms=' + JSON.stringify(searchTerms)).success(
            function(data, status) { //success function
              return data.data;
            }).error(function(data, status) { // failure function
              miscService.redirectUser(status, "Search admin local contact info");
            });
          };
        
        var searchLocalContactInfo = function(netIdToLookup) {
            return $http.get('/profile/api/localContactInfo/adminLookup?netId=' + netIdToLookup).success(
                function(data, status) { //success function
                    return data;
                }).error(function(data, status) { // failure function
                miscService.redirectUser(status, "Search admin local contact info");
            });
        };
     
        return {
          getLocalContactInfo : getLocalContactInfo,
          saveLocalContactInfo : saveLocalContactInfo,
          getEmergencyContactInfo : getEmergencyContactInfo,
          saveEmergencyContactInfo : saveEmergencyContactInfo,
          searchUsers : searchUsers,
          searchLocalContactInfo : searchLocalContactInfo
        }
      });
});