(function (){
    var notificationsController = function ($rootScope, $scope, $cookieStore, $users) {
        $scope.notifications = [{message:'notificacion vieja', id:10}, {message:'Otra notificacion vieja', id:11},{message:'hola', id:9}, {message:'nuevo', id:8}, {message:' nuevo pero viejo nuevo', id:7}]; ; 
        $scope.newMatchingsNotifications = [{message:'hola', id:9}, {message:'nuevo', id:8}, {message:' nuevo pero viejo nuevo', id:7}]; 
    
    
        
        
        $scope.checkNew =  function(notification_id){
            
            if($scope.newMatchingsNotifications.length > 0){
                for( i = 0; i <  $scope.newMatchingsNotifications.length; i++){
                    
                    if(notification_id == $scope.newMatchingsNotifications[i].id){
                        return true;
                    }
                    
                }
            }else{
                return false;
            }
        };
        
        //JQ
        

            $('.dropdown.dropdown-li').data('open', false);

            $('#notifications-dropdown').click(function() {
                if($('.dropdown.dropdown-li').data('open')) {
                    $('.dropdown.dropdown-li').data('open', false);
                    $scope.clearNewNotifications();
                    
                    
                    
                } else
                    $('.dropdown.dropdown-li').data('open', true);
            });

            $(document).click(function() {
                if($('.dropdown.dropdown-li').data('open')) {
                    $('.dropdown.dropdown-li').data('open', false);
                     $scope.clearNewNotifications();
                    
                    
                    
                }
            });

        $scope.clearNewNotifications = function(){
            
            $scope.newMatchingsNotifications=[];
            alert("FUNCION LLAMADA" +  $scope.newMatchingsNotifications.length);
        }
        
    };
    
    
    
    notificationsController.$inject = ['$rootScope','$scope','$cookieStore', '$users'];
    angular.module( "clubhouse").controller("notificationsController", notificationsController);

}());
