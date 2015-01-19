(function (){
    var $items = function($transformRequestToForm, $cookieStore, $http, $upload){
        var factory = {};
        //POST ITEM
        factory.post = function(item){
            return $http({
                    method: 'POST',
                    url: 'rest/items/new',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + $cookieStore.get('email'), auth_token: $cookieStore.get('auth_token')},
                    transformRequest: $transformRequestToForm.transformRequest,
                    data: item
                })
        };
        //DELETE ITEM
        factory.remove = function(item_id){
            return $http({
                method: 'POST',
                url: 'rest/items/'+item_id+'/remove',
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + $cookieStore.get('email'), auth_token: $cookieStore.get('auth_token')},
                transformRequest: $transformRequestToForm.transformRequest,
                data: {id: item_id}
            })
        };
        //UPLOAD IMAGE
        factory.uploadImage = function(item_id, file){
            return $upload.upload({
                url: 'rest/items/' + item_id + '/image/upload', 
                method: 'POST',
                headers: {'Content-Type': 'multipart/form-data', service_key: 'webkey:' + $cookieStore.get('email'), auth_token: $cookieStore.get('auth_token')},
                file: file,
            })
        };
        
        
        return factory;
    };
    
    $items.$inject = ['$transformRequestToForm', '$cookieStore', '$http', '$upload'];
    angular.module( "clubhouse" ).factory("$items",$items);
    
}());