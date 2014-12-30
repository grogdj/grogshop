/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


services.factory('Auth', function ($http) {
    return {
        load: function () {
            return $http.get('/rest/auth');
        },
        logout: function () {
            return $http.get('/rest/auth/logout');
        },
        login: function (inputs) {
            return $http.post('/rest/auth/login', inputs);
        },
        register: function (inputs) {
            return $http.post('/rest/auth/register', inputs);
        },
        check: function () {
            return $http.get('/rest/auth/check');
        }
    }
});