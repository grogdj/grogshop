app.controller('clubController', function ($scope, $routeParams, $http, $rootScope, $location) {
    
    $scope.imagePath = "static/img/public-images/"

    $scope.club_id = $routeParams.club_id;
    $scope.club = {};
    $scope.url = $location.path();
    $scope.isPreview = $scope.url.indexOf('preview');

    $scope.items = [];

    $scope.productWidth = $(window).width() / 5;

    

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


    
    
    $scope.smallerThan = function(a,b) {
      return a <= b;
    };
    $scope.greaterThan = function(a,b) {
      return a >= b;
    };
    
    
    $scope.newItem = function (isValid) {
        if(isValid){
            console.log("adding new item  for user " + $scope.user_id + " with email: " + $scope.email + " and auth_token: " + $scope.auth_token);
            var tagsString = angular.copy($scope.newItem.tags);
            var itemToSend = {club_id: $scope.club_id, user_id: $scope.user_id, name: $scope.newItem.title, description: $scope.newItem.description, 
                     tags: tagsString.toString(), price: $scope.newItem.price};
            console.log(itemToSend);
            $http({
                method: 'POST',
                url: 'rest/items/new',
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + $scope.email, auth_token: $scope.auth_token},
                transformRequest: transformRequestToForm,
                data: itemToSend
            }).success(function (data) {
                $rootScope.$broadcast("quickNotification", "Item Created!");
                var newItem = {item_id: data, club_id: $scope.club_id, user_id: $scope.user_id, name: $scope.newItem.title, description: $scope.newItem.description, 
                     tags: tagsString, price: $scope.newItem.price};
                $scope.items.push(newItem);
                console.log("new item added: "+newItem);
                $scope.newPostForm.$setPristine();
                $('#newProductModal').modal('hide');
            }).error(function (data) {
                console.log("Error: " + data);
                $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
            });
        }else{
            $rootScope.$broadcast("quickNotification", "Form not valid: Something went wrong!");
            
        }

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
