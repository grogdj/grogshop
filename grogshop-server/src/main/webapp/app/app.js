(function() {
    
    var clubhouse = angular.module('clubhouse', ['shopnotifications', 'ngCookies', 'ngTagsInput', 'growlNotifications', 'ngRoute', 'ngAnimate', 'angular.filter', 'angularFileUpload', 'ui-rangeSlider','masonry']);

    // configure our routes
    clubhouse.config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'app/views/home.html',
                controller: 'homeController'
            })
            .when('/signup', {
                templateUrl: 'app/views/signup.html',
                controller: 'signUpController'
            })
            .when('/settings', {
                templateUrl: 'app/views/settings.html',
                controller: 'settingsController'
            })
            .when('/password', {
                templateUrl: 'app/views/password.html',
                controller: 'passwordController'

            })
            .when('/login', {
                templateUrl: 'app/views/login.html'
            })

            .when('/firstlogin', {
                templateUrl: 'app/views/userinterests.html',
                controller: 'userInterestsController'
            })

            .when('/club/preview/:club_id', {
                templateUrl: 'app/views/clubpreview.html',
                controller: 'clubController'

            })
             .when('/club/:club_id', {
                templateUrl: 'app/views/club.html',
                controller: 'clubController'

            })
            .otherwise({
                redirectto: '/'
            });

    });
    
    //HISTORY 
    clubhouse.run(function ($rootScope, $location) {

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

    
}());



///BORRAR ESTO
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









    