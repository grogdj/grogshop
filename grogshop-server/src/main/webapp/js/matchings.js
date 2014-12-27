(function () {
    var app = angular.module('shopmatchings', []);

    app.directive("matchingsItems", ['$http', function () {
            return {
                restrict: 'E',
                templateUrl: 'matchings.html',
                controller: function ($http, $rootScope) {
                    var shop = this;
                    shop.matchings = [];
                    //$http.jsonp("http://grog-restprovider.rhcloud.com/grogshop-server/rest/"+"/matchings/all"+"?callback=JSON_CALLBACK").success(function (data) {
                    $http.jsonp("http://localhost:8080/grogshop-server/secured/rest/" + "/matchings/all" + "?callback=JSON_CALLBACK").success(function (data) {
                        
                        shop.matchings = data;
                        


                    }).error(function (data) {
                        console.log("Request failed: mathings all failed");

                    });

                    $rootScope.$on('removeMatching', function (event, data) {
                        
                        //$http.delete("http://grog-restprovider.rhcloud.com/grogshop-server/rest/"+"/matchings/"+data).success(function (data) {
                        $http.delete("http://localhost:8080/grogshop-server/secured/" + "rest/matchings/" + data).success(function (data) {
                            var remove;
                            console.log("Deleted matching looking for: "+data);
                            for (var i = 0; i < shop.matchings.length; i++) {
                                if (shop.matchings[i].matchingId == data) {
                                    remove = shop.matchings[i];
                                    console.log("Removing! : "+JSON.stringify(remove));
                                }
                            }
                            var index = shop.matchings.indexOf(remove);
                            
                            shop.matchings.splice(index, 1);

                        }).error(function (data) {
                            console.log("Request failed: delete match");

                        });


                    });

                    $rootScope.$on('newMatching', function (event, data) {
                        shop.matchings.push(data);
                    });


                },
                controllerAs: 'matchingsCtrl'

            }

        }]);
})();