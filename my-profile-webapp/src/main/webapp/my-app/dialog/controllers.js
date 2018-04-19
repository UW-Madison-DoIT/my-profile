'use strict';

define(['angular'], function (angular) {
    return angular.module('my-app.dialog.controllers', [])
    .controller('HelpDialogController', ['$log', '$scope', '$mdDialog',
        '$location', function ($log, $scope, $mdDialog, $location) {
        $scope.helpOptions = [
            {
                label: 'Call the help desk (608-264-4357)',
                icon: 'phone', href: 'tel:608-264-4357'
            },
            {
                label: 'Send feedback',
                icon: 'feedback',
                href: 'https://my.wisc.edu/portal/p/feedback'},
            {
                label: 'Accessibility help',
                icon: 'accessibility',
                href: 'https://my.wisc.edu/web/static/myuw-help'},
        ];
        /**
         * Navigate to provided path
         * @param {string} url
         */
        $scope.goToUrl = function (url) {
            $location.path(url);
        };

        $scope.cancel = function () {
            $mdDialog.cancel();
        };
    }]);
});
