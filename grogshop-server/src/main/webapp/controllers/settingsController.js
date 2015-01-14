app.controller('settingsController', function ($rootScope, $http, $scope, $upload, $timeout) {
    $scope.settings = {
        username: "",
        location: "",
        bio: "",
        avatarUrl: "rest/public/users/" + $scope.user_id + "/avatar"
    };

    $scope.uploading = false;
    $scope.uploadPercentage = 0;
    
    $scope.fileReaderSupported = window.FileReader != null && (window.FileAPI == null || FileAPI.html5 != false);
    
    console.log("AVATAR URL " + $scope.settings.avatarUrl);

    $scope.uploadFile = function (files, event) {
        console.log("Files : " + files + "-- event: " + event);
        var file = files[0];
        $scope.upload = $upload.upload({
            url: 'rest/users/' + $scope.user_id + '/avatar/upload', // upload.php script, node.js route, or servlet url
            method: 'POST',
            headers: {'Content-Type': 'multipart/form-data', service_key: 'webkey:' + $scope.email, auth_token: $scope.auth_token},
            file: file,
        }).progress(function (evt) {
            $scope.uploadPercentage = parseInt(100.0 * evt.loaded / evt.total);
            $scope.uploading = true;
            console.log('progress: ' + parseInt(100.0 * evt.loaded / evt.total) + '% file :' + evt.config.file.name);
        }).success(function (data) {
            // file is uploaded successfully
            console.log('file ' + file.name + 'is uploaded successfully. Response: ' + data);
            $scope.uploading = false;
            $scope.settings.avatarUrl = "";
            $scope.settings.avatarUrl = "rest/public/users/" + $scope.user_id + "/avatar" + '?' + new Date().getTime();


        }).error(function (data) {
            console.log('file ' + file.name + ' upload error. Response: ' + data);

        });


    };
    
    
    $scope.generateThumb = function(file) {
        console.log(file);
		if (file != null) {
			if ($scope.fileReaderSupported && file.type.indexOf('image') > -1) {
                            console.log("file oh yeah");
				$timeout(function() {
					var fileReader = new FileReader();
					fileReader.readAsDataURL(file);
					fileReader.onload = function(e) {
						$timeout(function() {
                                                    console.log("file oh yeah 2: "+e.target.result);
							file.dataUrl = e.target.result;
						});
					}
				});
			}
		}
	};


    var initialData = "";



    $scope.loadProfile = function (user_id, email, auth_token) {
        console.log("Loading profile for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);

        $http({
            method: 'GET',
            url: 'rest/users/' + user_id,
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
            $rootScope.$broadcast("quickNotification", "Loading your settings...!");
            console.log("username = " + data.username);
            console.log("location = " + data.location);
            console.log("bio = " + data.bio);


            $scope.settings.username = data.username;
            $scope.settings.location = data.location;
            $scope.settings.bio = data.bio;
            initialData = angular.copy($scope.settings)



        }).error(function (data) {
            console.log("Error: " + data);
            $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
        });

    };

    $scope.save = function (isValid, files, event) {

        console.log("save-changes");
        if (isValid) {
            $http({
                method: 'POST',
                url: 'rest/users/' + $scope.user_id + '/update',
                headers: {'Content-Type': 'application/x-www-form-urlencoded', service_key: 'webkey:' + $scope.email, auth_token: $scope.auth_token},
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
                data: {username: $scope.settings.username, location: $scope.settings.location, bio: $scope.settings.bio},
                
            }).success(function (data) {
                $rootScope.$broadcast("quickNotification", "Your settings are now updated!");
                initialData = angular.copy($scope.settings)
                $scope.uploadFile(files, event);
            }).error(function (data) {
                console.log("Error: " + data.error);
                $rootScope.$broadcast("quickNotification", "Settings not saved because:" + data);
            });
        } else {
            alert("form not valid");
        }

    };

    $scope.cancel = function () {
        console.log("cancel-changes");
        console.log("initial data username: " + initialData.username);
        console.log("initial data bio: " + initialData.bio);
        console.log("initial data location: " + initialData.location);
        $scope.settings = angular.copy(initialData);
        $scope.settingsForm.$setPristine();
    };

    $scope.loadProfile($scope.user_id, $scope.email, $scope.auth_token);
});