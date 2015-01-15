app.controller('homeController', function ($scope, $http, $rootScope) {

    $scope.imagePath = "static/img/public-images/"
    
    $scope.interests = [];
    $scope.userInterests = [];
    
     $scope.loadUserInterests = function (user_id, email, auth_token) {
        console.log("loading interests for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

        $http({
            method: 'GET',
            url: 'rest/users/'+user_id+'/interests',
            headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + email, auth_token: auth_token},
            transformRequest: transformRequestToForm,
            data: {}
        }).success(function (data) {
            //$rootScope.$broadcast("quickNotification", "User Interest loaded!");
            $scope.interests = data;
            for (x in $scope.interests) {
                $scope.userInterests.push($scope.interests[x].name);
            }
            console.log($scope.userInterests);
        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };
    
    $scope.loadAllInterests = function () {
        console.log("loading all interests");

        $http({
            method: 'GET',
            url: 'rest/public/interests/all',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: transformRequestToForm,
            data: {}
        }).success(function (data) {
            //$rootScope.$broadcast("quickNotification", "User Interest loaded!");
            $scope.interests = data;
        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };
    
    $scope.loadClubs = function (user_id, email, auth_token) {
        console.log("loading clubs for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

        $http({
            method: 'GET',
            url: 'rest/clubs/all',
            headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + email, auth_token: auth_token},
            transformRequest: transformRequestToForm,
            data: {}
        }).success(function (data) {
            //$rootScope.$broadcast("quickNotification", "Clubs loaded!");
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
            //$rootScope.$broadcast("quickNotification", "Public Clubs loaded!");
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
        $scope.loadUserInterests($scope.user_id, $scope.email, $scope.auth_token);
    }else{
        console.log("loading public clubs because: "+$scope.auth_token);
        $scope.loadPublicClubs();
        $scope.loadAllInterests();
    }
  
      //CUSTOM FILTER FOR INTERESTS
    $scope.checkUserInterest = function(interest){
            //interest == userInterests[i];
            var temp = false;
            if($scope.userInterests.length > 0){
                for(i=0; i < $scope.userInterests.length; i++) {
                    if(interest.interest == $scope.userInterests[i]){

                        temp = true;
                        break;
                    }
                }
            }else{
                temp = true;
            }
        return temp;        
    };
});
