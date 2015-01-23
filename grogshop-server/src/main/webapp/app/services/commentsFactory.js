(function (){
        var $comments = function($transformRequestToForm, $cookieStore, $http ){
            var factory = {};
            factory.post = function(comment) {
                console.log("YOU ARE TRYING TO POST THIS COMMENT ");
                console.log(comment);
            }
            return factory;
        }
        
        $comments.$inject = ["$transformRequestToForm", "$cookieStore", "$http"];
        angular.module( "clubhouse" ).factory("$comments",$comments);
            
}());