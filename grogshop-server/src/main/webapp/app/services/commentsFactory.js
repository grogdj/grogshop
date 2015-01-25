(function (){
        var $comments = function($transformRequestToForm, $cookieStore, $http ){
            var factory = {};
            factory.post = function(comment) {
                 return $http({
                    method: 'POST',
                    url: 'rest/comments/new',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + $cookieStore.get('email'), auth_token: $cookieStore.get('auth_token')},
                    transformRequest: $transformRequestToForm.transformRequest,
                    data: comment
                })
            }
            
            factory.load = function(item_id){
                return $http({
                    method: 'GET',
                    url: 'rest/comments/item/' + item_id,
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    transformRequest: $transformRequestToForm.transformRequest,
                    data: {}
                })
            }
            return factory;
        }
        
        $comments.$inject = ["$transformRequestToForm", "$cookieStore", "$http"];
        angular.module( "clubhouse" ).factory("$comments",$comments);
            
}());