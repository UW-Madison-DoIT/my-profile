define(['angular'], function(angular) {

    var config = angular.module('app-config', []);
    config
        .constant('APP_FLAGS', {
            'features' : false,
            'showSidebar' : true,
            'showSearch' : true,
            'isWeb' : false
        })
        .constant('SERVICE_LOC', {
            'aboutURL' : null,
            'sessionInfo' : '/portal/web/session.json',
            'sidebarInfo' : '/web/staticFeeds/sidebar.json',
            'featuresInfo' : 'staticFeeds/features.json',
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
            'searchURL' : '/web/apps/search/'
        })
        .constant('NOTIFICATION', {
            'enabled' : false, 
            'groupFiltering' : false,
            'notificationFullURL' : '/web/notifications'
        })
        .constant('MISC_URLS',{
            'back2ClassicURL' : null,
            'loginURL' : '/portal/Login',
            'logoutURL' : '/portal/Logout',
            'myuwHome' : 'https://my.wisc.edu',
            'whatsNewURL' : null
        });

    return config;

});
