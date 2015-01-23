(function(){
    var clubController = function ($scope, $routeParams, $rootScope, $location, $upload, $timeout, $clubs, $memberships, $items) {
        $scope.imagePath = "static/img/public-images/"

        $scope.club_id = $routeParams.club_id;
        $scope.club = {};
        $scope.url = $location.path();
        $scope.isPreview = $scope.url.indexOf('preview');
        $scope.fileReaderSupported = window.FileReader != null && (window.FileAPI == null || FileAPI.html5 != false);
        $scope.items = [];

        //$scope.productWidth = $(window).width() / 5;

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

        $scope.loadClub = function () {
            //console.log("loading clubs for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);
            $clubs.load($scope.club_id)
            .success(function (data) {
                //$rootScope.$broadcast("quickNotification", "Club loaded!");
                $scope.club = data;
            }).error(function (data) {
                console.log("Error: " + data);
                $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
            });

        };

        $scope.loadClubItems = function () {
            //console.log("loading clubs for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);
            $clubs.loadItems($scope.club_id)
            .success(function (data) {
                //$rootScope.$broadcast("quickNotification", "items loaded!");
                console.log("My Items");
                console.log(data);
                $scope.items = data;

            }).error(function (data) {
                console.log("Error: " + data);
                $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
            });

        };


        $scope.joinClub = function (club_id) {
            //console.log("joining clubs for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);
            $memberships.join(club_id)
            .success(function (data) {
                //$rootScope.$broadcast("quickNotification", "Club Joined!");
                $scope.memberships.push(parseInt(club_id));
                $rootScope.$broadcast('goTo', "/club/" + $scope.club_id);
                console.log("after joining the club: "+$rootScope.memberships);
            }).error(function (data) {
                console.log("Error: " + data);
                $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
            });

        };

        $scope.cancelMembership = function (club_id) {
            //console.log("canceling membership ("+club_id+") for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);
            $memberships.cancel(club_id)
            .success(function (data) {
                //$rootScope.$broadcast("quickNotification", "Club Membership Cancelled!");
                $scope.memberships.splice($scope.memberships.indexOf(parseInt(club_id)), 1);
                console.log("after canceling the club: "+$scope.memberships);
                $rootScope.$broadcast('goTo', "/club/preview/" + $scope.club_id);
            }).error(function (data) {
                console.log("Error: " + data);
                $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
            });

        };


        //FILTER BAR 

        $scope.smallerThan = function(a,b) {
          return a <= b;
        };
        $scope.greaterThan = function(a,b) {
          return a >= b;
        };

           $scope.priceRange = {
            range: {
                min: 0,
                max: 10000
            },
            minPrice: 0,
            maxPrice: 10000
        };

        ///////////////////////////////////////////////////////////// POST ITEM
        $scope.postItem = function (isValid, files) {
            $scope.submitted = true;
            if(isValid && $scope.newItem.tags.length > 0 && $scope.itemPicFile != null){

                console.log("adding new item  for user " + $scope.user_id + " with email: " + $scope.email + " and auth_token: " + $scope.auth_token);

                var itemToSend = {club_id: $scope.club_id, user_id: $scope.user_id, name: $scope.newItem.title, description: $scope.newItem.description, 
                         tags: JSON.stringify($scope.newItem.tags), minPrice: $scope.newItem.minPrice, type: 'POST'};
                $items.post(itemToSend)
                .success(function (data) {
                    //$rootScope.$broadcast("quickNotification", "Item Created!");
                    var newItem = {id: data, club_id: $scope.club_id, user_id: $scope.user_id, user_email: $scope.email, name: $scope.newItem.title, description: $scope.newItem.description, 
                         tags: $scope.newItem.tags, price: $scope.newItem.price, type: 'POST'};
                     var item_id = data;

                    console.log("new item added: "+newItem);

                    $scope.uploadFile(item_id, files,newItem);

                }).error(function (data) {
                    console.log("Error: " + data);
                    $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
                });
            }else{

                $rootScope.$broadcast("quickNotification", "Form not valid: Something went wrong!");

            }

        };

        $scope.resetPostForm = function() {
            $scope.newItem={};
            $scope.itemPicFile= null;
            $scope.newPostForm.$setPristine();
            $scope.submitted = false;
        }

        $scope.deletePostImage = function() {
            $scope.itemPicFile= null;
        }
        
        ///////////////////////////////////////////////////////////// POST REQUEST
        
        $scope.postRequest = function (isValid, files) {
            $scope.submittedRequest = true;
            if(isValid && $scope.newItem.tags.length > 0){

                console.log("adding new request  for user " + $scope.user_id + " with email: " + $scope.email + " and auth_token: " + $scope.auth_token);

                var itemToSend = {club_id: $scope.club_id, user_id: $scope.user_id, name: $scope.newRequest.title, description: $scope.newRequest.description, 
                         tags: JSON.stringify($scope.newRequest.tags), minPrice: $scope.newRequest.minPrice, maxPrice: $scope.newRequest.maxPrice, type: 'REQUEST'};
                $items.post(itemToSend)
                .success(function (data) {
                    //$rootScope.$broadcast("quickNotification", "Item Created!");
                    var newRequest = {id: data, club_id: $scope.club_id, user_id: $scope.user_id, user_email: $scope.email, name: $scope.newRequest.title, description: $scope.newRequest.description, 
                         tags: $scope.newRequest.tags, minPrice: $scope.newRequest.minPrice, maxPrice: $scope.newRequest.maxPrice, type: 'REQUEST'};
                     var request_id = data;

                    console.log("new request added: "+newRequest);

                    $scope.uploadFile(request_id, files,newRequest);

                }).error(function (data) {
                    console.log("Error: " + data);
                    $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
                });
            }else{

                $rootScope.$broadcast("quickNotification", "Form not valid: Something went wrong!");

            }

        };

        $scope.resetRequestForm = function() {            
            $scope.newRequest={};
            $scope.requestPicFile= null;
            $scope.newRequestForm.$setPristine();
            $scope.submittedRequest = false;
        }

        $scope.deleteRequestImage = function() {
            $scope.requestPicFile= null;
        }
        
        
        ///////////////////////////////////////////////////////////// 
        

        $scope.removeItem = function (item) {

                console.log("remove new item  for user " + $scope.user_id + " with email: " + $scope.email + " and auth_token: " + $scope.auth_token);

                $items.remove(item.id)
                .success(function (data) {
                    //$rootScope.$broadcast("quickNotification", "Item Removed!");

                    $scope.items.splice($scope.items.indexOf(item), 1);

                }).error(function (data) {
                    console.log("Error: " + data);
                    $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
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

        $scope.uploadFile = function (item_id, files, newItem) {
            console.log("Files : " + files + "-- event: " + event);
            var file = files[0];
            $scope.upload = $items.uploadImage(item_id,file)
            .progress(function (evt) {
                $scope.uploadPercentage = parseInt(100.0 * evt.loaded / evt.total);
                $scope.uploading = true;
                console.log('progress: ' + parseInt(100.0 * evt.loaded / evt.total) + '% file :' + evt.config.file.name);
            }).success(function (data) {
                // file is uploaded successfully
                console.log('file ' + file.name + 'is uploaded successfully. Response: ' + data);
                $scope.uploading = false;
                $('#newProductModal').modal('hide');
                $scope.items.push(newItem);
                //
                $scope.newItem={};
                $scope.itemPicFile= null;
                $scope.newPostForm.$setPristine();
                $scope.submitted = false;
                //
                $scope.newRequest={};
                $scope.requestPicFile= null;
                $scope.newRequestForm.$setPristine();
                $scope.submittedRequest = false;


            }).error(function (data) {
                console.log('file ' + file.name + ' upload error. Response: ' + data);

            });


        };
        

        $scope.loadPublicClub = function () {
          // console.log("loading public club (" + $scope.club_id + ") for user " + user_id + " with email: " + email + " and auth_token: " + auth_token);
            $clubs.loadPublic($scope.club_id)
          .success(function (data) {
               //$rootScope.$broadcast("quickNotification", "Public Club loaded!");
               $scope.club = data;
           }).error(function (data) {
               console.log("Error: " + data);
               $rootScope.$broadcast("quickNotification", "Something went wrong!" + data);
           });

        };

        if ($scope.auth_token && $scope.auth_token !== "") {
            console.log("loading private clubs because: " + $scope.auth_token);
            $scope.loadClub($scope.user_id, $scope.email, $scope.auth_token);
            $scope.loadClubItems($scope.user_id, $scope.email, $scope.auth_token);
        } else {
            console.log("loading public clubs because: " + $scope.auth_token);
            $scope.loadPublicClub($scope.user_id, $scope.email, $scope.auth_token);
        }
    };
    
    clubController.$inject = ['$scope','$routeParams','$rootScope','$location','$upload','$timeout', '$clubs','$memberships','$items'];
    angular.module( "clubhouse" ).controller( "clubController", clubController);
    
}());


