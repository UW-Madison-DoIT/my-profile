define(['angular'], function(angular) {

  /*Keep in sync with docs/mardown/configuration.md*/

    var config = angular.module('override', []);
    config
        //see configuration.md for howto
        .constant('OVERRIDE', {
          'NAMES' : {
            'title' : 'My Profile',
            'fname' : 'contact-info'
          },
          'SERVICE_LOC' : {
            'sessionInfo' : '/portal/web/session.json'
          },
          'SEARCH' : {
            'searchURL' : '/web/apps/search'
          },
          'MISC_URLS' : {
            'feedbackURL' : '/portal/p/feedback'
          },
          'APP_BETA_FEATURES' : [
            {
              "id" : "enableProfileNavigation",
              "title" : "Enable Profile Navigation",
              "description" : "In LEC, enable profile navigation to the other parts of My Profile that is hidden right now"
            }

          ]
        })

    return config;

});
