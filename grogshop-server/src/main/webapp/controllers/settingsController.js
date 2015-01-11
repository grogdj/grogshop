app.controller('settingsController', function ($rootScope, $http, $scope, $upload) {
    $scope.settings = {
        username: "",
        location: "",
        bio: "",
        avatarUrl: "rest/public/users/"+$scope.user_id+"/avatar"
    };

    $scope.uploading = false;
    $scope.uploadPercentage = 0;

    console.log("AVATAR URL " + $scope.settings.avatarUrl);

    $scope.fileSelected= function (files, event) {
        console.log("Files : "+files + "-- event: "+event);
        var file = files[0];
        $scope.upload = $upload.upload({
            url: 'rest/users/' + $scope.user_id + '/avatar/upload', // upload.php script, node.js route, or servlet url
            method: 'POST',
            headers: {'Content-Type': 'multipart/form-data', service_key: 'webkey:' + $scope.email, auth_token: $scope.auth_token},
            file: file, // single file or a list of files. list is only for html5
            //fileName: 'doc.jpg' or ['1.jpg', '2.jpg', ...] // to modify the name of the file(s)
            //fileFormDataName: myFile, // file formData name ('Content-Disposition'), server side request form name
            // could be a list of names for multiple files (html5). Default is 'file'
            //formDataAppender: function(formData, key, val){}  // customize how data is added to the formData. 
            // See #40#issuecomment-28612000 for sample code

        }).progress(function (evt) {
            $scope.uploadPercentage = parseInt(100.0 * evt.loaded / evt.total);
             $scope.uploading = true;
            console.log('progress: ' + parseInt(100.0 * evt.loaded / evt.total) + '% file :' + evt.config.file.name);
        }).success(function (data) {
            // file is uploaded successfully
            console.log('file ' + file.name + 'is uploaded successfully. Response: ' + data);
             $scope.uploading = false;
            $scope.settings.avatarUrl = "";
           $scope.settings.avatarUrl = "rest/public/users/"+$scope.user_id+"/avatar"+ '?' + new Date().getTime();


        }).error(function (data) {
            console.log('file ' + file.name + ' upload error. Response: ' + data);
        });
        //.then(success, error, progress); // returns a promise that does NOT have progress/abort/xhr functions
        //.xhr(function(xhr){xhr.upload.addEventListener(...)}) // access or attach event listeners to 
        //the underlying XMLHttpRequest

        /* alternative way of uploading, send the file binary with the file's content-type.
         Could be used to upload files to CouchDB, imgur, etc... html5 FileReader is needed. 
         It could also be used to monitor the progress of a normal http post/put request. 
         Note that the whole file will be loaded in browser first so large files could crash the browser.
         You should verify the file size before uploading with $upload.http().
         */
        // $scope.upload = $upload.http({...})  // See 88#issuecomment-31366487 for sample code.

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

    $scope.save = function (isValid) {

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
                data: {username: $scope.settings.username, location: $scope.settings.location, bio: $scope.settings.bio}
            }).success(function (data) {
                $rootScope.$broadcast("quickNotification", "Your settings are now updated!");
                initialData = angular.copy($scope.settings)
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