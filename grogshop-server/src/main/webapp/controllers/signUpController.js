 app.controller('signUpController', function($scope, $http, $rootScope) {
    $scope.registerUser = function (user) {
            console.log("asd " + user.email + " / " + user.password);

            $http({
                method: 'POST',
                url: 'rest/auth/register',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
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
                data: {email: user.email, password: user.password}
            }).success(function (data) {
               
                    $rootScope.$broadcast("quickNotification", "You are  now registered, please login!");
                    $rootScope.$broadcast("goTo", "/");
                    console.log("Welcome to " + data + "!");
                    $scope.newUser = "";
                
            }).error(function (data) {
                    $rootScope.$broadcast("quickNotification", "Something failed, please retry!");
                    console.log("Error : " + data + "!");
                    
                
            });
        };
});
