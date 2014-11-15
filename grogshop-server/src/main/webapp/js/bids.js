(function () {
    var app = angular.module('shopbids', []);

    app.directive("bidsItem", ['$http', function () {
            return {
                restrict: 'E',
                templateUrl: 'bids-item.html',
                controller: function ($http, $rootScope) {

                    var shop = this;
                    shop.bids = [];
                    $http.jsonp("http://localhost:8080/grogshop-server/rest/" + "bids/all" + "?callback=JSON_CALLBACK").success(function (data) {
                        // $http.jsonp("http://grog-restprovider.rhcloud.com/grogshop-server/rest/"+"bids/all"+"?callback=JSON_CALLBACK").success(function (data) {

                        shop.bids = data;


                    }).error(function (data) {
                        console.log("Request failed");

                    });
                    $rootScope.$on('newBid', function (event, data) {
                        console.log(data);
                        shop.bids.push(data);
                    });

                    this.deleteBid = function (bid) {
                        var index = shop.bids.indexOf(bid);
                        $http.delete("http://localhost:8080/grogshop-server/" + "rest/bids/" + bid.id).success(function (data) {
                            shop.bids.splice(index, 1);

                        }).error(function (data) {
                            console.log("Request failed: delete bid");

                        });
                    };


                },
                controllerAs: 'bidsCtrl'
            }
        }]);

    app.directive("bidsForm", ['$http', function () {
            return {
                restrict: 'E',
                templateUrl: 'bids-form.html',
                controller: function ($http, $scope, $rootScope) {

                    var shop = this
                    shop.livebid = {};

                    this.addBid = function () {
                        var bid = {userId: shop.livebid.userId, amount: shop.livebid.amount, tags: shop.livebid.tags}
                        //$http.post("http://grog-restprovider.rhcloud.com/grogshop-server/rest/"+"bids/new",bid);
                        $http.post("http://localhost:8080/grogshop-server/rest/" + "bids/new", bid).success(function (data) {
                            bid.id = data;
                            $rootScope.$broadcast('newBid', bid);
                            shop.livebid = {};
                            $scope.bidform.$setPristine();
                        });

                    }
                },
                controllerAs: 'bidsFromCtrl'
            }
        }]);




})();

