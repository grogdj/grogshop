app.controller('homeController', function ($scope, $http, $rootScope) {

    $scope.imagePath = "static/img/public-images/"
    
    
    $scope.loadClubs = function (user_id, email, auth_token) {
        console.log("loading clubs for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

        $http({
            method: 'GET',
            url: 'rest/clubs/all',
            headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + email, auth_token: auth_token},
            transformRequest: transformRequestToForm,
            data: {}
        }).success(function (data) {
            $rootScope.$broadcast("quickNotification", "Clubs loaded!");
            $scope.clubList = data;
        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };
    
    $scope.loadPublicClubs = function () {
        console.log("loading public clubs" );

        $http({
            method: 'GET',
            url: 'rest/public/clubs/all',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: transformRequestToForm,
            data: {}
        }).success(function (data) {
            $rootScope.$broadcast("quickNotification", "Public Clubs loaded!");
            $scope.clubList = data;
        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };

    if($scope.auth_token && $scope.auth_token !== ""){
        console.log("loading private clubs because: "+$scope.auth_token);
        $scope.loadMemberships($scope.user_id, $scope.email, $scope.auth_token);
        $scope.loadClubs($scope.user_id, $scope.email, $scope.auth_token);
    }else{
        console.log("loading public clubs because: "+$scope.auth_token);
        $scope.loadPublicClubs();
    }
});