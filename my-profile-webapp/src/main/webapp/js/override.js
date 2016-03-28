define(['angular'], function(angular) {

  /*Keep in sync with docs/mardown/configuration.md*/

    var config = angular.module('override', []);
    config
        //see configuration.md for howto
        .constant('OVERRIDE', {
          'APP_FLAGS' : {
            'defaultTheme' : 0
          },
          'SERVICE_LOC' : {
            'sessionInfo' : '/portal/web/session.json',
            'featuresInfo' : '/web/staticFeeds/features.json',
            'notificationsURL' : '/web/staticFeeds/notifications.json'
          },
          'NAMES' : {
            'fname' : 'contact-info'
          },
          'SEARCH' : {
            'searchURL' : '/web/apps/search/'
          },
          'MISC_URLS' : {
            'feedbackURL' : 'https://my.wisc.edu/portal/p/feedback'
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
