(function(){
	var app = angular.module('shopbids',[]);

	app.directive("bidsItem", ['$http', function(){
		return {
			restrict: 'E',
			templateUrl: 'bids-item.html',
			controller: function ($http){
				
				var shop = this;
				shop.bids = [];

				$http.get("http://localhost:8080/grogshop-server/rest/bids/all").success(function(data){

				 	shop.bids = data;
                                
                                
				 });


				
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

				this.addBid = function() {
					
					//
					$http.post('http://localhost:8080/grogshop-server/rest/bids/new',{userId:shop.livebid.userId, amount:shop.livebid.amount, tags:[shop.livebid.tags]});
					//
					shop.livebid= {};
					 $scope.bidform.$setPristine();

				}
			},
			controllerAs: 'bidsFromCtrl'
		}
	}]);


	

})();

