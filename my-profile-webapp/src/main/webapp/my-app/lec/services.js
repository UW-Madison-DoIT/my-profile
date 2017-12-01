'use strict';

define(['angular'], function(angular) {
    var app = angular.module('my-app.lec.services', []);

    app.factory('errorService', function($rootScope) {
      var sendError = function(status, section, isWrite) {
        var title = 'Sorry!';
        var message = '';
        var note = '';
        if (status === 403) {
          message = 'You do not have access to this module. If this is incorrect please contact your supervisor.';
        } else if (status === 503) {
          message = 'The service is temporarily unavailable. If this problem persists, please contact the Help Desk.';
        } else {
          message = 'There was a problem ' +
            ((isWrite) ? 'saving' : 'looking up') +
            ' the ' + ((section) ? section : '') +
            ' information. If this problem persists, please contact the Help Desk.';

          if (isWrite) {
            note = 'If your address contains any non-English characters (accented letters or umlauts), please re-enter it with the closest English equivalent.';
          }
        }
        $rootScope.$emit('alert', { title: title, msg: message, note: note });
      };

      return {
        sendError : sendError
      }
    });

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

        var getEmergencyPhoneNumber = function(){
            return $http.get('/profile/api/emergencyPhoneNumber/get.json').success(
                function(data, status){
                    return data;
                }).error(function(data, status){
                    miscService.redirectUser(status, "Get emergency phone number");
                });
        };

        var saveEmergencyPhoneNumber = function(emergencyPhoneNumber){
            return $http.post('/profile/api/emergencyPhoneNumber/set',emergencyPhoneNumber).success(
                function(data, status) { //success function
                    return data;
                }).error(function(data, status) { // failure function
                    miscService.redirectUser(status, "Set emergency phone number");
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
          getEmergencyPhoneNumber : getEmergencyPhoneNumber,
          saveEmergencyPhoneNumber : saveEmergencyPhoneNumber,
          searchUsers : searchUsers,
          searchLocalContactInfo : searchLocalContactInfo
        }
      });
});
