$(document).ready(function () {
    findBirths('V');
});

function findBirths(websiteVisibilityStatus) {
    $.ajax({
        url: "/admin/api/births?" + prepareUrl(websiteVisibilityStatus),
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        fillResults(response);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function prepareUrl(websiteVisibilityStatus) {
    var url = "";
    url += "&website_visibility_status=" + websiteVisibilityStatus;
    url += preparePaginationUrl();

    return url;
}

function fillResults(response, gender) {
    $("#records_births").empty();
    var births = response;
    births.forEach(function(birth){
        showBirth(birth);
    });

}


function showBirth(birth) {
    $('#records_births').append(
        '<div class="rounded bg-light shadow-sm my-5 p-3 text-center">' +
            '<div class="form-row text-center">' +
                '<div class="col-md-2"></div>' +
                prepareBirthToShow(birth) +
            '</div>' +
        '</div>'
    );
}

function prepareBirthToShow(birth){
    if(birth != null){
        return '<div class="col-md-4 col-sm-10 col-xs-12 p-4 ml-4 text-center text-secondary border justify-content-center">' +
                    '<span><strong>' + birth.name + '</strong></span><br><br>' +
                    prepareImageToShow(birth.image.imageFileName) +
                    '<br><br><span>' + birth.websiteDescription + '</span><br><br>'+
                    '<div>' + prepareDetailsButton(birth.id) + '</div>'
                '</div>';
    }  else {
        return '<div></div>'
    }

}

function prepareImageToShow(imageFileName){
    return '<img src="/admin/birth/file/'+ imageFileName +'/" class="img-thumbnail">';
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-secondary" onclick="#">' + lang.Details + '</button>';
 }
