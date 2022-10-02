/**
    Tells whether the popover of the given element(which must be a tag with a popover registered on it) 
    is visible(showing);
*/
function isPopoverVisible(elementID) {
    try {
        var isVisible = $('#'+elementID).data('bs.popover').tip().hasClass('in');
        //this error is likely to occur because the popover doesn't exist
    }catch(err) {
        return false;
    }

    return isVisible;
}

/**
    This function is useful because if a you try to 
    show a popover and it is already visible, it will 
    first dissapear and then be shown(the effect will be visible)
*/
function showPopoverIfInvisible(elementID) {
    if(!isPopoverVisible(elementID)) {
        $('#'+elementID).popover("show");
    }
}

/**
    Use this method to create(initialize) a new popover with new content, 
    or to update an existing popover with new content.
    This method will create a visual delay effect which the user would expect.
*/
function fillContentAndshowPopover(elementID, popoverContentMessage, popoverTitle) {
    if(isPopoverInitialized(elementID)) {
        setTimeout(function(){
            var popover = $("#"+elementID).data('bs.popover');
            popover.options.content = popoverContentMessage;
            popover.tip().find('.popover-content').html(popoverContentMessage);
            popover.options.title = popoverTitle;
            popover.tip().find('.popover-title').html(popoverTitle);
            $('#'+elementID).popover("show");
        },200);

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
        },200);
    }
}

function hidePopover(elementID) {
    if(isPopoverVisible(elementID)) {
        $('#'+elementID).popover("hide");
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
function submitForm(form, callbackFunction){
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
    if(value ==undefined) {
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