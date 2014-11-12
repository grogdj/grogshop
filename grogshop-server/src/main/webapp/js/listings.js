(function () {
    var app = angular.module('shoplistings', []);
    
    app.directive("listingItem", ['$http', function () {
            return {
                restrict: 'E',
                templateUrl: 'listing-item.html',
                controller: function ($http, $rootScope) {

                    var shop = this;
                    shop.products = [];

                    $http.get("http://localhost:8080/grogshop-server/rest/listings/all").success(function (data) {

                        shop.products = data;


                    });
                    
                    $rootScope.$on('newListing', function (event, data) {
                        console.log(data);
                        shop.products.push(data);
                    });



                },
                controllerAs: 'listingsCtrl'
            }
        }]);

    app.directive("listingForm", ['$http',   function () {
            return {
                restrict: 'E',
                templateUrl: 'listings-form.html',
                controller: function ($http, $scope) {

                    var shop = this
                    shop.livelisting = {};
                    this.addListing = function () {
                        var listing = {userId: shop.livelisting.userId, price: shop.livelisting.price, tags: shop.livelisting.tags};
                        $http.post('http://localhost:8080/grogshop-server/rest/listings/new', listing);
                        $scope.$broadcast('newListing', listing);
                        shop.livelisting = {};
                        $scope.listingform.$setPristine();

                    }
                },
                controllerAs: 'listingFromCtrl'
            }
        }]);


})();