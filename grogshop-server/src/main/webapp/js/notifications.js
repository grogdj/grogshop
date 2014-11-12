(function () {
    var app = angular.module('shopnotifications', []);

    app.directive("notificationsItems", [function () {
            return {
                restrict: 'E',
                templateUrl: 'notifications.html',
                controller: function () {
                    console.log('notification controller on! ');
                    var shop = this;
                    shop.notifications = [];

                    var wsUri = "ws://" + document.location.hostname + ":" + document.location.port + "/grogshop-server/" + "shop";
                    var websocket = new WebSocket(wsUri);


                    websocket.onopen = function (evt) {
                        onOpen(evt)
                    };
                    websocket.onmessage = function (evt) {
                        onMessage(evt)
                    };
                    websocket.onerror = function (evt) {
                        onError(evt)
                    };
                    var status = document.getElementById("status");



                    function onOpen() {
                        websocket.send("joined");
                        writeToScreen("Connected to " + wsUri);
                    }

                    function onMessage(evt) {
                        console.log("onMessage: " + evt.data);
                         var notification = JSON.parse(event.data);
                        shop.notifications.push(notification);

                    }

                    function onError(evt) {
                        writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
                    }

                    function writeToScreen(message) {
                        status.innerHTML += message + "<br>";
                    }


                },
                controllerAs: 'notificationsCtrl'
            }
        }]);






})();
