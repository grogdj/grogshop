(function(){
    var matchingsController = function( $scope, $items, $rootScope, $matchings, $memberships, $clubs, appConstants){
        $scope.userItems = [];
        $scope.itemsMatchings = [];
         $scope.server = appConstants.server + appConstants.context;
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
                    //$scope.itemsMatchings.push(data);
                    
                     for (j = 0; j < $scope.userItems.length; j++) { 
                         console.log(" FOR" + $scope.userItems[j].id + " / " + data.item_id)
                        if($scope.userItems[j].id == data.item_id){
                              console.log("IN FOR DATA "+ j +" / " + data)
                            $scope.userItems[j].item_matchings = [];
                            $scope.userItems[j].item_matchings = data;
                        }
                     }
                }).error(function (data) {
                    console.log("Error: " + data);
                });
            }
        }
        
        
        //
        
        $scope.comparatorClub =function (a , b) {
        
             console.log("El filtro esta mandando " + a + " / " + b);
            //console.log(b);
                if(b){
                    if(a == b.id){
                        return true;
                    }else {

                            return false;

                    }
                }else {
                 return true;
                }
            
        }
        
         $scope.comparatorType =function (a , b) {
           
            
             if( a == b){
                 return true;
             }else{
                if(b == ""){
                    return true;
                }else{
                    return false;
                }
             }
            
        }
    
    }
    
    matchingsController.$inject = ['$scope', '$items', '$rootScope','$matchings', '$memberships', '$clubs', 'appConstants' ];
    angular.module('clubhouse').controller('matchingsController', matchingsController);
}());