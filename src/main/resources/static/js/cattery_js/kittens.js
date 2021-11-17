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
    var firstBirth;
    var secondBirth;
    for (var i = 0; i < births.length; i+=2) {
        firstBirth = births[i];
        if((i+1) != births.length){
            secondBirth = births[i+1];
        }
        showBirths(firstBirth, secondBirth);
        firstBirth = null;
        secondBirth = null;
        }
}


function showBirths(firstBirth, secondBirth) {
    $('#records_births').append(
        '<div class="rounded bg-light shadow-sm my-5 p-3 text-center">' +
            '<div class="form-row text-center">' +
                '<div class="col-md-2"></div>'+
                prepareBirthToShow(firstBirth) +
                prepareBirthToShow(secondBirth) +
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
                    prepareDetailsButton(birth.id) +
                '</div>';
    }  else {
        return '<div></div>'
    }
}

function prepareImageToShow(imageFileName){
    return '<img src="/admin/birth/file/'+ imageFileName +'/" class="img-thumbnail" style="max-height:300px">';
}


function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-secondary"  onclick="showDetails(' + id + ')">' + lang.Details + '</button>';
 }

 function showDetails(id) {
     getBirth(id);
 }

 function getBirth(id) {
     $.ajax({
         url: "/admin/api/birth/" + id,
         type: "get",
         dataType: "json",
         contentType: "application/json"
     })
     .done(function (birth) {
         showDetailsModal(birth);
     })
     .fail(function(jqxhr, textStatus, errorThrown){
         displayErrorInformation(jqxhr.responseText);
     });
 }

 function showDetailsModal(birth) {
     $("#birth-name").text('Miot ' + birth.name),
     $("#website-details-description").text(birth.websiteDetailsDescription);
     if(birth.birthsImages != null){
         showBirthImages(birth);
     };
     $('#details-modal').modal('show');
 }

 function showBirthImages(birth) {
     var firstImage = null;
     var secondImage = null;
     var thirdImage = null;
     var images = birth.birthsImages;
     $('#images-births').empty();
     for (var i = 0; i < images.length; i+=3) {
        firstImage = images[i];
        if((i+1) != images.length){
            secondImage = images[i+1];
        }
        if((i+2) != images.length){
           thirdImage = images[i+2];
        }
        $('#images-births').append(
        showDetailsImages(firstImage, secondImage, thirdImage)
        );
        firstImage = null;
        secondImage = null;
        thirdImage = null;
     }
}

 function showDetailsImages(firstImage, secondImage, thirdImage){
    if(secondImage == null){
        return('<div class="form-row">' +
                    '<div class="p-3 col-md-4">' +
                            prepareImageToShow(firstImage.imageFileName) +
                    '</div>' +
                '</div>');
    } else if(thirdImage == null){
        return('<div class="form-row">' +
                '<div class="p-3 col-md-4">' +
                        prepareImageToShow(firstImage.imageFileName) +
                    '</div>' +
                    '<div class="p-3 col-md-4">' +
                        prepareImageToShow(secondImage.imageFileName) +
                    '</div>' +
                '</div>');
    }  else {
        return('<div class="form-row">' +
                '<div class="p-3 col-md-4">' +
                    prepareImageToShow(firstImage.imageFileName) +
                '</div>' +
                '<div class="p-3 col-md-4">' +
                    prepareImageToShow(secondImage.imageFileName) +
                '</div>' +
                '<div class="p-3 col-md-4">' +
                    prepareImageToShow(thirdImage.imageFileName) +
                '</div>' +
            '</div>');
    }
 }
