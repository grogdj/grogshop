(function(){
    var $matchings = function($transformRequestToForm, $cookieStore, $http, appConstants){
        var factory = {};
        factory.load = function(item_id){
             return $http({
                method: 'GET',
                url: appConstants.server + appConstants.context + 'rest/matchings/item/' + item_id,
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + $cookieStore.get('email'), auth_token: $cookieStore.get('auth_token')},
                transformRequest: $transformRequestToForm.transformRequest,
                data: {}
            })
        }
        return factory;
    }
    
    $matchings.$inject = ['$transformRequestToForm', '$cookieStore', '$http', 'appConstants'];
    angular.module('clubhouse').factory("$matchings", $matchings);
}());