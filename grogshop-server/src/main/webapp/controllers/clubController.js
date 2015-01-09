app.controller('clubController', function ($scope, $routeParams, $http, $rootScope) {
    $scope.pageClass = "clubPreview";
    $scope.imagePath = "resources/img/maintag-images/"
    $scope.club_id = $routeParams.club_id;
    $scope.preview_club = {};
    
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
            $scope.preview_club = data;

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
            $scope.memberships.push(club_id);
            $rootScope.$digest();
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
            $scope.memberships.splice($scope.memberships.indexOf(club_id), 1);
            console.log("after canceling the club: "+$scope.memberships);
        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };

    $scope.loadClub($scope.user_id, $scope.email, $scope.auth_token);


});