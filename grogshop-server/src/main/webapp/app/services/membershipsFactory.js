(function(){
    var $memberships = function($transformRequestToForm, $cookieStore, $http){
        var factory = {};
        //LOAD MEMBERSHIPS
        factory.load = function(){
            return $http({
                method: 'GET',
                url: 'rest/members/'+ $cookieStore.get('user_id'),
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + $cookieStore.get('email'), auth_token: $cookieStore.get('auth_token')},
                transformRequest: transformRequestToForm.transformRequest,
                data: {}
            })
        };
        //CHECK MEMBERSHIPS
        factory.hasMembership = function(club_id, memberships){
              //console.log("Memberships to string= "+$scope.memberships.toString());
            //console.log("Checking for : "+club_id);
            if (typeof memberships !== 'undefined' 
                    && memberships.indexOf(parseInt(club_id)) !== -1) {

                return true;
            } else {

                return false;
            }
        };
        //JOIN CLUB
        factory.join = function(club_id){
             return $http({
                method: 'POST',
                url: 'rest/members/join',
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + $cookieStore.get('email'), auth_token: $cookieStore.get('auth_token')},
                transformRequest: $transformRequestToForm.transformRequest,
                data: {club_id: club_id, user_id: $cookieStore.get('user_id')}
            })
        };
         //CANCEL CLUB
        factory.cancel = function(club_id){
            return $http({
                method: 'POST',
                url: 'rest/members/cancel',
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + $cookieStore.get('email'), auth_token: $cookieStore.get('auth_token')},
                transformRequest: $transformRequestToForm.transformRequest,
                data: {club_id: club_id, user_id: $cookieStore.get('user_id')}
            })
        };
        return factory;
    };
    
    $memberships.$inject = ['$transformRequestToForm','$cookieStore', '$http'];
    angular.module( "clubhouse" ).factory("$memberships", $memberships);
    
}());