(function () {
    var app = angular.module('shoplistings', []);

    app.directive("listingItem", ['$http', function () {
            return {
                restrict: 'E',
                templateUrl: 'listing-item.html',
                controller: function ($http, $rootScope) {

                    var shop = this;
                    shop.products = [];
                    //$http.jsonp("http://grog-restprovider.rhcloud.com/grogshop-server/rest/"+"/listings/all"+"?callback=JSON_CALLBACK").success(function (data) {
                    $http.jsonp("http://localhost:8080/grogshop-server/rest/" + "/listings/all" + "?callback=JSON_CALLBACK").success(function (data) {
                            shop.products = data;
        
                    }).error(function (data) {
                        console.log("Request failed");

                    });

                    $rootScope.$on('newListing', function (event, data) {
                        console.log(data);
                        shop.products.push(data);
                    });



                },
                controllerAs: 'listingsCtrl'
            }
        }]);

    app.directive("listingForm", ['$http', function () {
            return {
                restrict: 'E',
                templateUrl: 'listings-form.html',
                controller: function ($http, $scope, $rootScope) {

                    var shop = this
                    shop.livelisting = {};
                    this.addListing = function () {
                        var listing = {userId: shop.livelisting.userId, price: shop.livelisting.price, tags: shop.livelisting.tags};
                        //$http.post("http://grog-restprovider.rhcloud.com/grogshop-server/rest/"+"listings/new", listing);
                        $http.post("http://localhost:8080/grogshop-server/rest/" + "listings/new", listing).success(function(data) {
                                listing.id = data;
                                console.log("new listing, broadcasting event! "+data);
                        
                                $rootScope.$broadcast('newListing', listing);
                                shop.livelisting = {};
                                $scope.listingform.$setPristine();
                              });
                        

                    };

                    $scope.loadTags = function (query) {
                        return $http.get('http://localhost:8080/grogshop-server/rest/tags/all');
                    };

                },
                controllerAs: 'listingFromCtrl'
            }
        }]);


})();