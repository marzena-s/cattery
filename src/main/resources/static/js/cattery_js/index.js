
/* PAGINATION */
$(document).ready(function(){
    let $page = $("#page");

    if ( $page.val() == 1) {
        $("#prev-page").addClass("disabled");
    }

    $("#next-page").click(function(event){
        event.preventDefault();
        $page.val(parseInt($page.val()) + 1);
        $page.triggerHandler("change");
    })

    $("#prev-page").click(function(event){
        event.preventDefault();
        if ($page.val() > 1) {
            $page.val($page.val() - 1);
            $page.triggerHandler("change");
        }
    })
})

function preparePaginationUrl() {

    let url = "";

    const pageSize = $("#page_size").children(":selected").val();
    if (pageSize != "" && pageSize > 0) {
        url += "&page_size=" + pageSize;
    }

    const page = $("#page").val();
    if (page != "" && !isNaN(page) && page > 0) {
        url += "&page=" + page;
    }

    return url;
}

/* ERROR and SUCCESS */
function displayErrorInformation(jqxhrResponseText) {
    alert(prepareErrorMessage(jqxhrResponseText));
    //$("#operation-failed-modal").modal("show");
    //$("#operation_error_message").text(response.message);
}

function prepareErrorMessage(jqxhrResponseText) {
    var response = jQuery.parseJSON(jqxhrResponseText);
    return response.message;
}

function changeLanguage(lang) {
    window.location.href = window.location.pathname + "?lang=" + lang;
}

function htmlEncode (html) {
    html = html.split(' ').join('&#32;');
    html = html.split('/').join('&#47;');
    html = html.split('<').join('&#60;');
    html = html.split('>').join('&#62;');
    html = html.split('≤').join('&le;');
    html = html.split('≥').join('&ge;');
    html = html.split("'").join('&#39;');
    html = html.split('"').join('&quot;');
    return html;
}

function changeLanguage(lang) {
    window.location.href = window.location.pathname + "?lang=" + lang;
}

function prepareText(text) {
    if(text === null) {
        return "";
    } else {
        return htmlEncode(text);
    }
}

function prepareDateTime(dateTime) {
    return dateTime.replace('T', ' ');
}


//
///* PASSWORD */
//function hash(password) {
//  var hashObj = new jsSHA("SHA-256", "TEXT", {numRounds: 1});
//  hashObj.update(password);
//  return hashObj.getHash("HEX");
//}

/* PASSWORD VALIDATION */
//
//function arePasswordsValid(password, repeatedPassword) {
//    if(password.length < 8) {
//        showError("Password must have at least 8 characters");
//        return false;
//    } else if(password !== repeatedPassword) {
//        showError("Passwords are not the same");
//        return false;
//    } else if(!hasLowerCase(password)) {
//        showError("Password must have at least one lowercase letter");
//        return false;
//    } else if(!hasUpperCase(password)){
//        showError("Password must have at least one uppercase letter");
//        return false;
//    } else if(!hasNumber(password)) {
//        showError("Password must have at least one number");
//        return false;
//    } else if(!hasSpecialCharacter(password)) {
//        showError("Password must have at least one special character");
//        return false;
//    } else if(hasSpace(password)) {
//        showError("Password can't have spaces");
//        return false;
//    }else {
//        return true;
//    }
//}
//function hasLowerCase(str) {
//    return (/[a-z]/.test(str));
//}
//
//function hasUpperCase(str) {
//    return (/[A-Z]/.test(str));
//}
//
//function hasNumber(str) {
//    return (/[0-9]/.test(str));
//}
//
//function hasSpecialCharacter(str) {
//    var regex = /^[A-Za-z0-9 ]+$/
//    return !regex.test(str);
//}
//
//function hasSpace(str) {
//    return (/\s/.test(str));
//}
//
///* IMAGES */
//
//function prepareImage(imageName) {
//    if(imageName === "") {
//        return "";
//    } else {
//        return '<img src="/get-image/' + imageName + '/" class="img-fluid img-thumbnail">';
//    }
//}


//$(document).ready(function() {
//    if(localStorage.getItem('popState') != 'shown'){
//        $('#cookie-modal').modal('show');
//        localStorage.setItem('popState','shown')
//    }
//});
