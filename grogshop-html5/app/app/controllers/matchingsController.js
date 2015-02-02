(function(){
    var matchingsController = function( $scope, $items){
        $scope.userMatchings = [
            {
                item_id : 1,
                item_name : "item que matcheo con otros",
                item_type : "ITEM",
                item_matchings : 
                     [
                        { item_name: "item coincide"},
                        { item_name: "item coincide 2"},
                         { item_name: "item coincide 3"},
                         { item_name: "item coincide 4"},
                          { item_name: "item coincide"},
                        { item_name: "item coincide 2"},
                         { item_name: "item coincide 3"},
                         { item_name: "item coincide 4"}
                        
                    ]
            },
            {
                item_id : 2,
                item_name : "Compro pelota de futbol adidas",
                item_type : "REQUEST",
                item_matchings : 
                     [
                        { item_name: "item coincide"},
                        { item_name: "item coincide 2"}
                        
                    ]
            },
            {
                item_id : 3,
                item_name : "Esto puede ser un topic",
                item_type : "TOPIC",
                item_matchings :  
                    [
                        { item_name: "item coincide con tags del topic"},
                        { item_name: "item coincide con tags del topic 2"},
                        {    item_name: "Uno mas"}
                    ]
            },
            {
                item_id : 2,
                item_name : "Compro pelota de futbol adidas",
                item_type : "REQUEST",
                item_matchings : 
                     [
                        { item_name: "item coincide"},
                        { item_name: "item coincide 2"}
                        
                    ]
            }
        ];
            
    }
    matchingsController.$inject = ['$scope', '$items'];
    angular.module('clubhouse').controller('matchingsController', matchingsController);
}())