'use strict';
(function(){

	var app = angular.module('grogshop', ['shoplistings', 'shopbids', 'shopnotifications', 'ngTagsInput']);

	app.controller('NavigationController', function (){
		this.section = 1;

		this.selectSection = function(setSection){
			this.section = setSection;
		};

		this.isSelected = function(checkSection){
			return this.section === checkSection;
		}
	});

	app.controller('CreateBarController', function (){
		this.status = false;
	});

}) ();