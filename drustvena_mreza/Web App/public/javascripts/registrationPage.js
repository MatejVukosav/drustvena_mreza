//utilities
/**
    Tells whether the popover of the given element(which must be a tag with a popover registered on it) 
    is visible(showing);
*/

function isPopoverVisible(elementID) {
	try {
		var isVisible = $('#' + elementID).data('bs.popover').tip().hasClass('in');
		//this error is likely to occur because the popover doesn't exist
	} catch (err) {
		return false;
	}

	return isVisible;
}

/**
    This function is useful because if a you try to 
    show a popover and it is already visible, it will 
    first dissapear and then be shown(the effect will be visible)

    Returns false if the popover hasn't yet been initialized.
*/
function showPopoverIfInvisible(elementID) {
	if(!isPopoverVisible(elementID)) {
		$('#' + elementID).popover("show");
	}
}

function isPopoverInitialized(elementID) {
	try {
		var isVisible = $('#' + elementID).data('bs.popover').tip().hasClass('in');
		//this error is likely to occur because the popover doesn't exist
	}catch(err) {
		return false;
	}

	return true;
}

/**
    Use this method to create(initialize) a new popover with new content, 
    or to update an existing popover with new content.
    This method will create a visual delay effect which the user would expect.
*/
function fillContentAndshowPopover(elementID, popoverContentMessage, popoverTitle) {
	if(isPopoverInitialized(elementID)) {
		setTimeout(function() {
			var popover = $("#" + elementID).data('bs.popover');
			popover.options.content = popoverContentMessage;
			popover.tip().find('.popover-content').html(popoverContentMessage);
			popover.options.title = popoverTitle;
			popover.tip().find('.popover-title').html(popoverTitle);
			$('#' + elementID).popover("show");
		},0);

		//        //if this is a new popover it has to be initialized
		//        $('#'+elementID).popover("destroy").popover({
		//            content : function(){return popoverContentMessage},
		//            title : function(){ return popoverTitle}
		//        });
		//    
		//        //$('#'+elementID).popover("show");
		//        setTimeout(function(){
		//            $('#'+elementID).popover("show");
		//        },200);
		//        
		//could be that the popover hasnt been initialised
	} else {
		setTimeout(function(){
			$('#'+elementID).popover({
				content : function(){return popoverContentMessage},
				title : function(){ return popoverTitle}
			});
			$('#'+elementID).popover("show");
		},0);
	}
}

function hidePopover(elementID) {
	if(isPopoverVisible(elementID)) {
		$('#' + elementID).popover("hide");
	}
}

/**
    Submits a form using the ajax post method. Sends 
    a JSON of all the <input> values within the form.
    The keys in JSON will be the "name" attributes of the 
    respective <input> fields.
    Eg., <input name="number" value="123">  will be 
    sent as JSON: {"name":123};
    Expects the form to contain the destination url in 
    the "action" attribute.
    Expects the given form object to be a jQuery object,
    and not a HTML DOM object.
    The callback function will be called with the 
    data returned by the server.
*/
function submitForm(form, callbackFunction) {
	var url = form.attr("action");
	var formData = {};
	$(form).find("input[name]").each(function (index, node) {
		formData[node.name] = node.value;
	});
	$.post(url, formData).done(function (data) {
		callbackFunction(data);
	});
}

/**
    Returns whether the give data value is valid. 
    Use this function with the JSON data obtained from the 
    server. Eg., if the JSON is var json= {"username":"Username invalid!"} 
    then the value passed to this method should be json.username -> "Username invalid".
    This method will return true only if the given value is undefined or "true".
*/
function isValid(value) {
	if (value ==undefined) {
		return true;
	}
	if(value.trim()=="true") {
		return true;
	}

	return false;
}

/**
    Shows a previously hidden alert.
*/
function showAlert(alertID) {
	var alert = document.getElementById(alertID);
	alert.style.visibility="visible";
	alert.style.display = "inline";
}



//actual page code
//urls
var homepage = "/homepage";

var usernameAvailable = false;
var usernameTimeoutStarted = false;

var popoverHeaderWarning = "Warning";
var popoverHeaderSuccess = "Success";
var popoverUsernameNotAvailable = "The username is taken";
var popoverUsernameAvailable = "The username is available";
var popoverUsernameWarningStartsWithNumber = "The username must start with a letter";
var popoverUsernameWarningContainsSpecialSymbols = "The username can only be composed of letters and numbers";
var popoverUsernameWarningNotAvailable = "This username is already taken!";
var popoverPasswordWarning = "Passwords must be at least 8 symbols long and contain both letters and numbers.";
var popoverRetypePasswordWarning = "The passwords don't match";
var popoverEmailWarning = "Invalid e-mail address";
$(document).ready(initializeInputPopovers());

