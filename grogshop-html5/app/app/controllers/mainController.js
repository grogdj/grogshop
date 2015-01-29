
(function () {
    
    var MainCtrl = function ($scope,  $cookieStore, $rootScope, $users, $memberships, appConstants) {
        $scope.auth_token = $cookieStore.get('auth_token');
        $scope.email = $cookieStore.get('email');
        $scope.user_id = $cookieStore.get('user_id');
        $scope.firstLogin = $cookieStore.get('firstLogin');
        $scope.index = 0;
        $scope.memberships = [];
        $scope.notifications = {};
        $scope.avatarStyle = "";

        $rootScope.$on('quickNotification', function (event, data) {
            var i;

            if (!data) {
                $scope.invalidNotification = true;
                return;
            }

            i = $scope.index++;
            $scope.invalidNotification = false;
            $scope.notifications[i] = data;

        });

        $scope.logoutUser = function () {
            $scope.auth_token = "";
            $scope.email = "";
            $scope.user_id = "";
            $scope.firstLogin = "";
            $scope.memberships = [];
            
            $users.logout().success(function (data) {
                console.log("You have been logged out." + data);
                $cookieStore.remove('auth_token');
                $cookieStore.remove('email');
                $scope.avatarStyle ="";
                $rootScope.$broadcast('goTo', "/");
               // $rootScope.$broadcast("quickNotification", "You have been logged out.");
            }).error(function (data) {
                console.log("Error: " + data);
                $rootScope.$broadcast("quickNotification", "Error: " + data);
            });

        };

        $scope.loginUser = function (isValid, user) {
            console.log("logged user " + user.email + " / password" + user.password);

            $scope.submitted = true;

            if (isValid) {
                $users.login(user).success(function (data) {
                   // $rootScope.$broadcast("quickNotification", "You are logged now, have fun!");
                    console.log("You are signed in! " + data.auth_token);

                    $cookieStore.put('auth_token', data.auth_token);
                    $cookieStore.put('email', data.email);
                    $cookieStore.put('user_id', data.user_id);
                    $cookieStore.put('firstLogin', data.firstLogin);
                    $scope.auth_token = $cookieStore.get('auth_token');
                    $scope.email = $cookieStore.get('email');
                    $scope.user_id = $cookieStore.get('user_id');
                    $scope.firstLogin = $cookieStore.get('firstLogin');
                    $scope.credentials = {};
                    $scope.submitted = false;
                    $scope.avatarStyle = {'background-image':'url('+appConstants.server + appConstants.context +'rest/public/users/'+$scope.user_id+'/avatar'+ '?' + new Date().getTime()+')'} ;
                    console.log("firstLogin: " + $scope.firstLogin);

                    if ($scope.firstLogin) {
                        $rootScope.$broadcast('goTo', "/firstlogin");
                    } else {
                        $rootScope.$broadcast('goTo', "/");
                    }

                }).error(function (data) {
                    console.log("Error: " + data.error);
                    $rootScope.$broadcast("quickNotification", "You are NOT logged in because:" + data.error);
                });
            }
        };

        $scope.loadMemberships = function () {
           
            $memberships.load().success(function (data) {
               // $rootScope.$broadcast("quickNotification", "Memberships loaded! ");
                console.log("data: "+ data);
                $scope.memberships = JSON.parse(JSON.stringify(data));
                console.log("my memberships: "+ $scope.memberships);

            }).error(function (data) {
                console.log("Error: " + data);
                $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
            });

        };

        $scope.hasMembership = function (club_id) {
            return $memberships.hasMembership(club_id, $scope.memberships);
        };

        if($scope.auth_token && $scope.auth_token !== ""){
            $scope.loadMemberships($scope.user_id, $scope.email, $scope.auth_token);
            $scope.avatarStyle = {'background-image':'url('+appConstants.server + appConstants.context + 'rest/public/users/'+$scope.user_id+'/avatar'+ '?' + new Date().getTime()+')'} ;

        }

        $rootScope.$on("updateProfileImage",function (event, data) {
            $scope.avatarStyle = {'background-image':'url('+appConstants.server + appConstants.context + 'rest/public/users/'+$scope.user_id+'/avatar'+ '?' + new Date().getTime()+')'} ;

        });
    };
    
    
    MainCtrl.$inject = ['$scope', '$cookieStore', '$rootScope', '$users', '$memberships', 'appConstants'];
    angular.module( "clubhouse" ).controller("MainCtrl", MainCtrl);
}());




