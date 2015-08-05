define(['angular'], function(angular) {

    var config = angular.module('app-config', []);
    config
        .constant('APP_FLAGS', {
            'welcome' : false
        })
        .constant('SERVICE_LOC', {
            'sessionInfo' : '/portal/web/session.json',
            'welcomeInfo' : 'samples/welcome.json',
            'sidebarInfo' : '/web/samples/sidebar.json',
            'notificationsURL' : '/web/samples/notifications.json',
            'groupURL' : '/portal/api/groups'
        })
        .constant('NAMES', {
            'title' : 'MyUW',
            'crest' : 'img/uwcrest_web_sm.png',
            'crestalt' : 'UW Crest',
            'sublogo' : null
        })
        .constant('SEARCH',{
            'isWeb' : false,
            'searchURL' : '/web/apps/search/'
        })
        .constant('NOTIFICATION', {
            'enabled' : false, 
            'groupFiltering' : true,
            'notificationFullURL' : '/web/notifications'
        })
        .constant('MISC_URLS',{
            'feedbackURL' : 'https://my.wisc.edu/portal/p/feedback',
            'back2ClassicURL' : null,
            'whatsNewURL' : null
        });

    return config;

});
