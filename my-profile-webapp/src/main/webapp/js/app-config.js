define(['angular'], function(angular) {

    var config = angular.module('app-config', []);
    config
        .constant('APP_FLAGS', {
            'features' : false,
            'showSidebar' : true,
            'showSearch' : true
        })
        .constant('SERVICE_LOC', {
            'sessionInfo' : '/portal/web/session.json',
            'sidebarInfo' : '/web/samples/sidebar.json',
            'featuresInfo' : 'samples/features.json',
            'notificationsURL' : '/web/notification.json',
            'kvURL' : null,
            'groupURL' : null
        })
        .constant('NAMES', {
            'title' : 'MyUW',
            'ariaLabelTitle' : 'My U W',
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
            'groupFiltering' : false,
            'notificationFullURL' : '/web/notifications'
        })
        .constant('MISC_URLS',{
            'feedbackURL' : 'https://my.wisc.edu/portal/p/feedback',
            'back2ClassicURL' : null,
            'whatsNewURL' : null,
            'loginURL' : '/portal/Login'
        });

    return config;

});
