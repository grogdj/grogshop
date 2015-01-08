app.controller('homeController', function ($scope, $http, $rootScope) {

    $scope.imagePath = "resources/img/maintag-images/"
    
    
    $scope.loadClubs = function (user_id, email, auth_token) {
        console.log("loading clubs for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

        $http({
            method: 'GET',
            url: 'rest/clubs/all',
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
            data: {}
        }).success(function (data) {
            $rootScope.$broadcast("quickNotification", "Clubs loaded!");
            $scope.clubList = data;
            $scope.loadMemberships($scope.user_id, $scope.email, $scope.auth_token);
        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };

    
    
    $scope.loadMemberships = function (user_id, email, auth_token) {
        console.log("loading memberships for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

        $http({
            method: 'GET',
            url: 'rest/members/'+user_id,
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
            data: {}
        }).success(function (data) {
            $rootScope.$broadcast("quickNotification", "Memberships loaded!");
            $rootScope.memberships = data;
            console.log("my memberships: "+ $rootScope.memberships);

        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };

    $scope.loadClubs($scope.user_id, $scope.email, $scope.auth_token);
});