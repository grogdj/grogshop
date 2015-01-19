(function(){

    var $clubs = function ($http, $cookieStore, $transformRequestToForm){
        var factory={};
        //LOAD ALL CLUBS
        factory.loadAll = function(){
            return $http({
                method: 'GET',
                url: 'rest/clubs/all',
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + $cookieStore.get('email'), auth_token: $cookieStore.get('auth_token')},
                transformRequest: $transformRequestToForm.transformRequest,
                data: {}
            });
        };
        //LOAD ALL PUBLIC CLUBS
        factory.loadAllPublic = function(){
            return  $http({
                method: 'GET',
                url: 'rest/public/clubs/all',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: transformRequestToForm,
                data: {}
            });
        };
        //LOAD SPECIFIC CLUB
        factory.load = function(club_id){
            return $http({
                method: 'GET',
                url: 'rest/clubs/' + club_id,
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + $cookieStore.get('email'), auth_token: $cookieStore.get('auth_token')},
                transformRequest: $transformRequestToForm.transformRequest,
                data: {}
            });
        };
        //LOAD SPECIFIC CLUB ITEMS
        factory.loadItems = function(club_id){
            return $http({
                method: 'GET',
                url: 'rest/items/club/' + club_id,
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + $cookieStore.get('email'), auth_token: $cookieStore.get('auth_token')},
                transformRequest: $transformRequestToForm.transformRequest,
                data: {}
            })
        };
        //LOAD SPECIFIC PUBLIC CLUB
        factory.loadPublic = function(club_id){
            return $http({
               method: 'GET',
               url: 'rest/public/clubs/' + club_id,
               headers: {'Content-Type': 'application/x-www-form-urlencoded'},
               transformRequest: $transformRequestToForm.transformRequest,
               data: {}
           })
        };
        
        return factory;
    }
    
    $clubs.$inject = ['$http','$cookieStore','$transformRequestToForm'];
    angular.module("clubhouse").factory("$clubs", $clubs)

}());
