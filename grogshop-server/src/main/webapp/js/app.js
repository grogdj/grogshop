'use strict';
(function () {

    var app = angular.module('grogshop', ['shoplistings', 'shopbids', 'shopnotifications', 'ngTagsInput']);

    app.controller('NavigationController', function ($rootScope) {
        this.section = 1;
        this.notificationCounter = 0;
        
        var nav = this;
        
        this.selectSection = function (setSection) {
            this.section = setSection;
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