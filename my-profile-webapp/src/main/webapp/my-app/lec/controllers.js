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
  app.controller('LocalContactAdminController', ['$scope', '$q', 'lecService', function($scope, $q, lecService){
      //init function
      var init = function() {
          $scope.error = "";
          $scope.empty = false;
          $scope.searching = false;
          $scope.result = [];
          $scope.searchTerm = "";
          $scope.searchResultLimitIncrementor=10;
          $scope.searchResultLimit = $scope.searchResultLimitIncrementor;
      };
      
      var merge = function(one, two){
        if (!one.people) return {people:two.people};
        if (!two.people) return {people:one.people};
        var final = {people:one.people};
        for(var i = 0 ; i < two.people.length;i++){
          var item = two.people[i];
          insert(item, final);
        }
        return final;
      };
      
      var insert = function(item, obj){
        var people = obj.people;
        var insertIndex = people.length;
          for(var i = 0; i < people.length; i++){
            if(String(item.attributes.uid) === String(people[i].attributes.uid)){
            // ignore duplicates
            insertIndex = -1;
            break;
          } else if(item.attributes.uid < people[i].attributes.uid){
            insertIndex = i;
            break;
          }
        }
        if(insertIndex == people.length){
          people.push(item);
        } else if(insertIndex != -1) {
          people.splice(insertIndex,0,item);
        }
      };
        
        //scope functions
        $scope.search = function() {
          $scope.empty=false;
          $scope.searching=true;
          $scope.result = [];
          $scope.searchResultLimit = $scope.searchResultLimitIncrementor;
          $q.all([lecService.searchUsersLastName($scope.searchTerm), lecService.searchUsersNetId($scope.searchTerm)]).then(function(result){
            $scope.result = merge(result[0].data, result[1].data);
            angular.forEach($scope.result.addresses, function(value, key, obj){
              value.edit = false;
              value.readOnly=true;
            });
            if($scope.result && $scope.result.people && $scope.result.people.length === 0){
                $scope.empty = true;
            }
          }, function(data){
            console.warn("Error looking up search term");
            if(data.status === 403) {
              $scope.error = "You do not have access to this module, if you feel this is incorrect please contact your supervisor.";
            } else {
              $scope.error = "Issue looking up contact information, please try again later.";
            }
          });
        };
      
      $scope.lookupUser = function(netIdToLookup, index) {
        lecService.searchLocalContactInfo(netIdToLookup).then(function(result){
          $scope.result.people[index].contactInformation = result.data;
          angular.forEach($scope.result.people[index].contactInformation.addresses, function(value, key, obj){
            value.edit = false;
            value.readOnly=true;
          });
          $scope.empty = result.data && result.data.addresses.length === 0;
        }, function(data){
          console.warn("Error looking up netId");
          if(data.status === 403) {
            $scope.error = "You do not have access to this module, if you feel this is incorrect please contact your supervisor.";
          } else {
            $scope.error = "Issue looking up contact information, please try again later.";
          }
        });
      };
      
      $scope.reset = function(){init();};
      init();
      
  }]);
  
  app.controller('LocalContactInformationController', ['$localStorage','$scope', 'lecService', function($localStorage, $scope, lecService) {
      //scope functions
      $scope.addEdit = function() {
        $scope.contactInfo.addresses.push({ addressLines : [""], edit : true})
      }
      
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
                  $scope.error = "There was an issue saving your address, please try again later."
              });
      }
      
      $scope.deleteAddress = function(index) {
          $scope.contactInfo.addresses.splice(index,1);
          $scope.save();
      };
      
      $scope.cancel = function() {
          init();
      }
      
      //local functions
      var init = function() {
          $scope.contactInfo = [];
          $scope.error = "";
          $scope.notSaving = true;
          lecService.getLocalContactInfo()
              .then(
                function(result){//success
                  $scope.contactInfo = result.data;
                  //clear out any editing that may have been saved
                  angular.forEach($scope.contactInfo.addresses, function(value, key, obj){
                      value.edit = false;
                  })
              }, function(result, status){//error
                $scope.contactInfo = {};
                $scope.error = "There was an issue getting your local address information. Please try again later.";
            });
          if ( $scope.contactInfo.length === 0 ) {
            $scope.noAddresses = true;
          }
      }
      
      //run init
      init();
    } ]);
    
    app.controller('EmergencyInformationController', ['$localStorage','$scope', 'lecService', function($localStorage, $scope, lecService) {
      $scope.addEdit = function() {
          $scope.emergencyInfo.push({ preferredName : "", addresses : [{addressLines:[""]}], emails:[{"type":"primary"}], phoneNumbers : [""], edit : true});
      }
      
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
                  $scope.error = "There was an issue saving your contact, please try again later."
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
          $scope.emergencyInfo = [];
          $scope.error = "";
          $scope.notSaving = true;
          lecService.getEmergencyContactInfo()
              .then(
                function(result){//success
                  $scope.emergencyInfo = result.data;
                  //clear out any editing that may have been saved
                  angular.forEach($scope.emergencyInfo, function(value, key, obj){
                      value.edit = false;
                  })
              }, function(result, status){//error
                $scope.emergencyInfo = {};
                $scope.error = "There was an issue getting your local address information. Please try again later.";
            });
          if ( $scope.emergencyInfo.length === 0 ) {
            $scope.noContacts = true;
          }
      }
      
      //run init
      init();
        
    }]);
});