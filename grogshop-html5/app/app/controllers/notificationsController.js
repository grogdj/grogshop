(function (){
    var notificationsController = function ($rootScope, $scope, $cookieStore, $users,  $notifications) {
        var not = this; 
        $scope.notifications = []; ; 
        $scope.newMatchingsNotifications = [{message:"esta es nueva"}]; 
    
        $scope.loadNotifications = function(){
            
            $notifications.loadAll().success(function (data) {
                   $scope.notifications = data;  
                
                }).error(function (data) {
                    console.log("Error: " + data);
                });
        }
    
        
        
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

            //$(document).click(function() {
            //    if($('.dropdown.dropdown-li').data('open')) {
            //        $('.dropdown.dropdown-li').data('open', false);
                     //$scope.clearNewNotifications();
                    
            //        $scope.clearNewNotificationsFromDocument($scope);
                    
                    
                    
              //  }
            //});

        $scope.clearNewNotifications = function(){
            console.log($scope.newMatchingsNotifications)
            $scope.newMatchingsNotifications=[];
        }
        
     
        
    };
    
    
    
    notificationsController.$inject = ['$rootScope','$scope','$cookieStore', '$users', '$notifications'];
    angular.module( "clubhouse").controller("notificationsController", notificationsController);

}());
