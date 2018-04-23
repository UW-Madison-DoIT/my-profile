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
    return angular.module('my-app.menu.controllers', [])
        .controller('AppMenuController', ['$rootScope', '$scope',
            '$location', '$mdDialog', '$mdSidenav',
            function($rootScope, $scope, $location,
                     $mdDialog, $mdSidenav) {
                var vm = this;

                // ///////////////////
                // BINDABLE MEMBERS //
                // ///////////////////

                // ////////////////
                // SCOPE METHODS //
                // ////////////////
                /**
                 * Check if the side nav menu is open
                 * @return {boolean}
                 */
                vm.isMenuOpen = function() {
                    return $mdSidenav('main-menu').isOpen();
                };

                /**
                 * Close the side navigation menu (used in ng-click)
                 */
                vm.closeAppMenu = function() {
                    if (vm.isMenuOpen()) {
                        $mdSidenav('main-menu').close();
                    }
                };

                /**
                 * Display the help dialog
                 * @param event
                 */
                vm.showHelpDialog = function(event) {
                    $mdDialog.show({
                        templateUrl: require.toUrl(
                            'my-app/dialog/partials/help-dialog-template.html'
                        ),
                        parent: angular.element(document).find('div.my-uw')[0],
                        targetEvent: event,
                        clickOutsideToClose: true,
                        controller: 'HelpDialogController',
                    });
                };
            }]);
});
