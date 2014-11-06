(function(){
	var app = angular.module('shoplistings',[]);

	app.directive("listingItem", ['$http', function(){
		return {
			restrict: 'E',
			templateUrl: 'listing-item.html',
			controller: function ($http){
				
				var shop = this;
				shop.products = [];

				//$http.get("http://grogshop-restprovider.rhcloud.com/grogshop-server/rest/listings/all").success(function(data){

				 	shop.products = [
                                     {
                                        "userId" : "xxx",
                                        "price" : 200000,
                                        "tags" : [
                                          {
                                            "name" : "#car"
                                          },
                                          {
                                            "name" : "#2014"
                                          },
                                          {
                                            "name" : "#ferrari"
                                          },
                                          {
                                            "name" : "#blue"
                                          }
                                        ]
                                      },
                                      {
                                        "userId" : "yyy",
                                        "price" : 4000,
                                        "tags" : [
                                          {
                                            "name" : "#car"
                                          },
                                          {
                                            "name" : "#2000"
                                          },
                                          {
                                            "name" : "#ford"
                                          },
                                          {
                                            "name" : "#red"
                                          }
                                        ]
                                      },
                                      {
                                        "userId" : "zzz",
                                        "price" : 4500,
                                        "tags" : [
                                          {
                                            "name" : "#car"
                                          },
                                          {
                                            "name" : "#2003"
                                          },
                                          {
                                            "name" : "#ford"
                                          },
                                          {
                                            "name" : "#blue"
                                          }
                                        ]
                                      },
                                      {
                                        "userId" : "jojjjjjjjj",
                                        "price" : 200000,
                                        "tags" : [
                                          {
                                            "name" : "#car"
                                          },
                                          {
                                            "name" : "#2014"
                                          },
                                          {
                                            "name" : "#ferrari"
                                          },
                                          {
                                            "name" : "#blue"
                                          }
                                        ]
                                      },

                                       {
                                        "userId" : "xxx",
                                        "price" : 200000,
                                        "tags" : [
                                          {
                                            "name" : "#car"
                                          },
                                          {
                                            "name" : "#2014"
                                          },
                                          {
                                            "name" : "#ferrari"
                                          },
                                          {
                                            "name" : "#blue"
                                          }
                                        ]
                                      },
                                      {
                                        "userId" : "yyy",
                                        "price" : 4000,
                                        "tags" : [
                                          {
                                            "name" : "#car"
                                          },
                                          {
                                            "name" : "#2000"
                                          },
                                          {
                                            "name" : "#ford"
                                          },
                                          {
                                            "name" : "#red"
                                          }
                                        ]
                                      },
                                      {
                                        "userId" : "zzz",
                                        "price" : 4500,
                                        "tags" : [
                                          {
                                            "name" : "#car"
                                          },
                                          {
                                            "name" : "#2003"
                                          },
                                          {
                                            "name" : "#ford"
                                          },
                                          {
                                            "name" : "#blue"
                                          }
                                        ]
                                      },
                                      {
                                        "userId" : "jojjjjjjjj",
                                        "price" : 200000,
                                        "tags" : [
                                          {
                                            "name" : "#car"
                                          },
                                          {
                                            "name" : "#2014"
                                          },
                                          {
                                            "name" : "#ferrari"
                                          },
                                          {
                                            "name" : "#blue"
                                          }
                                        ]
                                      }



                                    
                                ];
                                
				// });


				
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
          alert("estas creando un nuevo listing - " + shop.livelisting.userId)
          // ACA VA LA LOGICA QUE GUARDE LOS DATOS CON POST
          shop.livelisting= {};
          $scope.listingform.$setPristine();

        }
      },
      controllerAs: 'listingFromCtrl'
    }
  }]);


})();