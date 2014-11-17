'use strict';
(function () {

    var app = angular.module('grogshop', ['shoplistings', 'shopbids', 'shopnotifications', 'shopmatchings', 'ngTagsInput', 'growlNotifications', 'ngAnimate']);

    app.controller('NavigationController', function ($rootScope) {
        this.section = 1;
        this.notificationCounter = 0;
        
        var nav = this;
        
        this.selectSection = function (setSection) {
            this.section = setSection;
            if (this.section === 3){
            	nav.notificationCounter = 0;
            }
        };

        this.isSelected = function (checkSection) {
            return this.section === checkSection;
        }

        

        $rootScope.$on('newNotification', function (event, data) {
            nav.notificationCounter = nav.notificationCounter + 1;
        });

    });

    app.controller('CreateBarController', function () {
        this.status = false;
    });

})();