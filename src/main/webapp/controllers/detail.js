'use strict';

angular.module('libreria')
    .controller('DetailCtrl', function ($scope, $routeParams, libreria) {

        $scope.load = function(id) {
        	libreria.get(id, function (libro) {
                $scope.libro = libro.data;
            });
        }

        $scope.libro = {};
        
        $scope.load($routeParams.id);
    });
