/*
 * Copyright 2015, Board of Regents of the University of
 * Wisconsin System. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Board of Regents of the University of Wisconsin
 * System licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
'use strict';

define(['angular'], function(angular) {
  var app = angular.module('my-app.lec.controllers', []);

  app.controller('MainController', ['$scope', '$location', function($scope, $location) {
    $scope.currentNavItem = $location.path();
  }]);

  app.controller('LocalContactAdminController', ['$scope', '$q', 'lecService', 'errorService', function($scope, $q, lecService, errorService){
      //init function
      var init = function() {
          $scope.error = "";
          $scope.empty = false;
          $scope.searching = false;
          $scope.result = [];
          $scope.firstName ="";
          $scope.lastName = "";
          $scope.searchResultLimitIncrementor=10;
          $scope.searchResultLimit = $scope.searchResultLimitIncrementor;
      };

        //scope functions
        $scope.search = function() {
          $scope.empty=false;
          $scope.searching=true;
          $scope.result = [];
          $scope.searchResultLimit = $scope.searchResultLimitIncrementor;
          lecService.searchUsers($scope.firstName, $scope.lastName).then(function(result){
            $scope.result = result.data;
            angular.forEach($scope.result.addresses, function(value, key, obj){
              value.edit = false;
              value.readOnly=true;
            });
          if($scope.result && $scope.result.people && $scope.result.people.length === 0){
              $scope.empty = true;
          }
        }, function(data){
          errorService.sendError(data.status, 'search', false);
        });
      };

      $scope.lookupUser = function(netIdToLookup, index) {
        lecService.searchLocalContactInfo(netIdToLookup).then(function(result){
          $scope.result.people[index].contactInformation = result.data;
          angular.forEach($scope.result.people[index].contactInformation.local.addresses, function(value, key, obj){
            value.edit = false;
            value.readOnly=true;
          });
          angular.forEach($scope.result.people[index].contactInformation.emergency, function(value, key, obj){
            value.edit = false;
            value.readOnly=true;
          });
          $scope.empty = result.data && result.data.local.addresses.length === 0;
        }, function(data){
          errorService.sendError(data.status, 'user', false);
        });
      };

      $scope.reset = function(){init();};
      init();

  }]);

  app.controller('EmergencyPhoneController', ['$localStorage','$rootScope','$scope', 'lecService', 'errorService', function($localStorage, $rootScope, $scope, lecService, errorService) {
    //scope functions

    $scope.cancel = function() {
      init();
    };

    $scope.edit = function() {
      $scope.editMode = true;
    };

    $scope.save = function() {
      lecService.saveEmergencyPhoneNumber($scope.emergencyPhoneNumbers)
        .then(function(result){//success
          $scope.editMode = false;
        },function(data, status){//error
          errorService.sendError(status, 'phone number', true);
        });
    };

    //local functions
    var init = function() {
      $scope.emergencyPhoneNumbers = [];
      $scope.editMode = false;
      $scope.empty = false;
      $rootScope.profileLoadingState = $rootScope.profileLoadingState || {};
      $rootScope.profileLoadingState.ephone = true;
      lecService.getEmergencyPhoneNumber()
        .then(function(result){//success
          $rootScope.profileLoadingState.ephone = false;
          $scope.emergencyPhoneNumbers = result.data;
          if ( $scope.emergencyPhoneNumbers.length === 0 ) {
              $scope.empty = true;
          }
        }, function(result, status){//error
          $rootScope.profileLoadingState.ephone = false;
          $scope.emergencyPhoneNumbers = [];
          errorService.sendError(status, 'phone number', false);
        });
    };

    //run init
    init();
  } ]);

  app.controller('LocalContactInformationController', ['$localStorage','$rootScope','$scope', 'lecService','COUNTRIES','STATES', 'errorService', function($localStorage, $rootScope, $scope, lecService, COUNTRIES, STATES, errorService) {
      //scope functions
      $scope.addEdit = function() {
          // Make sure there's an array to add to
          if (!$scope.contactInfo.addresses) {
              $scope.contactInfo.addresses = [];
          }
        $scope.contactInfo.addresses.push({ addressLines : [""], country : 'USA', state : 'WI', edit : true});
      };

      $scope.save = function() {
          $scope.notSaving = false;
          $scope.error = "";
          lecService.saveLocalContactInfo($scope.contactInfo)
              .then(function(result){//success
                  $scope.contactInfo = result.data;
                  angular.forEach($scope.contactInfo.addresses, function(value, key, obj){
                      value.edit = false;
                  });
                  $scope.notSaving = true;
              },function(data, status){//error
                  $scope.notSaving = true;
                  errorService.sendError(status, 'local address', true);
              });
      };

      $scope.deleteAddress = function(index) {
          $scope.contactInfo.addresses.splice(index,1);
          $scope.save();
      };

      $scope.cancel = function() {
          init();
      };

      //local functions
      var init = function() {
          $rootScope.profileLoadingState = $rootScope.profileLoadingState || {};
          $rootScope.profileLoadingState.lcontact = true;
          $scope.contactInfo = {};
          $scope.countries = COUNTRIES;
          $scope.states = STATES;
          $scope.error = "";
          $scope.notSaving = true;
          lecService.getLocalContactInfo()
              .then(
                function(result){//success
                  $rootScope.profileLoadingState.lcontact = false;
                  // Make sure we get the expected type of data
                  if (typeof result.data === 'object') {
                      $scope.contactInfo = result.data;
                  }
                  // Clear out any editing that may have been saved
                  angular.forEach($scope.contactInfo.addresses, function(value, key, obj){
                      value.edit = false;
                      if(value.type === "HOUSING") {
                          value.readOnly=true;
                          value.housing = true;
                      }
                  })
              }, function(result, status){//error
                $scope.contactInfo = {};
                $rootScope.profileLoadingState.lcontact = false;
                errorService.sendError(status, 'local address', false);
            });
          if ($scope.contactInfo.addresses
              && $scope.contactInfo.addresses.length === 0) {
            $scope.noAddresses = true;
          }
      };

      init();
    } ]);

    app.controller('EmergencyInformationController', ['$localStorage','$rootScope','$scope', 'lecService', 'RELATIONSHIPS', 'errorService', function($localStorage, $rootScope, $scope, lecService, RELATIONSHIPS, errorService) {
      $scope.addEdit = function() {
          $scope.emergencyInfo.push({
            preferredName : "",
            addresses : [{
              addressLines:[""]
            }],
            emails: [{"type":"primary"}],
            phoneNumbers : [""],
            edit : true
          });
      };

      $scope.save = function() {
          $scope.notSaving = false;
          $scope.error = "";
          lecService.saveEmergencyContactInfo($scope.emergencyInfo)
              .then(function(result){//success
                  $scope.emergencyInfo = result.data;
                  angular.forEach($scope.emergencyInfo, function(value, key, obj){
                      value.edit = false;
                  });
                  $scope.notSaving = true;
              },function(data, status){//error
                  $scope.notSaving = true;
                  errorService.sendError(status, 'emergency contact', true);
              });
      }

      $scope.deleteContact = function(index) {
          $scope.emergencyInfo.splice(index,1);
          $scope.save();
      };

      $scope.cancel = function() {
          init();
      }

      //local functions
      var init = function() {
          $scope.relationshipOptions = RELATIONSHIPS;
          $scope.emergencyInfo = [];
          $scope.error = "";
          $scope.notSaving = true;
          $rootScope.profileLoadingState = $rootScope.profileLoadingState || {};
          $rootScope.profileLoadingState.einfo = true;
          lecService.getEmergencyContactInfo()
              .then(
                function(result){//success
                  if (result.data === '' ) {
                    $scope.emergencyInfo = [];
                  } else {
                    $scope.emergencyInfo = result.data;
                  }
                  $rootScope.profileLoadingState.einfo = false;
                  //clear out any editing that may have been saved
                  angular.forEach($scope.emergencyInfo, function(value, key, obj){
                      value.edit = false;
                  })
              }, function(result, status){//error
                $rootScope.profileLoadingState.einfo = false;
                $scope.emergencyInfo = {};
                errorService.sendError(status, 'emergency contact', false);
                console.log('inside getEmergencyContactInfo error state: ' + typeof $scope.emergencyInfo);
            });
          if ( $scope.emergencyInfo.length === 0 ) {
            $scope.noContacts = true;
          }
      };

      //run init
      init();

    }]);

    app.controller('ErrorController', ['$scope','$rootScope','$mdDialog', function($scope, $rootScope, $mdDialog) {
      $scope.alert = {
        title: '',
        msg: '',
        note: ''
      };

      $rootScope.$on('alert', function(event, data) {
        $scope.alert.title = data.title || '';
        $scope.alert.msg = data.msg || '';
        $scope.alert.note = data.note || '';
        $mdDialog.show({
          templateUrl: 'alert.html',
          parent: angular.element(document).find('div.my-uw')[0],
          clickOutsideToClose: true,
          preserveScope: true,
          scope: $scope
        })
      });
      $scope.close = function() {
        $mdDialog.hide();
      };
    }]);
});
