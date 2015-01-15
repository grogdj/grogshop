app.controller('signUpController', function ($scope, $http, $rootScope) {
    $scope.registerUser = function (isValid) {
        // console.log("asd " + $scope.newUser.email + " / " + $scope.newUser.pass);

        $scope.submitted = true;

        if (isValid) {
            $http({
                method: 'POST',
                url: 'rest/auth/register',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: transformRequestToForm,
                data: {email: $scope.newUser.email, password: $scope.newUser.pass}
            }).success(function (data) {
                
               // $rootScope.$broadcast("quickNotification", "You are  now registered, please login!");
                $rootScope.$broadcast("goTo", "/");
                console.log("Welcome to " + $scope.newUser.email + "!");
                
                $scope.registerForm.$setPristine();

            }).error(function (data) {
                $rootScope.$broadcast("quickNotification", "Something failed: "+data.error);
                console.log("Error : " + data.error + "!");


            });
        } else {
            console.log("Invalid Form");
        }
    };
});


