var objToDeleteId;

$(document).ready(function () {

 $('#create-modal').on('hide.bs.modal', function (e) {
        clearCreateModal();
    });

 $("#filter input, #filter select, [form='filter']").on("change", function () {
        findBirths();
    });

    findBirths();

 $("#upload-birth").submit(function(event){
         $.ajax({
             type: "post",
             enctype: 'multipart/form-data',
             url: "/admin/api/birth" + prepareCreateBirthUrl(),
             data: new FormData(this),
             contentType: false,
             cache: false,
             processData:false,
             success: function (response) {
                $("#create-modal").modal('hide');
                $("#operation-successful-modal").modal('show');
                $(".modal-backdrop").remove();
                findBirths();
             },
             error: function (jqxhr, textStatus, errorThrown) {
               $("#create-button").prop( "disabled", false );
               $("#error-text").text(prepareErrorMessage(jqxhr.responseText));
               $("#invalid-data-modal").modal('show');
             }
          });
          event.preventDefault();
    });

 $("#upload-birth-main-photo").submit(function(event){
         var birthId = $("#id").val();
         $.ajax({
             type: "put",
             enctype: 'multipart/form-data',
             url: "/admin/api/birth/" + birthId +"/photo",
             data: new FormData(this),
             contentType: false,
             cache: false,
             processData:false,
             success: function (response) {
                window.location.reload();
             },
             error: function (jqxhr, textStatus, errorThrown) {
                $("#error-text").text(prepareErrorMessage(jqxhr.responseText));
                $("#invalid-data-modal").modal('show');
             }
          });
          event.preventDefault();
    });

     $("#birth_images").submit(function(event){

    var data = new FormData();
    jQuery.each(jQuery('#birth_images')[0].files, function(i, file) {
        if(file!=null){
            data.push(file);
        }
       console.log(file.name);
    });
             var birthId = $("#id").val();
             $.ajax({
                 type: "put",
                 enctype: 'multipart/form-data',
                 url: "/admin/api/birth/" + birthId +"/photo",
                 data: data,
                 contentType: false,
                 cache: false,
                 processData:false,
                 success: function (response) {
                    window.location.reload();
                 },
                 error: function (jqxhr, textStatus, errorThrown) {
                    $("#error-text").text(prepareErrorMessage(jqxhr.responseText));
                    $("#invalid-data-modal").modal('show');
                 }
              });
              event.preventDefault();
        });




// $("#upload-birth-photo").submit(function(event){
//    var birthId = $("#id").val();
//    $.ajax({
//        type: "put",
//        enctype: 'multipart/form-data',
//        url: "/admin/api/animal/" + animalId +"/photo",
//        data: new FormData(this),
//        contentType: false,
//        cache: false,
//        processData:false,
//        success: function (response) {
//            window.location.reload();
//        },
//        error: function (jqxhr, textStatus, errorThrown) {
//            $("#error-text").text(prepareErrorMessage(jqxhr.responseText));
//            $("#invalid-data-modal").modal('show');
//        }
//    });
//    event.preventDefault();
// });
});

function prepareCreateBirthUrl() {
        var url = "?";
        url+= "&name=" + $("#name-create").val(),
        url+= "&birth_date=" + $("#birth-date-create").val(),
        url+= "&mother_id=" + $("#mother-create").val(),
        url+= "&father_id=" + $("#father-create").val(),
        url+= "&note=" + $("#note-create").val(),
        url+= "&website_description=" + $("#website-description-create").val(),
        url+= "&website_details_description=" + $("#website-details-description-create").val(),
        url+= "&website_visibility_status=" +  $("#website-visibility-status-create").find(":selected").val()
    return url;
}


