 
$( document ).ready(function() {

   $('#login-popover').popover({ 

	    html : true,
   		title: function() {
      	  return $("#login-form-title").html();
	    },
	    content: function() {
	      return $("#login-form-content").html();
	    },
	    placement : 'bottom',
	    trigger : 'click' 
	});

});
 