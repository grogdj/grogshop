app.controller('clubController', function ($scope, $routeParams, $http, $rootScope) {
    
    $scope.imagePath = "static/img/public-images/"

    $scope.club_id = $routeParams.club_id;
    $scope.club = {};
    $scope.url = $location.path();
    $scope.isPreview = $scope.url.indexOf('preview');
    $scope.products = [{'name':'My Product', 'price':'124.12351', 'desc':'My product is unique'}, {'name':'Other Product', 'price':'0.5', 'desc':'Description'}, {'name':'Expensive Product', 'price':'7000.5', 'desc':'Other product description'},{'name':'My Product', 'price':'124.12351', 'desc':'My product is uniqu adsask dlja lsdjaskld jaslkd asd as dajs ldasj dklasj dlkas jdaskl djaslk djasld jaslk djasl djaslk djasl das dljas kldjsa lkdja lkdja sldje'}, {'name':'Other Product', 'price':'0.5', 'desc':'Description'}, {'name':'Expensive Product', 'price':'7000.5', 'desc':'Other product description'},{'name':'My Product', 'price':'124.12351', 'desc':'My product is unique'}, {'name':'Other Product', 'price':'0.5', 'desc':'Description'}, {'name':'Expensive Product', 'price':'7000.5', 'desc':'Other product description'}];
    
    $scope.productWidth = $( window ).width()/5;
    
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
        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };

    $scope.loadClub($scope.user_id, $scope.email, $scope.auth_token);


});
