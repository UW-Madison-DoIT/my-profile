define(['angular'], function(angular) {
  
  /*Keep in sync with docs/mardown/configuration.md*/

    var config = angular.module('app-config', []);
    config
        .constant('APP_FLAGS', {
            'features' : false,
            'showSearch' : true,
            'isWeb' : false,
            'defaultTheme' : 0
        })
        .constant('SERVICE_LOC', {
            'aboutURL' : null,
            'sessionInfo' : '/portal/web/session.json',
            'featuresInfo' : '/web/staticFeeds/features.json',
            'notificationsURL' : '/web/staticFeeds/notifications.json',
            'kvURL' : null,
            'groupURL' : null
        })
        .constant('NAMES', {
            'title' : 'My Profile',
            'guestUserName' : 'guest',
            'fname' : 'contact-info'
        })
        .constant('SEARCH',{
            'searchURL' : '/web/apps/search/'
        })
        .constant('NOTIFICATION', {
            'enabled' : false,
            'groupFiltering' : false,
            'notificationFullURL' : 'notifications'
        })
        .constant('MISC_URLS',{
            'back2ClassicURL' : null,
            'feedbackURL' : '/portal/p/feedback',
            'helpdeskURL' : 'https://kb.wisc.edu/helpdesk/',
            'loginURL' : '/portal/Login?profile=bucky',
            'logoutURL' : '/portal/Logout',
            'myuwHome' : 'https://my.wisc.edu',
            'rootURL' : '/web',
            'addToHomeURLS' : {
              'layoutURL' : '/portal/web/layoutDoc?tab=UW Bucky Home',
              'addToHomeActionURL' : '/portal/web/layout?tabName=UW Bucky Home&action=addPortlet&fname='
            }

        })
        .constant('FOOTER_URLS', [
          { "url" : "/web/static/myuw-help",
            "target" : "_blank",
            "title" : "Help"
          },
          { "url" : "/portal/p/feedback",
            "target" : "_blank",
            "title" : "Feedback"
          }
        ])
        .constant('APP_BETA_FEATURES', [
          {
            "id" : "enableProfileNavigation",
            "title" : "Enable Profile Navigation",
            "description" : "In LEC, enable profile navigation to the other parts of My Profile that is hidden right now"
          }
        ]);

    return config;

});
