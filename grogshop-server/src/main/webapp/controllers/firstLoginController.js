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
});