(function(){
    var matchingsController = function( $scope, $items, $rootScope, $matchings, $memberships, $clubs){
        $scope.userItems = [];
        $scope.itemsMatchings = [];
        //LOAD MEMBERSHIPS AND CLUBS FOR FILTER
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
        
        //LOAD ITEMS
        
        $items.loadUserItems().success(function (data) {
            $scope.userItems = data;
            console.log("CANTIDAD DE ITEMS DEL USUARIO " + $scope.userItems.length )
            if($scope.userItems.length > 0){
                loadMatchings();
            };
        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        }); 
        
        
        //LOAD MATCHINGS
        function loadMatchings(){
             for (i = 0; i < $scope.userItems.length; i++) { 
                 console.log(i);
                 
                 console.log($scope.userItems[i].id);
                $matchings.load($scope.userItems[i].id).success(function (data) {
                    console.log("MATCHING DATA" + data)
                    console.log(data);
                    
                    $scope.itemsMatchings.push(data);
                }).error(function (data) {
                    console.log("Error: " + data);
                });
            }
        }
        //
        
        $scope.comparatorClub =function (a , b) {
            console.log("El filtro esta mandando " + a + " / " + b);
        }
    
    }
    
    matchingsController.$inject = ['$scope', '$items', '$rootScope','$matchings', '$memberships', '$clubs' ];
    angular.module('clubhouse').controller('matchingsController', matchingsController);
}());