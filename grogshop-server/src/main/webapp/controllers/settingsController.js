app.controller('settingsController', function ($rootScope, $http, $scope) {
    
    $scope.settings =  {
        username:"",
        location:"",
        bio:""
    };
   
    var initialData = "";
    
    
    
    $scope.loadProfile = function ( user_id, email, auth_token) {
        console.log("Loading profile for user " + user_id +" with email: "+email +" and auth_token: "+auth_token);

        $http({
            method: 'GET',
            url: 'rest/users/' + user_id,
            headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + email, auth_token: auth_token },
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
            data: {}
        }).success(function (data) {
            $rootScope.$broadcast("quickNotification", "Loading your settings...!");
            console.log("username = "+data.username);
            console.log("location = "+data.location);
            console.log("bio = "+data.bio);


            $scope.settings.username = data.username;
            $scope.settings.location = data.location;
            $scope.settings.bio = data.bio;
            initialData = angular.copy($scope.settings)



        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };

    $scope.save = function (isValid) {

        console.log("save-changes");
        if (isValid) {
            $http({
                    method: 'POST',
                    url: 'rest/users/'+$scope.user_id+'/update',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:'+$scope.email, auth_token: $scope.auth_token},
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
                    data: {username: $scope.settings.username, location: $scope.settings.location, bio: $scope.settings.bio}
                }).success(function (data) {
                        $rootScope.$broadcast("quickNotification", "Your settings are now updated!");
                        initialData = angular.copy($scope.settings)
                }).error(function (data){
                        console.log("Error: "+data.error);
                        $rootScope.$broadcast("quickNotification", "Settings not saved because:" +data);
                });
            
            


        } else {
            alert("form not valid");
        }
        
    };

    $scope.cancel = function () {
        console.log("cancel-changes");
        console.log("initial data username: "+initialData.username);
        console.log("initial data bio: "+initialData.bio);
        console.log("initial data location: "+initialData.location);
        $scope.settings = angular.copy(initialData);
        $scope.settingsForm.$setPristine();
    };
    
    $scope.loadProfile($scope.user_id, $scope.email, $scope.auth_token);
});