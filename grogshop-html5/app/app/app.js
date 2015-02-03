(function() {
    
    var clubhouse = angular.module('clubhouse', ['shopnotifications', 'ngCookies', 'ngTagsInput', 'ngRoute', 'ngAnimate', 'angular.filter', 'angularFileUpload', 'ui-rangeSlider','masonry','angular-growl']);
    
   clubhouse.constant("appConstants", {
        server: "http://localhost:8080/",
        context: "grogshop-server/"
    })
    // configure our routes
    .config(function ($routeProvider) {
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
        
             .when('/matchings/', {
                templateUrl: 'app/views/matchings.html',
                controller: 'matchingsController'
            })
        
             .when('/myclubs/', {
                templateUrl: 'app/views/myclubs.html',
                controller: 'myclubsController'
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













    