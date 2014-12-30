'use strict';
(function () {

    var app = angular.module('grogshop', [ 'shopnotifications', 'ngTagsInput', 'growlNotifications', 'ngAnimate', 'angular.filter']);

    app.controller('MainCtrl', function ($scope, $http) {
        $scope.main = {
          user: {}
          
        };
  

        $scope.logoutUser = function () {
            Auth.logout().success(function (data) {
                console.log("You have been logged out.");
                $scope.main.user = {};
            });
        };

        $scope.loginUser = function () {
            Auth.login({
                email: $scope.main.credentials.email,
                password: $scope.main.credentials.password,
                service_key: 'webkey'
            }).success(function (data) {
                if (data.error) {
                    console.log("Error:"+data.error);
                } else {
                    console.log("You are signed in!");
                    $scope.main.user.auth_token = data.auth_token;
                    $scope.main.credentials = {};
                }
            });
        };
        $scope.load = function(user) {
           console.log("asd " + $scope.newUser.email);  
        };

        $scope.registerUser = function (user) {
            $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
            $http.defaults.headers.common.service_key = "webkey";

            console.log("asd " + user.email + " / " +  user.password) ;

            $http.post('rest/auth/register',{
                email: user.email,
                password: user.password
            }).success(function (data) {
                if (data.error) {
                    console.log("Error:"+data.error);
                }

                if (data.success) {
                    console.log("Welcome to " + $scope.main.email + "!");
                    $scope.newUser = {};
                }
            });
        };


    });

    app.controller('NavigationController', function ($rootScope) {
        this.section = 1;
        this.notificationCounter = 0;

        var nav = this;

        this.selectSection = function (setSection) {
            this.section = setSection;
            if (this.section === 3) {
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