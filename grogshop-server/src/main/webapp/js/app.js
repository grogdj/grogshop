'use strict';
(function () {

    var app = angular.module('grogshop', ['shopnotifications', 'ngTagsInput', 'growlNotifications', 'ngAnimate', 'angular.filter']);

    app.controller('MainCtrl', function ($scope, $http, $compile) {
        $scope.main = {
            auth_token : ""
        };


        $scope.logoutUser = function () {
            $http({
                method: 'POST',
                url: 'rest/auth/logout',
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey', auth_token: $scope.main.auth_token},
                
            }).success(function (data) {
                
                console.log("You have been logged out."+data);
                $scope.main = {};
                
            }).error(function (data){
                    console.log("Error: "+data);
                
            });
            
        };

        $scope.loginUser = function (user) {
            console.log("asd22 " + user.email + " / password" + user.password);
            $http({
                method: 'POST',
                url: 'rest/auth/login',
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey'},
                transformRequest: function (obj) {
                    var str = [];
                    for (var key in obj) {
                        if (obj[key] instanceof Array) {
                            for (var idx in obj[key]) {
                                var subObj = obj[key][idx];
                                for (var subKey in subObj) {
                                    str.push(encodeURIComponent(key) + "[" + idx + "][" + encodeURIComponent(subKey) + "]=" + encodeURIComponent(subObj[subKey]));
                                }
                            }
                        }
                        else {
                            str.push(encodeURIComponent(key) + "=" + encodeURIComponent(obj[key]));
                        }
                    }
                    return str.join("&");
                },
                data: {email: user.email, password: user.password}
            }).success(function (data) {
                
                    console.log("You are signed in! "+data.auth_token );
                    $scope.main.auth_token = data.auth_token;
                    $scope.credentials = {};
                    $scope.main.user = user.email;

                    $('#login-popover').popover('hide');

                
            }).error(function (data){
                    console.log("Error: "+data);
                
            });
        };
       

        $scope.registerUser = function (user) {
            console.log("asd " + user.email + " / " + user.password);

            $http({
                method: 'POST',
                url: 'rest/auth/register',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function (obj) {
                    var str = [];
                    for (var key in obj) {
                        if (obj[key] instanceof Array) {
                            for (var idx in obj[key]) {
                                var subObj = obj[key][idx];
                                for (var subKey in subObj) {
                                    str.push(encodeURIComponent(key) + "[" + idx + "][" + encodeURIComponent(subKey) + "]=" + encodeURIComponent(subObj[subKey]));
                                }
                            }
                        }
                        else {
                            str.push(encodeURIComponent(key) + "=" + encodeURIComponent(obj[key]));
                        }
                    }
                    return str.join("&");
                },
                data: {email: user.email, password: user.password}
            }).success(function (data) {
               

                
                    console.log("Welcome to " + data + "!");
                    $scope.newUser = "";
                
            }).error(function (data) {
              
                    console.log("Error : " + data + "!");
                    
                
            });
        };

        $('#login-popover').popover({
 
            html : true,
                title: function() {
          return $("#login-form-title").html();
            },
            content: function() {
              return $compile($("#login-form-content").html())($scope);
            },
            placement : 'bottom',
            trigger : 'click'
        });
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