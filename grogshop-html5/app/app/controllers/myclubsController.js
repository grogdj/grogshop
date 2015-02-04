(function(){
    
    var myclubsController = function($scope, $memberships, $users, $clubs, $rootScope){
        $scope.imagePath = "static/img/public-images/";
        $scope.holas = "Esto viene del controller de my clubs";
        $scope.userMemberships = {};
        $scope.userClubs = [];
        $memberships.load().success(function (data) {
         
            $scope.userMemberships = data;
            $scope.loadUserClubs($scope.userMemberships);

        }).error(function (data) {
          

        });
        
        $scope.loadUserClubs = function(club_ids){
            for (i = 0; i < club_ids.length; i++) { 
                $clubs.load(club_ids[i]).success(function (data) {
                   
                    $scope.userClubs.push(data)
                   
                }).error(function (data) {


                });
            }
        }
        
        $scope.cancelMembership = function (club_id) {
            //console.log("canceling membership ("+club_id+") for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);
            $memberships.cancel(club_id)
            .success(function (data) {
                $rootScope.$broadcast("quickNotification", "Club Membership Cancelled!", 'info');
              //  $scope.userClubs.splice($scope.userClubs.indexOf(parseInt(club_id)), 1);
                
            }).error(function (data) {
                console.log("Error: " + data);
                $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
            });

        };
    }
    myclubsController.$inject = ["$scope", "$memberships", "$users", "$clubs", "$rootScope"];
    angular.module('clubhouse').controller("myclubsController", myclubsController);
    
}());