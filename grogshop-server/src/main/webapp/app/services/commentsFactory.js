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
            return factory;
        }
        
        $comments.$inject = ["$transformRequestToForm", "$cookieStore", "$http"];
        angular.module( "clubhouse" ).factory("$comments",$comments);
            
}());