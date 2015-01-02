

    var app = angular.module('grogshop', ['shopnotifications', 'ngCookies','ngTagsInput', 'growlNotifications', 'ngRoute','ngAnimate', 'angular.filter']);
    
    // configure our routes
    app.config(function($routeProvider ){
        $routeProvider.when('/',{
            templateUrl : 'views/home.html',
            controller : 'homeController'
        })
        .when('/signup',{
            templateUrl : 'views/signup.html',
            controller : 'signUpController'
        })
        .when('/settings',{
            templateUrl : 'views/settings.html',
            controller : 'settingsController'
        })
         .when('/password',{
            templateUrl : 'views/password.html',
            controller : 'passwordController'
        })
          .when('/login',{
            templateUrl : 'views/login.html'
        })
         
    });


    //

    app.controller('MainCtrl', function ($scope, $http, $cookieStore, $rootScope) {
        $scope.auth_token=$cookieStore.get('auth_token');
        $scope.email = $cookieStore.get('email');
        $scope.index = 0;
        $scope.notifications = {};
        
        $rootScope.$on('quickNotification', function (event, data) {
            var i;
            
            if(!data){
              $scope.invalidNotification = true;
              return;
            }

            i = $scope.index++;
            $scope.invalidNotification = false;
            $scope.notifications[i] = data;
           
        });
        
        $scope.logoutUser = function (isValid) {

            $http({
                method: 'POST',
                url: 'rest/auth/logout',
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:'+$cookieStore.get('email'), auth_token: $cookieStore.get('auth_token')},
                
            }).success(function (data) {
                
                console.log("You have been logged out."+data);
                $cookieStore.remove('auth_token');
                $cookieStore.remove('email');
                $scope.auth_token = "";
                $scope.email = "";
                $rootScope.$broadcast('goTo', "/");
                $rootScope.$broadcast("quickNotification", "You have been logged out.");
            }).error(function (data){
                    console.log("Error: "+data);
                    $rootScope.$broadcast("quickNotification", "Error: "+data);
            });
            
        };

        $scope.loginUser = function (isValid, user) {
            console.log("asd22 " + user.email + " / password" + user.password);

            $scope.submitted = true;

            if(isValid){

                $http({
                    method: 'POST',
                    url: 'rest/auth/login',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:'+user.email},
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
                        $rootScope.$broadcast("quickNotification", "You are logged now, have fun!");
                        console.log("You are signed in! "+data.auth_token );
                        
                        $cookieStore.put('auth_token', data.auth_token);
                        $cookieStore.put('email',user.email);
                        $scope.auth_token = $cookieStore.get('auth_token');
                        $scope.email = $cookieStore.get('email');
                        $scope.credentials = {};
                        $scope.submitted = false;
                        $rootScope.$broadcast('goTo', "/");
                         
                    
                }).error(function (data){
                        console.log("Error: "+data);
                        $rootScope.$broadcast("quickNotification", "You are NOT logged in because: +data");
                });
            }
        };
       

       
        

         
    
    });




    app.controller('NavigationController', function($scope, $location, $rootScope) {
       $scope.isActive = function(route) {
           return route === $location.path();
       };

        $rootScope.$on('goTo', function (event, data) {
            $location.path(data);
        });

       
    });



    

      


    


    