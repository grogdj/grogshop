app.controller('firstLoginController', function ($rootScope, $http, $scope) {
   
    $scope.newProfile = function (user_id, email, auth_token) {
        console.log("creating profile for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

        $http({
            method: 'POST',
            url: 'rest/users/new',
            headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + email, auth_token: auth_token},
            transformRequest: function (obj) {
                var str = [];
                for (var key in obj) {
                    if (obj[key] instanceof Array) {
                        for (var idx in obj[key]) {
                            var subObj = obj[key][idx];
                            for (var subKey in subObj) {
                                str.push(encodeURIComponent(key) + "[" + idx + "][" + encodeURIComponent(subKey) + "]=" + encodeURIComponent(subObj[subKey]));
                            }
                        }
                    }
                    else {
                        str.push(encodeURIComponent(key) + "=" + encodeURIComponent(obj[key]));
                    }
                }
                return str.join("&");
            },
            data: {user_id: user_id}
        }).success(function (data) {
            $rootScope.$broadcast("quickNotification", "Profile created!");




        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };
    
    $scope.newProfile($scope.user_id, $scope.email, $scope.auth_token);


    //GRID
    $scope.imagePath = "resources/img/maintag-images/"
    $scope.mainTags = [
		{name:"music", image:"music.jpg"},
		{name:"art", image:"art.jpg"},
		{name:"science", image:"science.jpg"},
		{name:"sports", image:"sports.jpg"},
		{name:"cars", image:"cars.jpg"},
		{name:"cooking", image:"cooking.jpg"},
		{name:"design", image:"design.jpg"},
		{name:"health", image:"health.jpg"},
		{name:"music", image:"music.jpg"},
		{name:"art", image:"art.jpg"},
		{name:"science", image:"science.jpg"},
		{name:"sports", image:"sports.jpg"},
		{name:"cars", image:"cars.jpg"},
		{name:"cooking", image:"cooking.jpg"},
		{name:"design", image:"design.jpg"},
		{name:"health", image:"health.jpg"}
		
	];

	$scope.numberOfItem = Math.floor($( window ).width() / 300);
	console.log("items per row " + $scope.numberOfItem);
	$scope.pageClass="firstLoginScreen";
	$scope.elementWidth = $( window ).width() / $scope.numberOfItem ;
	$scope.elementHeight = ($( window ).height()-50) / Math.ceil(($scope.mainTags.length / $scope.numberOfItem)) ;
	
	$( window ).resize(function() {
	  $scope.numberOfItem = Math.floor($( window ).width() / 300);
	  $scope.elementWidth = $( window ).width() / $scope.numberOfItem ;
	  $scope.elementHeight = ($( window ).height()-50) / Math.ceil(($scope.mainTags.length / $scope.numberOfItem)) ;
	  $scope.$digest();
	  console.log("elementWidth " + $scope.elementWidth)
	});

	//CLICK CHECK
	$scope.selectedTagsName = [];
	$scope.selectedTags = [];
	$scope.toogleCheck = function(valueName){
		if($scope.selectedTagsName.indexOf(valueName) > -1){
			$scope.selectedTagsName.splice($scope.selectedTagsName.indexOf(valueName),1);
		}else {
			$scope.selectedTagsName.push(valueName);
		};
		console.log($scope.selectedTagsName);
	}



});