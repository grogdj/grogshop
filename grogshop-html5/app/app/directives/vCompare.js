(function(){
    var vCompare = function() {
        return {
            require: 'ngModel',
            scope: {

              reference: '=vCompare'

            },
            link: function(scope, elm, attrs, ctrl) {
              ctrl.$parsers.unshift(function(viewValue, $scope) {

                var noMatch = viewValue != scope.reference;
                ctrl.$setValidity('noMatch', !noMatch);
              });

              scope.$watch("reference", function(value) {;
                ctrl.$setValidity('noMatch', value === ctrl.$viewValue);

              });
            }
         }
    };
    
    angular.module( "clubhouse" ).directive('vCompare', vCompare);
    
}());

