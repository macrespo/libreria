'use strict';

angular.module('libreria')
    .controller('ListCtrl', function ($scope, $location, libreria) {

        $scope.load = function() {
        	libreria.list(function (list) {
                $scope.list = list.data;
            });
        }

        $scope.search = function() {
        	if (!$scope.palabra)
        		$scope.load();
        	else
	        	libreria.search($scope.palabra, function (list) {
	                $scope.list = list.data.sort(function(a, b) {
	                	return a.autor.localeCompare(b.autor)
	                });
	            });
        }
        
        $scope.viewSelected = function() {
        	$location.path($scope.selected.libro);
        }

        $scope.delSelected = function() {
        	var eliminar = confirm("Va a eliminar un libro de la lirería");
        	if (eliminar == true)
            	libreria.del($scope.selected.libro, function (list) {
            		let index = undefined;
            		for (let i = 0; i < $scope.list.length; i++) {
            			if ($scope.list[i].id === $scope.selected.libro)
            				index = i;
            		}
            		if (index !== undefined)
            			$scope.list.splice(index, 1);
                });
        }

        $scope.select = function(id) {
        	if ($scope.selected.libro === id)
        		$scope.selected.libro = null;
        	else
        		$scope.selected.libro = id;
        }
        
        $scope.save = function() {
        	if ($scope.form.$valid)
	        	libreria.save($scope.nuevo, function() {
	                $scope.nuevo = {};
	                $scope.form.$setUntouched();
	                $scope.load();
	            });
        }
        
        $scope.selected = {
        	libro: null
        };
        
        $scope.palabra = null;

        $scope.nuevo = {};

        $scope.load();
    });
