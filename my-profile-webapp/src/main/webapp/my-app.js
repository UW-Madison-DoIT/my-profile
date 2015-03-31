'use strict';

(function() {
    
    app.config(['$routeProvider',function($routeProvider, $locationProvider) {
        $routeProvider.
         when('/settings', {templateUrl: 'partials/settings.html'}).
         when('/contact-info', {templateUrl: 'partials/contact-info.html'}).
         when('/main', {templateUrl: 'partials/main.html'}).
         when('/local', {templateUrl: 'partials/local-address.html'}).
         when('/emergencyContact', {templateUrl: 'partials/emergency-contact.html'}).
         otherwise({
             redirectTo: '/main'
         });
         }
    ]);
    
    //controllers
    
    app.controller('ContactInformationController', ['$localStorage','$scope', 'profileService', function($localStorage, $scope, profileService) {
        $scope.contactInfo = [];
        profileService.getContactInfo().then(function(result){
            $scope.contactInfo = result.data;
        });
        
      } ]);
    
    app.controller('LocalContactInformationController', ['$localStorage','$scope', 'profileService', function($localStorage, $scope, profileService) {
        //scope functions
        $scope.addEdit = function() {
          $scope.contactInfo.addresses.push({ addressLines : [""], edit : true})
        }
        
        $scope.save = function() {
            var len = $scope.contactInfo.addresses.length;
            $scope.contactInfo.addresses[len -1].edit = false;
            $scope.saving = true;
            profileService.saveLocalContactInfo($scope.contactInfo).then(function(result){
                $scope.contactInfo = result.data;
                $scope.saving = false;
            });
        }
        
        //local functions
        var init = function() {
            $scope.contactInfo = [];
            profileService.getLocalContactInfo().then(function(result){
                $scope.contactInfo = result.data;
            });
        }
        
        //run init
        init();
      } ]);
      
      app.controller('BasicInformationController', ['$localStorage','$scope', 'profileService', function($localStorage, $scope, profileService) {
          $scope.basicInfo = [];
          profileService.getBasicInfo().then(function(result){
              $scope.basicInfo = result.data.basicInfo;
          });
          
      } ]);
      
      app.controller('EmergencyInformationController', ['$localStorage','$scope', 'profileService', function($localStorage, $scope, profileService) {
          $scope.emergencyContacts = [];
          profileService.getEmergencyInfo().then(function(result){
              $scope.emergencyContacts = result.data.emergencyContacts;
          });
          
      }]);
      
      /// directives
      
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
      
      app.directive('address', function() {
          return {
            restrict : 'E',
            templateUrl : 'partials/address.html'
          }
        });
      
      app.directive('emergencyInfo', function() {
          return {
            restrict : 'E',
            templateUrl : 'partials/emergency-info.html'
          }
        });

      //service
      
      app.factory('profileService', function($http, miscService) {
          //var contactInfoPromise = $http.get('/profile/samples/contact-info.json');
          var contactInfoPromise = $http.get('/profile/api/contactInfo.json');
          var localContactInfoPromise = $http.get('/profile/api/localContactInfo/get.json');
          var basicInfoPromise = $http.get('/profile/samples/basic-info.json');
          var emergencyInfoPromise = $http.get('/profile/samples/emergency-info.json');
        
          var getContactInfo = function() {
              return contactInfoPromise.success(
                 function(data, status) { //success function
                     return data;
                 }).error(function(data, status) { // failure function
                 miscService.redirectUser(status, "Get contact info");
              });
          }
          
          var getLocalContactInfo = function() {
              return localContactInfoPromise.success(
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
          
          var getBasicInfo = function() {
              return basicInfoPromise.success(
                 function(data, status) { //success function
                     return data.basicInfo;
                 }).error(function(data, status) { // failure function
                 miscService.redirectUser(status, "Get basic info");
              });
          }
          
          var getEmergencyInfo = function() {
              return emergencyInfoPromise.success(
                 function(data, status) { //success function
                     return data.emergencyContacts;
                 }).error(function(data, status) { // failure function
                 miscService.redirectUser(status, "Get emergency info");
              });
          }
        
          return {
            getContactInfo : getContactInfo,
            getLocalContactInfo : getLocalContactInfo,
            saveLocalContactInfo : saveLocalContactInfo,
            getBasicInfo   : getBasicInfo,
            getEmergencyInfo : getEmergencyInfo
          }
        });
})();