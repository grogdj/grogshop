(function(){
	var app = angular.module('shoplistings',[]);

	app.directive("listingItem", ['$http', function(){
		return {
			restrict: 'E',
			templateUrl: 'listing-item.html',
			controller: function ($http){
				
				var shop = this;
				shop.products = [];

				$http.get("/grogshop-server/rest/listings/all").success(function(data){

				 	shop.products = data;
				 });

				
			},
			controllerAs: 'listingsCtrl'
		}
	}]);
})();