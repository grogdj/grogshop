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

				 	shop.bids = bidsdata;
                                
				// });


				
			},
			controllerAs: 'bidsCtrl'
		}
	}]);

	app.directive("bidsForm", ['$http',function(){
		return {
			restrict: 'E',
			templateUrl: 'bids-form.html',
			controller: function($http, $scope){

				var shop = this
				shop.livebid = {};

				this.addBid = function(formCtrl) {
					alert("estas creando un nuevo bid - " + formCtrl.livebid.userId)
					// ACA VA LA LOGICA QUE GUARDE LOS DATOS CON POST
					shop.livebid= {};
					 $scope.bidform.$setPristine();

				}
			},
			controllerAs: 'bidsFromCtrl'
		}
	}]);


	var bidsdata = [
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

})();

