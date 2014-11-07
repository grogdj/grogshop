(function(){
	var app = angular.module('shoplistings',[]);

	app.directive("listingItem", ['$http', function(){
		return {
			restrict: 'E',
			templateUrl: 'listing-item.html',
			controller: function ($http){
				
				var shop = this;
				shop.products = [];

				$http.get("http://localhost:8080/grogshop-server/rest/listings/all").success(function(data){

				 	shop.products = data;
                             
                                
				 });


				
			},
			controllerAs: 'listingsCtrl'
		}
	}]);

  app.directive("listingForm", ['$http',function(){
    return {
      restrict: 'E',
      templateUrl: 'listings-form.html',
      controller: function($http, $scope){

        var shop = this
        shop.livelisting = {};

        this.addListing = function() {
        
          $http.post('http://localhost:8080/grogshop-server/rest/listings/new',{userId:shop.livelisting.userId, price:shop.livelisting.price, tags:[shop.livelisting.tags]});
          shop.livelisting= {};
          $scope.listingform.$setPristine();

        }
      },
      controllerAs: 'listingFromCtrl'
    }
  }]);


})();