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

function displayErrorInformation(jqxhrResponseText) {
    alert(prepareErrorMessage(jqxhrResponseText));
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

$(document).ready(function() {
    if(localStorage.getItem('popState') != 'shown'){
        $('#cookie-modal').modal('show');
        localStorage.setItem('popState','shown')
    }
});
