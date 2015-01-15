

var app = angular.module('grogshop', ['shopnotifications', 'ngCookies', 'ngTagsInput', 'growlNotifications', 'ngRoute', 'ngAnimate', 'angular.filter', 'angularFileUpload', 'ui-rangeSlider','masonry']);


// configure our routes
app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/home.html',
        controller: 'homeController'
    })
            .when('/signup', {
                templateUrl: 'views/signup.html',
                controller: 'signUpController'
            })
            .when('/settings', {
                templateUrl: 'views/settings.html',
                controller: 'settingsController'
            })
            .when('/password', {
                templateUrl: 'views/password.html',
                controller: 'passwordController'

            })
            .when('/login', {
                templateUrl: 'views/login.html'
            })

            .when('/firstlogin', {
                templateUrl: 'views/firstlogin.html',
                controller: 'firstLoginController'
            })

            .when('/club/preview/:club_id', {
                templateUrl: 'views/clubpreview.html',
                controller: 'clubController'


            })
             .when('/club/:club_id', {
                templateUrl: 'views/club.html',
                controller: 'clubController'


            })
            .otherwise({
                redirectto: '/'
            });

});


//HISTORY 

app.run(function ($rootScope, $location) {

    var history = [];

    $rootScope.$on('$routeChangeSuccess', function () {
        history.push($location.$$path);
        console.log("HISTORY " + history)
    });

    $rootScope.back = function () {
        var prevUrl = history.length > 1 ? history.splice(-2)[0] : "/";
        
        $location.path(prevUrl);
    };


    
});

//END HISTORY


app.controller('MainCtrl', function ($scope, $http, $cookieStore, $rootScope) {
    $scope.auth_token = $cookieStore.get('auth_token');
    $scope.email = $cookieStore.get('email');
    $scope.user_id = $cookieStore.get('user_id');
    $scope.firstLogin = $cookieStore.get('firstLogin');
    $scope.index = 0;
    $scope.memberships = [];
    $scope.notifications = {};
    $scope.avatarStyle = "";
    
    $rootScope.$on('quickNotification', function (event, data) {
        var i;

        if (!data) {
            $scope.invalidNotification = true;
            return;
        }

        i = $scope.index++;
        $scope.invalidNotification = false;
        $scope.notifications[i] = data;

    });

    $scope.logoutUser = function (isValid) {
        $scope.auth_token = "";
        $scope.email = "";
        $scope.user_id = "";
        $scope.firstLogin = "";
        $scope.memberships = [];
        $http({
            method: 'POST',
            url: 'rest/auth/logout',
            headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + $cookieStore.get('email'), auth_token: $cookieStore.get('auth_token')},
        }).success(function (data) {

            console.log("You have been logged out." + data);
            $cookieStore.remove('auth_token');
            $cookieStore.remove('email');
            $scope.avatarStyle ="";
            $rootScope.$broadcast('goTo', "/");
           // $rootScope.$broadcast("quickNotification", "You have been logged out.");
        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Error: " + data);
        });

    };

    $scope.loginUser = function (isValid, user) {
        console.log("logged user " + user.email + " / password" + user.password);

        $scope.submitted = true;

        if (isValid) {

            $http({
                method: 'POST',
                url: 'rest/auth/login',
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + user.email},
                transformRequest: transformRequestToForm,
                data: {email: user.email, password: user.password}
            }).success(function (data) {
               // $rootScope.$broadcast("quickNotification", "You are logged now, have fun!");
                console.log("You are signed in! " + data.auth_token);

                $cookieStore.put('auth_token', data.auth_token);
                $cookieStore.put('email', data.email);
                $cookieStore.put('user_id', data.user_id);
                $cookieStore.put('firstLogin', data.firstLogin);
                $scope.auth_token = $cookieStore.get('auth_token');
                $scope.email = $cookieStore.get('email');
                $scope.user_id = $cookieStore.get('user_id');
                $scope.firstLogin = $cookieStore.get('firstLogin');
                $scope.credentials = {};
                $scope.submitted = false;
                $scope.avatarStyle = {'background-image':'url(rest/public/users/'+$scope.user_id+'/avatar'+ '?' + new Date().getTime()+')'} ;
                console.log("firstLogin: " + $scope.firstLogin);
                
                if ($scope.firstLogin) {
                    $rootScope.$broadcast('goTo', "/firstlogin");
                } else {
                    $rootScope.$broadcast('goTo', "/");
                }
                
            }).error(function (data) {
                console.log("Error: " + data.error);
                $rootScope.$broadcast("quickNotification", "You are NOT logged in because:" + data.error);
            });
        }
    };

    $scope.loadMemberships = function (user_id, email, auth_token) {
        console.log("loading memberships for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

        $http({
            method: 'GET',
            url: 'rest/members/'+user_id,
            headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + email, auth_token: auth_token},
            transformRequest: transformRequestToForm,
            data: {}
        }).success(function (data) {
           // $rootScope.$broadcast("quickNotification", "Memberships loaded! ");
            console.log("data: "+ data);
            $scope.memberships = JSON.parse(JSON.stringify(data));
            console.log("my memberships: "+ $scope.memberships);

        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };

    $scope.hasMembership = function (club_id) {
        //console.log("Memberships to string= "+$scope.memberships.toString());
        //console.log("Checking for : "+club_id);
        if (typeof $scope.memberships !== 'undefined' 
                && $scope.memberships.indexOf(parseInt(club_id)) !== -1) {
            
            return true;
        } else {
           
            return false;
        }
    };

    if($scope.auth_token && $scope.auth_token !== ""){
        $scope.loadMemberships($scope.user_id, $scope.email, $scope.auth_token);
        $scope.avatarStyle = {'background-image':'url(rest/public/users/'+$scope.user_id+'/avatar'+ '?' + new Date().getTime()+')'} ;
        
    }
    
    $rootScope.$on("updateProfileImage",function (event, data) {
        $scope.avatarStyle = {'background-image':'url(rest/public/users/'+$scope.user_id+'/avatar'+ '?' + new Date().getTime()+')'} ;

    });

});




app.controller('NavigationController', function ($scope, $location, $rootScope) {
    $scope.isActive = function (route) {
        return route === $location.path();
    };

    $rootScope.$on('goTo', function (event, data) {
        $location.path(data);
    });


});


var transformRequestToForm = function (obj) {
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
};








    