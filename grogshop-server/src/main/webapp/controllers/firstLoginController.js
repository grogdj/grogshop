app.controller('firstLoginController', function ($rootScope, $http, $scope, $cookieStore) {
    
    //GRID
    $scope.imagePath = "static/img/public-images/"
    $scope.interests = [];
    
    $scope.newProfile = function (user_id, email, auth_token) {
        console.log("creating profile for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

        $http({
            method: 'POST',
            url: 'rest/users/new',
            headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + email, auth_token: auth_token},
            transformRequest: transformRequestToForm,
            data: {user_id: user_id}
        }).success(function (data) {
            //$rootScope.$broadcast("quickNotification", "Profile created!");
            $cookieStore.put('firstLogin', false);



        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };
    
    $scope.loadInterests = function (user_id, email, auth_token) {
        console.log("creating profile for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

        $http({
            method: 'GET',
            url: 'rest/interests/all',
            headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + email, auth_token: auth_token},
            transformRequest: transformRequestToForm,
            data: {}
        }).success(function (data) {
            //$rootScope.$broadcast("quickNotification", "Tags loaded!");

            $scope.interests = data;
            $scope.initImages();
            $scope.loadUserInterests($scope.user_id, $scope.email, $scope.auth_token);

        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };

     $scope.loadUserInterests = function (user_id, email, auth_token) {
        console.log("creating profile for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

        $http({
            method: 'GET',
            url: 'rest/users/'+user_id+"/interests",
            headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + email, auth_token: auth_token},
            transformRequest: transformRequestToForm,
            data: {user_id: user_id}
        }).success(function (data) {
            //$rootScope.$broadcast("quickNotification", "Users Tags loaded!");
            
            $scope.selectedInterests = data;
            console.log("User interests: "+$scope.selectedInterests);
        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };
    
    $scope.updateInterests = function (user_id, email, auth_token) {
        console.log("updating interests for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);
        console.log("selected interests: "+$scope.selectedInterests);
        $http({
            method: 'POST',
            url: 'rest/users/'+user_id+"/interests/update",
            headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + email, auth_token: auth_token},
            transformRequest: transformRequestToForm,
            data: { interests: JSON.stringify($scope.selectedInterests)}
        }).success(function (data) {
            //$rootScope.$broadcast("quickNotification", "Interest updated !");
            $rootScope.$broadcast("goTo", "/settings");



        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };
    var firstLogin = $cookieStore.get('firstLogin');
    if(firstLogin){
        $scope.newProfile($scope.user_id, $scope.email, $scope.auth_token);
    }
    $scope.loadInterests($scope.user_id, $scope.email, $scope.auth_token);
    

    $scope.initImages = function(){
        $scope.numberOfItem = Math.floor($(window).width() / 300);
        $scope.pageClass = "firstLoginScreen";
        $scope.elementWidth = (($(window).width()-4) / $scope.numberOfItem)-4;
        $scope.elementHeight = (($(window).height() - 4) / Math.ceil(($scope.interests.length / $scope.numberOfItem)))-4;
        
    }


    $(window).resize(function () {
        $scope.numberOfItem = Math.floor($(window).width() / 300);
        $scope.elementWidth = (($(window).width()-4) / $scope.numberOfItem)-4;
        $scope.elementHeight = (($(window).height() - 4) / Math.ceil(($scope.interests.length / $scope.numberOfItem)))-4;
        $scope.$digest();
        
    });

    //CLICK CHECK
    $scope.selectedInterests = [];
    $scope.toggleCheck = function (value) {
        
        if ($scope.selectedInterests.map(function(e) { return e.name; }).indexOf(value.name) > -1) {
            $scope.selectedInterests.splice($scope.selectedInterests.map(function(e) { return e.name; }).indexOf(value.name), 1);
        } else {
            $scope.selectedInterests.push(value);
        }
        
        console.log($scope.selectedInterests);
    }

    $scope.isChecked = function (value) {
            return $scope.selectedInterests.map(function(e) { return e.name; }).indexOf(value.name) > -1;
    };

});