function initializeInputPopovers() {
	//initializes all element popovers, must be called -> says tutorial
	//blocks any further popover calls, so blocks inputting any text -> says I -> never call it(alternativa je da ubijem popover(destroy) prije nego ga napunim ponovo)
	//$('[data-toggle="popover"]').popover();

	//these calls will also initialize the popover
	//fill popover content messages
	//all done at the spot
	//             $("#password").popover({
	//                 content : popoverPasswordWarning,
	//                 title : popoverHeaderWarning
	//             });
	//             $("#retypePassword").popover({
	//                 content : popoverRetypePasswordWarning,
	//                 title : popoverHeaderWarning
	//             });
	//              $("#email").popover({
	//                 content : popoverEmailWarning,
	//                 title : popoverHeaderWarning
	//             });
}

function checkEmail() {
	var email = document.getElementById("email");

	if(email.value.length == 0) {
		hidePopover("email");
		return false;
	}

	if(email.value.search("@")==-1) {
		fillContentAndshowPopover("email", popoverEmailWarning,popoverHeaderWarning);
		return false;
	} else {
		hidePopover("email");
		return true;
	}
}

function checkPassword() {
	var password = document.getElementById("password");
	var text = password.value;

	if(text.length == 0) {
		hidePopover("password");
		return false;
	}

	if(text.search("[a-zA-Z]")!=-1 && text.search("[0-9]")!=-1 && text.length>=8) {
		hidePopover("password");
		return true;
	} else {
		fillContentAndshowPopover("password",popoverPasswordWarning, popoverHeaderWarning);
		return false;
	}
}
function comparePasswords() {
	var pass1 = document.getElementById("password").value;
	var pass2 = document.getElementById("retypePassword").value;

	if(pass2 != pass1) {
		fillContentAndshowPopover("retypePassword",popoverRetypePasswordWarning,popoverHeaderWarning);
		return false;
	} else {
		hidePopover("retypePassword");
		return true;
	}
}
/**
            Checks the username, first the format of the username, and then whether 
            it is available if the format is valid. 
            This function will delay the second check, that is,it will wait some time, so that 
            the user can input several symbols.
          */
function checkUsernameDelayed() {
	usernameAvailable = false;
	var username = document.getElementById("username").value;
	//starts with a number, no need to wait, cause it's certainly invalid
	if(username.length != 0) {
		if(/^[0-9].*$/.test(username)) {
			fillContentAndshowPopover("username", popoverUsernameWarningStartsWithNumber, popoverHeaderWarning);
			return false;
			//contains special characters
		} else if(!/^[a-z0-9]+$/i.test(username)) {
			fillContentAndshowPopover("username", popoverUsernameWarningContainsSpecialSymbols, popoverHeaderWarning);
			return false;

			//check if the username is valid with ajax
		} else {
			if(!usernameTimeoutStarted) {
				$.get("/api/isUsernameAvailable", {"username":username}, doUsernameAvailabilityResponse);
			}

			return true;
		}
	} else {
		hidePopover("username");
		return false;
	}
}

function doUsernameAvailabilityResponse(jsonResponse) {

	if(jsonResponse.available == true) {
		hidePopover("username");
		usernameAvailable = true;
		console.log(usernameAvailable);
	} else {
		usernameAvailable = false;
		console.log(usernameAvailable);
		fillContentAndshowPopover("username", popoverUsernameWarningNotAvailable, popoverHeaderWarning);
	}
	
};

function login() {
	var username = document.getElementById("usernameLogin").value;
	var password = document.getElementById("passwordLogin").value;

	//    console.log(username);
	//    console.log(password);

	/* NESREÆA submitForm($("#loginForm"), doLoginResponse); */
	//test
	//doLoginResponse();
}

function doLoginResponse(jsonResponse) {
	//test1 username unavailable
	//jsonResponse = {"username":"Username invalid"};
	//test username available
	//jsonResponse = {"username":"true"};
	//test2 username available
	jsonResponse = {};

	if(isValid(jsonResponse.username) && isValid(jsonResponse.password)) {
		//window.location.href = homepage;
	} else {
		showAlert("loginAlert");
	}
}

function register() {
	var valid = usernameAvailable && checkUsernameDelayed() && checkPassword() && checkEmail() && comparePasswords();
	console.log(usernameAvailable);
	console.log(valid);
	if(!valid) {
		console.log(1);
		return;
	}

	/* NESREÆA submitForm($("#registerForm"), doRegisterResponse); */
}

function doRegisterResponse(jsonResponse) {
	//should anything be checked here?
	//no because the frontend has already done all the checks
	//if the page was accessed illegaly without frontend, then 
	//this will make sure no registration occurs

	//window.location.href = homepage;
}