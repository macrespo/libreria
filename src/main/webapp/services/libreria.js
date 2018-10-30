'use strict';

angular.module('libreria')
    .service('libreria', function ($http) {
        return {
            list: function (success) {
                return $http.get("/rest/libreria").then(success);
            },
            search: function (palabra, success) {
            	return $http.get("/rest/libreria/search/" + palabra).then(success);
            },
            get: function (id, success) {
            	return $http.get("/rest/libreria/" + id).then(success);
            },
            del: function (id, success) {
            	return $http.delete("/rest/libreria/" + id).then(success);
            },
            save: function (libro, success) {
                return $http.post("/rest/libreria", libro).then(success);
            }
        };
    });
