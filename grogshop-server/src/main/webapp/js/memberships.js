(function () {
    var app = angular.module('shopmemberships', []);

    app.directive("membershipsItem", ['$http', function () {
            return {
                restrict: 'E',
                templateUrl: 'memberships-item.html',
                controller: function ($http, $rootScope) {

                    var shop = this;
                    shop.memberships = [];
                    $http.jsonp("http://localhost:8080/grogshop-server/rest/" + "memberships/all" + "?callback=JSON_CALLBACK").success(function (data) {
                    //$http.jsonp("http://grog-restprovider.rhcloud.com/grogshop-server/rest/"+"memberships/all"+"?callback=JSON_CALLBACK").success(function (data) {

                        shop.memberships = data;


                    }).error(function (data) {
                        console.log("Request failed");

                    });
                    $rootScope.$on('newMembership', function (event, data) {
                        console.log(data);
                        shop.memberships.push(data);
                    });

                    this.deleteMembership = function (membership) {
                        var index = shop.memberships.indexOf(membership);
                        //$http.delete("http://grog-restprovider.rhcloud.com/grogshop-server/" + "rest/memberships/" + membership.id).success(function (data) {
                        $http.delete("http://localhost:8080/grogshop-server/" + "rest/memberships/" + membership.id).success(function (data) {
                            shop.memberships.splice(index, 1);

                        }).error(function (data) {
                            console.log("Request failed: delete membership");

                        });
                    };


                },
                controllerAs: 'membershipsCtrl'
            }
        }]);

    app.directive("membershipsForm", ['$http', function () {
            return {
                restrict: 'E',
                templateUrl: 'memberships-form.html',
                controller: function ($http, $scope, $rootScope) {

                    var shop = this
                    shop.livemembership = {};

                    this.previewClub = function () {
                        var membership = {tags: shop.livemembership.tags}
                        //$http.post("http://grog-restprovider.rhcloud.com/grogshop-server/rest/"+"memberships/join",membership).success(function (data) {
                        $http.post("http://localhost:8080/grogshop-server/rest/" + "memberships/join", membership).success(function (data) {
                            membership.id = data;
                            $rootScope.$broadcast('newMembership', membership);
                            shop.livemembership = {};
                            $scope.membershipform.$setPristine();
                        });

                    }
                },
                controllerAs: 'membershipsFromCtrl'
            }
        }]);




})();

