(function () {
    var $interests = function($http, $transformRequestToForm) {
        var factory = {};
        //LOAD ALL INTERESTS
        factory.loadAll = function(){
            return $http({
                method: 'GET',
                url: 'rest/public/interests/all',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: $transformRequestToForm.transformRequest,
                data: {}
            })
        };
        return factory;
    };
    $interests.$inject = ['$http', '$transformRequestToForm'];
    angular.module("clubhouse").factory("$interests",$interests);
}());