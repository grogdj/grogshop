'use strict';
(function () {

    var app = angular.module('grogshop', ['shopmemberships', 'shopnotifications', 'ngTagsInput', 'growlNotifications', 'ngAnimate', 'angular.filter']);

    app.controller('MainCtrl', function ($scope) {
        $scope.$on('authLoaded', function () {
            $scope.isMember($scope.main.user.name);
        });

        $scope.loadAuth = function () {
            Auth.load().success(function (data) {
                $scope.main.user = data.user;
                $scope.$broadcast("authLoaded");
            });
        }


        $scope.logoutUser = function () {
            Auth.logout().success(function (data) {
                toastr.info("You have been logged out.");
                $scope.main.user = {};
            });
        }

        $scope.loginUser = function () {
            Auth.login({
                username: $scope.main.credentials.email,
                password: $scope.main.credentials.password
            }).success(function (data) {
                if (data.error) {
                    toastr.error(data.error);
                } else {
                    toastr.success("You are signed in!");
                    $scope.loadAuth();
                    $scope.main.credentials = {};
                }
            });
        }

        $scope.registerUser = function () {
            Auth.register({
                email: $scope.newUser.email,
                password: $scope.newUser.password,
                name: $scope.newUser.name,
            }).success(function (data) {
                if (data.error) {
                    toastr.error(data.error);
                }

                if (data.success) {
                    toastr.success("Welcome to " + $scope.main.serie.name + "!");
                    $scope.loadAuth();
                    $scope.newUser = {};
                }
            });
        }

        $scope.loadAuth();
        $scope.loadSerie();
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