app.controller('clubController', function ($scope, $routeParams, $http, $rootScope, $location, $upload, $timeout) {
    
    $scope.imagePath = "static/img/public-images/"

    $scope.club_id = $routeParams.club_id;
    $scope.club = {};
    $scope.url = $location.path();
    $scope.isPreview = $scope.url.indexOf('preview');
    $scope.fileReaderSupported = window.FileReader != null && (window.FileAPI == null || FileAPI.html5 != false);
    $scope.items = [];

    //$scope.productWidth = $(window).width() / 5;

    if ($scope.isPreview > 0) {
        $scope.pageClass = "clubPreview";
    } else {
        $scope.pageClass = "clubDetail";
    }
    
    if ($scope.hasMembership($scope.club_id) && $scope.isPreview > 0) {
        $rootScope.$broadcast('goTo', "/club/" + $scope.club_id);
    } else if (!$scope.hasMembership($scope.club_id) && $scope.isPreview < 0) {
        $rootScope.$broadcast('goTo', "/club/preview/" + $scope.club_id);
    }
    console.log("HAS MEMB = " + $scope.hasMembership($scope.club_id));
    console.log("Es preview? " + $scope.isPreview);
    
    if($scope.isPreview > 0 ){
       $scope.pageClass = "clubPreview";
    }else {
       $scope.pageClass = "clubDetail"; 
    }
    
    $scope.loadClub = function (user_id, email, auth_token) {
        console.log("loading clubs for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

        $http({
            method: 'GET',
            url: 'rest/clubs/' + $scope.club_id,
            headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + email, auth_token: auth_token},
            transformRequest: transformRequestToForm,
            data: {}
        }).success(function (data) {
            $rootScope.$broadcast("quickNotification", "Club loaded!");
            $scope.club = data;
        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };
    
    $scope.loadClubItems = function (user_id, email, auth_token) {
        console.log("loading clubs for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

        $http({
            method: 'GET',
            url: 'rest/items/club/' + $scope.club_id,
            headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + email, auth_token: auth_token},
            transformRequest: transformRequestToForm,
            data: {}
        }).success(function (data) {
            $rootScope.$broadcast("quickNotification", "items loaded!");
            console.log("My Items");
            console.log(data);
            $scope.items = data;
                
        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };


    $scope.joinClub = function (club_id, user_id, email, auth_token) {
        console.log("joining clubs for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

        $http({
            method: 'POST',
            url: 'rest/members/join',
            headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + email, auth_token: auth_token},
            transformRequest: transformRequestToForm,
            data: {club_id: club_id, user_id: user_id}
        }).success(function (data) {
            $rootScope.$broadcast("quickNotification", "Club Joined!");
            $scope.memberships.push(parseInt(club_id));
            $rootScope.$broadcast('goTo', "/club/" + $scope.club_id);
            console.log("after joining the club: "+$rootScope.memberships);
        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };
    
    $scope.cancelMembership = function (club_id, user_id, email, auth_token) {
        console.log("canceling membership ("+club_id+") for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

        $http({
            method: 'POST',
            url: 'rest/members/cancel',
            headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + email, auth_token: auth_token},
            transformRequest: transformRequestToForm,
            data: {club_id: club_id, user_id: user_id}
        }).success(function (data) {
            $rootScope.$broadcast("quickNotification", "Club Membership Cancelled!");
            $scope.memberships.splice($scope.memberships.indexOf(parseInt(club_id)), 1);
            console.log("after canceling the club: "+$scope.memberships);
            $rootScope.$broadcast('goTo', "/club/preview/" + $scope.club_id);
        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };


    //FILTER BAR 
    
    $scope.smallerThan = function(a,b) {
      return a <= b;
    };
    $scope.greaterThan = function(a,b) {
      return a >= b;
    };
    
       $scope.priceRange = {
        range: {
            min: 0,
            max: 10000
        },
        minPrice: 0,
        maxPrice: 10000
    };
    
    //
    
    
    $scope.newItem = function (isValid, files, event) {
        if(isValid){
            console.log("adding new item  for user " + $scope.user_id + " with email: " + $scope.email + " and auth_token: " + $scope.auth_token);
            
            var itemToSend = {club_id: $scope.club_id, user_id: $scope.user_id, name: $scope.newItem.title, description: $scope.newItem.description, 
                     tags: JSON.stringify($scope.newItem.tags), price: $scope.newItem.price};

            $http({
                method: 'POST',
                url: 'rest/items/new',
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + $scope.email, auth_token: $scope.auth_token},
                transformRequest: transformRequestToForm,
                data: itemToSend
            }).success(function (data) {
                $rootScope.$broadcast("quickNotification", "Item Created!");
                var newItem = {id: data, club_id: $scope.club_id, user_id: $scope.user_id, user_email: $scope.email, name: $scope.newItem.title, description: $scope.newItem.description, 
                     tags: $scope.newItem.tags, price: $scope.newItem.price};
                 var item_id = data;
                
                console.log("new item added: "+newItem);
                $scope.newItem={};
                $scope.newPostForm.$setPristine();
                $scope.uploadFile(item_id, files,newItem);
                
            }).error(function (data) {
                console.log("Error: " + data);
                $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
            });
        }else{
            $rootScope.$broadcast("quickNotification", "Form not valid: Something went wrong!");
            
        }

    };
    
    $scope.removeItem = function (item_id) {
        
            console.log("remove new item  for user " + $scope.user_id + " with email: " + $scope.email + " and auth_token: " + $scope.auth_token);
            
           

            $http({
                method: 'POST',
                url: 'rest/items/'+item_id+'/remove',
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + $scope.email, auth_token: $scope.auth_token},
                transformRequest: transformRequestToForm,
                data: {id: item_id}
            }).success(function (data) {
                $rootScope.$broadcast("quickNotification", "Item Removed!");
                
                $scope.items.splice($scope.items.indexOf(parseInt(item_id)), 1);
                
            }).error(function (data) {
                console.log("Error: " + data);
                $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
            });
        

    };
    
    $scope.generateThumb = function(file) {
        console.log(file);
		if (file != null) {
			if ($scope.fileReaderSupported && file.type.indexOf('image') > -1) {
                            console.log("file oh yeah");
				$timeout(function() {
					var fileReader = new FileReader();
					fileReader.readAsDataURL(file);
					fileReader.onload = function(e) {
						$timeout(function() {
                                                    console.log("file oh yeah 2: "+e.target.result);
							file.dataUrl = e.target.result;
						});
					}
				});
			}
		}
	};
    
    $scope.uploadFile = function (item_id, files, newItem) {
        console.log("Files : " + files + "-- event: " + event);
        var file = files[0];
        $scope.upload = $upload.upload({
            url: 'rest/items/' + item_id + '/image/upload', 
            method: 'POST',
            headers: {'Content-Type': 'multipart/form-data', service_key: 'webkey:' + $scope.email, auth_token: $scope.auth_token},
            file: file,
        }).progress(function (evt) {
            $scope.uploadPercentage = parseInt(100.0 * evt.loaded / evt.total);
            $scope.uploading = true;
            console.log('progress: ' + parseInt(100.0 * evt.loaded / evt.total) + '% file :' + evt.config.file.name);
        }).success(function (data) {
            // file is uploaded successfully
            console.log('file ' + file.name + 'is uploaded successfully. Response: ' + data);
            $scope.uploading = false;
            $('#newProductModal').modal('hide');
            $scope.items.push(newItem);


        }).error(function (data) {
            console.log('file ' + file.name + ' upload error. Response: ' + data);

        });


    };
     
    $scope.loadPublicClub = function (user_id, email, auth_token) {
       console.log("loading public club (" + $scope.club_id + ") for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

       $http({
           method: 'GET',
           url: 'rest/public/clubs/' + $scope.club_id,
           headers: {'Content-Type': 'application/x-www-form-urlencoded'},
           transformRequest: transformRequestToForm,
           data: {}
       }).success(function (data) {
           $rootScope.$broadcast("quickNotification", "Public Club loaded!");
           $scope.club = data;
       }).error(function (data) {
           console.log("Error: " + data);
           $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
       });

    };

    if ($scope.auth_token && $scope.auth_token !== "") {
        console.log("loading private clubs because: " + $scope.auth_token);
        $scope.loadClub($scope.user_id, $scope.email, $scope.auth_token);
        $scope.loadClubItems($scope.user_id, $scope.email, $scope.auth_token);
    } else {
        console.log("loading public clubs because: " + $scope.auth_token);
        $scope.loadPublicClub($scope.user_id, $scope.email, $scope.auth_token);
    }
   
});
