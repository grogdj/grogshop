(function(){
	var app = angular.module('shopbids',[]);

	app.directive("bidsItem", ['$http', function(){
		return {
			restrict: 'E',
			templateUrl: 'bids-item.html',
			controller: function ($http){
				
				var shop = this;
				shop.bids = [];

				//$http.get("http://grogshop-restprovider.rhcloud.com/grogshop-server/rest/listings/all").success(function(data){

				 	shop.bids = [
                             {
							    "userId" : "example",
							    "amount" : 300,
							    "tags" : [
							      {
							        "name" : "#tag1"
							      },
							      {
							        "name" : "#tag2"
							      }
							    ]
							  },
							  {
							    "userId" : "Ezequiel",
							    "amount" : 700,
							    "tags" : [
							      {
							        "name" : "#tag1"
							      },
							      {
							        "name" : "#tag2"
							      }
							    ]
							  }      
                                   
                    ];
                                
				// });


				
			},
			controllerAs: 'bidsCtrl'
		}
	}]);

})();

