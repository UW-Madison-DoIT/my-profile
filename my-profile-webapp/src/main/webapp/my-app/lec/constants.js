define(['angular'], function(angular) {

    var config = angular.module('my-app.lec.app-constaints', []);
    config
        .constant('RELATIONSHIPS', [
                                    { "key" : "Parent" , "value" : "Parent" },
                                    { "key" : "Spouse" , "value" : "Spouse" },
                                    { "key" : "Child" , "value" : "Child" },
                                    { "key" : "Grand Parent" , "value" : "Grand Parent" },
                                    { "key" : "Sibling" , "value" : "Sibling" },
                                    { "key" : "Step Parent" , "value" : "Step Parent" },
                                    { "key" : "Other Relative" , "value" : "Other Relative" },
                                    { "key" : "Friend" , "value" : "Friend" },
                                    { "key" : "Other" , "value" : "Other" }
                                ]);

    return config;

});

