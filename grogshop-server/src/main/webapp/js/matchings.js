(function () {
    var app = angular.module('shopmatchings', []);

    app.directive("matchingsItems", ['$http', function () {
            return {
                restrict: 'E',
                templateUrl: 'matchings.html',
                controller: function ($http, $rootScope) {
                    var shop = this;
                    shop.matchings = [];

                    $http.jsonp("http://localhost:8080/grogshop-server/rest/" + "/matchings/all" + "?callback=JSON_CALLBACK").success(function (data) {
                        shop.matchings = data;


                    }).error(function (data) {
                        console.log("Request failed: mathings all failed");

                    });
                    
                    $rootScope.$on('newMatching', function (event, data) {
                        console.log("NewMatching -> "+data);
                        shop.matchings.push(data);
                    });
                },
                controllerAs: 'matchingsCtrl'

            }

        }]);
})();