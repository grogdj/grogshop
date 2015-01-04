app.controller('settingsController',  function($scope){
	var settingsvalues = {username:"test", location:"Londres", bio:"asd kajd klajd lak jdlajd klasjdl ak"};
	$scope.settings = settingsvalues;
	var inicialData = angular.copy($scope.settings);

	$scope.save = function(isValid){

		console.log("save-changes");
		if(isValid){
			alert("submit changes");


		}else {
			alert("something else");
		};
	};

	$scope.cancel = function(){
		console.log("cancel-changes");
		$scope.settings = inicialData;
	
		$scope.settingsForm.$setPristine();
	};
});