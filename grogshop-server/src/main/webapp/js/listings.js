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
                        console.log("Request failed: listing all failed");

                    });

                    $rootScope.$on('newListing', function (event, data) {
                        
                        shop.products.push(data);
                    });

                    this.deleteListing = function (product) {
                        var index = shop.products.indexOf(product);
                        console.log("Deleting listing: "+ index);
                        //$http.delete("http://grog-restprovider.rhcloud.com/grogshop-server/rest/" + "listings/" + product.id).success(function (data) {
                        $http.delete("http://localhost:8080/grogshop-server/rest/" + "listings/" + product.id).success(function (data) {
                           console.log("Deleting listing 2: "+ index);
                           shop.products.splice(index, 1);

                        }).error(function (data) {
                            console.log("Request failed: delete listing");

                        });
                    };



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
                        var listing = {userId: shop.livelisting.userId, priceRange: [shop.livelisting.price, shop.livelisting.range ], tags: shop.livelisting.tags};
                        //$http.post("http://grog-restprovider.rhcloud.com/grogshop-server/rest/"+"listings/new", listing).success(function (data) {
                        $http.post("http://localhost:8080/grogshop-server/rest/" + "listings/new", listing).success(function (data) {
                            listing.id = data;
                            console.log("new listing, broadcasting event! " + data);

                            $rootScope.$broadcast('newListing', listing);
                            shop.livelisting = {};
                            $scope.listingform.$setPristine();
                        });


                    };

                    $scope.loadTags = function (query) {
                        //return $http.get('http://grog-restprovider.rhcloud.com/grogshop-server/rest/tags/all');
                        return $http.get('http://localhost:8080/grogshop-server/rest/tags/all');
                    };
                    
                    $scope.tagAdded = function(tag) {
                        
                        $scope.log.push('Added: ' + tag.text);
                    };


                },
                controllerAs: 'listingFromCtrl'
            }
        }]);


})();