function findBirths() {
    $.ajax({
        url: "/admin/api/births?" + prepareUrl(),
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

function fillBirthImages(birth) {
       birth.birthsImages.forEach(function(image){
            if(image != null){
                 $('#' + image);
            }
       })
}

 function prepareImageToShow(imageFileName){
    return '<img src="/admin/birth/file/'+ imageFileName +'/" class="img-thumbnail" width="300px" height="300px">';
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


function prepareUrl() {
    var url = "";

    var name = $("#name-filter").val();
         if (name != "") {
            url += "&birth_name=" + name;
         }
    var motherId = $("#mother-filter").find(":selected").val();
         if (motherId != "") {
             url += "&mother_id=" + motherId;
         }
    var fatherId = $("#father-filter").find(":selected").val();
         if (fatherId != "") {
             url += "&father_id=" + fatherId;
         }

    url += preparePaginationUrl();
    return url;
}

function fillResults(response) {
    $("#records").empty();
    var births = response;
    births.forEach(function(birth){
        fillRow(birth);
    });
}

function fillRow(birth) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + prepareText(birth.mother.name) + "</td>" +
            "<td class='align-middle'>" + prepareText(birth.father.name) + "</td>" +
            "<td class='align-middle'>" + prepareText(birth.name) + "</td>" +
            "<td class='align-middle'>" + birth.birthDate + "</td>" +
            "<td class='align-middle'>" + birth.amount + "</td>" +
            "<td class='align-middle'>" + prepareDetailsButton(birth.id) + "</td>" +
            "<td class='align-middle'>" + prepareDeleteButton(birth.id) + "</td>" +
        "</tr>"
    );
}


function prepareWebsiteVisibilityStatusFromDict(status) {
    for(i=0; i<websiteVisibilityStatusDict.length; i++) {
        if(websiteVisibilityStatusDict[i].code === status) {
            return websiteVisibilityStatusDict[i].value;
        }
    }
}

function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="showDetails(' + id + ')">' + lang.Details + '</button>';
 }

function showDetails(id) {
    getBirth(id);
}

function prepareDeleteButton(id) {
       $("#id-delete").val(id);
    return '<button type="button" class="btn btn-danger" id="delete-button" onclick="$(\'#delete-object-modal\').modal(\'show\')">' + lang.Delete +'</button>';
  }

function showDetailsModal(birth) {
    $("#id").val(birth.id),
    $("#name-details").val(birth.name),
    $("#amount-details").val(birth.amount),
    $("#birth-date-details").val(birth.birthDate);
    if (birth.mother != null){
        $("#mother-details").val(birth.mother.id);
    };
    if (birth.father != null){
        $("#father-details").val(birth.father.id);
    }
    $("#note-details").val(birth.note),
    $("#website-description").val(birth.websiteDescription),
    $("#website-details-description").val(birth.websiteDetailsDescription),
    $("#website-visibility-status-details").val(birth.websiteVisibilityStatus),
    $("#note-details").val(birth.note);
    if(birth.image != null){
        $("#main-image-details").append(
        prepareImageToShow(birth.image.imageFileName)
        );
    };
    $('#details-modal').modal('show');
}

     function clearCreateModal() {
         $("#name-create").val('');
         $("#birth-date-create").val('');
         $("#amount-create").val('');
         $("#note-create").val('');
         $("#website-description-create").val('');
         $("#website-details-description-create").val('');
     }


function sendUpdateRequest(source) {
     var birthId;
       if(source == "delete"){
          birthId = $("#id-delete").val();
       } else {
          birthId = $("#id").val();
       };

    $.ajax({
        url: "/admin/api/birth/" + birthId,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            id : birthId,
            name: $("#name-details").val(),
            birthDate: $("#birth-date-details").val(),
            motherId: $("#mother-details").val(),
            fatherId: $("#father-details").val(),
            amount: $("#amount-details").val(),
            note: $("#note-details").val(),
            websiteDescription: $("#website-description").val(),
            websiteDetailsDescription: $("#website-details-description").val(),
            websiteVisibilityStatus:  $("#website-visibility-status-details").find(":selected").val(),
            source: source
        })
    })
        .done(function () {
            $('#delete-object-modal').modal('hide');
            $("#operation-successful-modal").modal('show');
            $('#details-modal').modal('hide');
            findBirths();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            $("#save-changes-button").prop( "disabled", false );
            $("#error-text").text(prepareErrorMessage(jqxhr.responseText));
            $("#invalid-data-modal").modal('show');        })
}


