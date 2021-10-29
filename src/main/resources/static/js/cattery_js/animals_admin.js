var objToDeleteId;

$(document).ready(function () {

    $('#create-modal').on('hide.bs.modal', function (e) {
        clearCreateModal();
    });

    $("#filter input, #filter select, [form='filter']").on("change", function () {
        findAnimals();
    });

    $('#species-create').on('change', function() {
        refreshBreeds();
    });

    $('#mother-create').on('change', function() {
        refreshBirths();
    });

    $('#father-create').on('change', function() {
    refreshBirths();
    });

    $('#mother').on('change', function() {
        refreshBirths();
    });

    $('#father').on('change', function() {
        refreshBirths();
    });

    $('#create-modal').css('overflow-y', 'auto');

    $('#create-modal').on('shown.bs.modal', function() {
        $("#births-create").prop("disabled", false);
        $("#mother-create").prop("disabled", false);
        $("#father-create").prop("disabled", false);
        $("#birth_date").prop("disabled", false);
        $("#birth-date-create").prop("disabled", true);

        refreshBreeds();
        getDictionaryMother();
    });

    $("#father-outside").change(function() {
        if(this.checked) {
            $("#father-create").prop("disabled", true);
            $("#mother-create").prop("disabled", true);
            $("#mother-outside").prop("checked", true);
        } else{
            $("#father-create").prop("disabled", false);
            $("#mother-create").prop("disabled", false);
            $("#mother-outside").prop("checked", false);
        };

        if(this.checked || $("#mother-outside").prop("checked")){
            $("#births-create").prop("disabled", true);
            $("#birth-date-create").prop("disabled", false);
        } else {
            $("#births-create").prop("disabled", false);
            $("#birth-date-create").prop("disabled", true);
            }
    });


    $("#mother-outside").change(function() {
            if(this.checked) {
                $("#mother-create").prop("disabled", true);
                $("#father-create").prop("disabled", true);
                $("#father-outside").prop("checked", true);
            } else{
                $("#mother-create").prop("disabled", false);
                $("#father-create").prop("disabled", false);
                $("#father-outside").prop("checked", false);
            };

            if(this.checked || $("#father-outside").prop("checked")){
                 $("#births-create").prop("disabled", true);
                 $("#birth-date-create").prop("disabled", false);
            } else {
                 $("#births-create").prop("disabled", false);
                 $("#birth-date-create").prop("disabled", true);
            };
        });

    $('#details-modal').on('shown.bs.modal', function() {
        refreshBreeds();
        getDictionaryMother();
        if($("#cattery-status").val() == 'S'){
            $("#cattery-status").prop("disabled", true);
        }
    });

    $("#father-outside-details").change(function() {
            if(this.checked) {
                $("#father").prop("disabled", true);
                $("#mother").prop("disabled", true);
                $("#mother-outside-details").prop("checked", true)
            } else{
                $("#father").prop("disabled", false);
                $("#mother").prop("disabled", false);
                $("#mother-outside-details").prop("checked", false)
            };

            if(this.checked || $("#mother-outside-details").prop("checked")){
                $("#births").prop("disabled", true);
                $("#birth-date").prop("disabled", false);
            } else {
                $("#births").prop("disabled", false);
                $("#birth-date").prop("disabled", true);
            }
        });

    $("#mother-outside-details").change(function() {
        if(this.checked) {
            $("#mother").prop("disabled", true);
            $("#father").prop("disabled", true);
            $("#father-outside-details").prop("checked", true)
        } else{
            $("#mother").prop("disabled", false);
            $("#father").prop("disabled", false);
            $("#father-outside-details").prop("checked", false)
        };

        if(this.checked || $("#father-outside-details").prop("checked")){
             $("#births").prop("disabled", true);
             $("#birth-date").prop("disabled", false);
        } else {
             $("#births").prop("disabled", false);
             $("#birth-date").prop("disabled", true);
        };
    });

    findAnimals();

    $("#upload-animal").submit(function(event){
         $.ajax({
             type: "post",
             enctype: 'multipart/form-data',
             url: "/admin/api/animal" + prepareCreateAnimalUrl(),
             data: new FormData(this),
             contentType: false,
             cache: false,
             processData:false,
             success: function (response) {
                $("#create-modal").modal('hide');
                $("#operation-successful-modal").modal('show');
                $(".modal-backdrop").remove();
                getDictionaryFather();
                getDictionaryMother();
                findAnimals();
             },
             error: function (jqxhr, textStatus, errorThrown) {
                $("#create-button").prop( "disabled", false );
                $("#error-text").text(prepareErrorMessage(jqxhr.responseText));
                $("#invalid-data-modal").modal('show');
             }
          });
          event.preventDefault();
    });

    $("#upload-animal-photo").submit(function(event){
         var animalId = $("#id").val();
         $.ajax({
             type: "put",
             enctype: 'multipart/form-data',
             url: "/admin/api/animal/" + animalId +"/photo",
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

});

function prepareCreateAnimalUrl() {
    var url = "?";
    url+= "&breed_id=" +  $("#breed-create").find(":selected").val();
    url+= "&birth_id=" +  $("#births-create").find(":selected").val();
    url+= "&name=" +  $("#name-create").val();
    url+= "&lineage_name=" + $("#lineage-name-create").val();;
    url+= "&gender_code=" + $("#gender-create").find(":selected").val();
    url+= "&birth_date=" + $("#birth-date-create").val();
    url+= "&color=" + $("#color-create").val();
    url+= "&chip_number=" + $("#chip-number-create").val();
    url+= "&lineage_number=" + $("#lineage-number-create").val();
    url+= "&weight=" + $("#weight-create").val();
    url+= "&cattery_status_code=" + $("#cattery-status-create").find(":selected").val();
    var motherId = Number(null);
    if($("#mother-outside").prop('checked')){
        url+= "&mother_id=" + motherId;
    } else{
        motherId = $("#mother-create").val();
        url+= "&mother_id=" + motherId;
    }
    var fatherId=Number(null);
    if($("#father-outside").prop('checked')){
            url+= "&father_id=" + fatherId;
        } else {
            url+= "&father_id=" + $("#father-create").val();
        }
    url+= "&price=" + $("#price-create").val()
    url+= "&sale_status_code=" + $("#sale-status-create").find(":selected").val();
    url+= "&note=" + $("#note-create").val();
    url+= "&website_description=" + $("#website-description-create").val();
    url+= "&website_visibility_status=" + $("#website-visibility-status-create").find(":selected").val();
    return url;
}

$(function() {
  function setPriceInputCreateDisabled() {
    $("#price-create").prop("disabled", (($("#cattery-status-create").val() != "S") && ($("#cattery-status-create").val() != "FS")));
    }
  $("#cattery-status-create").change(setPriceInputCreateDisabled);
});

$(function() {
  function setPriceInputDetailsDisabled() {
    $("#price").prop("disabled", (($("#cattery-status").val() != "S") && ($("#cattery-status").val() != "FS")));
    }
  $("#cattery-status").change(setPriceInputDetailsDisabled);
});


$(function() {
    function setSaleStatusCreate() {
        if(($("#cattery-status-create").val() != "S") && ($("#cattery-status-create").val() != "FS")){
            $('#sale-status-create').val('N');
        } else if(($("#cattery-status-create").val() == "S")){
             $('#sale-status-create').val('S');
             $('#cattery-status-create').prop("disabled", true);
        } else {
            $('#sale-status-create').val('F');
        }
   }
     $("#cattery-status-create").change(setSaleStatusCreate);
});

$(function() {
    function setSaleStatusDetails() {
        if(($("#cattery-status").val() != "S") && ($("#cattery-status").val() != "FS")){
            $('#sale-status').val('N');
        } else if(($("#cattery-status").val() == "S")){
            $('#sale-status').val('S');
        } else {
            $('#sale-status').val('F');
        }
   }
     $("#cattery-status").change(setSaleStatusDetails);
});

function findAnimals() {
    $.ajax({
        url: "/admin/api/animals?" + prepareUrl(),
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

function getAnimal(id) {
    $.ajax({
        url: "/admin/api/animal/" + id,
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (animal) {
        $('#image-details').empty();
        showDetailsModal(animal);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function prepareUrl() {
    var url = "";

    var name = $("#name-filter").val();
         if (name != "") {
            url += "&name=" + name;
         }
    var lineageName = $("#lineage-name-filter").val();
         if (lineageName != "") {
             url += "&lineage_name=" + lineageName;
         }
    var gender = $("#gender-filter").find(":selected").val();
         if (gender != "") {
             url += "&gender=" + gender;
         }
    var catteryStatus = $("#cattery-status-filter").find(":selected").val();
         if (catteryStatus != "") {
             url += "&cattery_status=" + catteryStatus;
         }
    var saleStatus = $("#sale-status-filter").find(":selected").val();
         if (saleStatus != "") {
             url += "&sale_status=" + saleStatus;
         }
    var websiteVisibilityStatus = $("#website-visibility-filter").find(":selected").val();
         if (websiteVisibilityStatus != "") {
             url += "&website_visibility_status=" + websiteVisibilityStatus;
         }

    url += "&page_size=" + 10;

    url += preparePaginationUrl();

    return url;
}

function fillResults(response) {
    $("#records").empty();
    var animals = response;
    animals.forEach(function(animal){
        fillRow(animal);
    });
}

function fillRow(animal) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + prepareText(animal.name) + "</td>" +
            "<td class='align-middle'>" + prepareText(animal.lineageName) + "</td>" +
            "<td class='align-middle'>" + animal.birthDate + "</td>" +
            "<td class='align-middle'>" + prepareCatteryStatusCodeFromDict(animal.catteryStatusCode) + "</td>" +
            "<td class='align-middle'>" + prepareSaleStatusCodeFromDict(animal.saleStatusCode) + "</td>" +
            "<td class='align-middle'>" + prepareDetailsButton(animal.id) + "</td>" +
            "<td class='align-middle'>" + prepareDeleteButton(animal.id) + "</td>" +
        "</tr>"
    );
}

function prepareGenderFromDict(gender) {
    for(i=0; i<genderDict.length; i++) {
        if(genderDict[i].code === gender) {
            return genderDict[i].value;
        }
    }
}

function prepareCatteryStatusCodeFromDict(catteryStatusCode) {
    for(i=0; i<catteryStatusCodeDict.length; i++) {
        if(catteryStatusCodeDict[i].code === catteryStatusCode) {
            return catteryStatusCodeDict[i].value;
        }
    }
}

function prepareSaleStatusCodeFromDict(saleStatusCode) {
    for(i=0; i<saleStatusDict.length; i++) {
        if(saleStatusDict[i].code === saleStatusCode) {
            return saleStatusDict[i].value;
        }
    }
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
    getAnimal(id);
}

function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" id="delete-button" onclick="setObjectToDeleteIdAndShowModal('+ id +')">' + lang.Delete +'</button>';
 }

 function prepareImageToShow(imageFileName){
    return '<img src="/admin/animal/file/'+ imageFileName +'/" class="img-thumbnail" width="300px" height="300px">';
 }

function setObjectToDeleteIdAndShowModal(id){
    $("#id-delete").val(id);
    $('#delete-object-modal').modal('show');
}


function showDetailsModal(animal) {
    $("#id").val(animal.id);
    setBirthDetails(animal);
    $("#breed").val(animal.breed.id),
    $("#name-details").val(animal.name),
    $("#lineage-name-details").val(animal.lineageName),
    $("#gender").val(animal.genderCode),
    $("#birth-date").val(animal.birthDate),
    $("#color").val(animal.color),
    $("#chip-number").val(animal.chipNumber),
    $("#lineage-number").val(animal.lineageNumber),
    $("#weight").val(animal.weight),
    $("#cattery-status").val(animal.catteryStatusCode);
    setMotherDetails(animal);
    setFatherDetails(animal);
    $("#price").val(animal.price);
    setSaleStatusAndPriceDetails(animal);
    if(animal.image != null){
    $('#image-details').append(
        prepareImageToShow(animal.image.imageFileName)
        );
    }
    $("#note").val(animal.note);
    $("#website-description").val(animal.websiteDescription);
    $("#website-visibility-status").val(animal.websiteVisibilityStatus);
    $('#details-modal').modal('show');
};

function setBirthDetails(animal){
    if(animal.birth != null){
        $("#births").val(animal.birth.id);
        $("#birth-date").prop("disabled", true);
                $("#births").prop("disabled", false);

    } else {
        $("#births").prop("disabled", true);
                $("#birth-date").prop("disabled", false);

    };
};

function setFatherDetails(animal){
    if(animal.father != null){
        $("#father").val(animal.father.id);
        $("#father").prop("disabled", false);
        $("#father-outside-details").prop("checked", false);
    } else {
        $("#father-outside-details").prop("checked", true);
        $("#father").prop("disabled", true);
    };
};

function setMotherDetails(animal){
    if(animal.mother != null){
        $("#mother").val(animal.mother.id);
        $("#mother").prop("disabled", false);
        $("#mother-outside-details").prop("checked", false);
    } else {
        $("#mother-outside-details").prop("checked", true);
        $("#mother").prop("disabled", true);
    };
};

function setSaleStatusAndPriceDetails(animal){
    if(($("#cattery-status").val() != "S") && ($("#cattery-status").val() != "FS")){
        $("#sale-status").val(animal.saleStatusCode);
        $("#sale-status").prop("disabled", true);
        $("#price").prop("disabled", true);
    } else {
        $("#sale-status").val(animal.saleStatusCode);
//        $("#sale-status").prop("disabled", false);
        $("#price").prop("disabled", false);
    };
};

function clearCreateModal() {
         $("#image-create").val('');
         $("#name-create").val('');
         $("#lineage-name-create").val('');
         $("#birth-date-create").val('');
         $("#color-create").val('');
         $("#chip-number-create").val('');
         $("#weight-create").val('');
         $("#lineage-number-create").val('');
         $("#price-create").val('');
         $("#note-create").val('');
         $("#website-description-create").val('');
         $("#mother-outside").prop("checked", false);
         $("#father-outside").prop("checked", false);
}

function refreshBreeds(){
    $('#breed-create').empty();
    findBreeds();
}

function findBreeds() {

         $.ajax({
             url: "/admin/api/breeds?" + prepareBreedsUrl(),
             type: "get",
             dataType: "json",
             contentType: "application/json"
         })
         .done(function (response) {
            response.forEach(function(breed){
                $('#breed-create').append('<option value='+ breed.id +'> '+ breed.name +' </option>');
            });
         })
         .fail(function(jqxhr, textStatus, errorThrown){
             displayErrorInformation(jqxhr.responseText);
         });
}

function prepareBreedsUrl(){
    var url = "";

       var species = $("#species-create").find(":selected").val();
          if (species != "") {
             url += "&species_id=" + species;
          }
    return url;
}

function sendUpdateRequest(source) {

    var animalId;
    if(source == "delete"){
          animalId = $("#id-delete").val();
    } else {
          animalId = $("#id").val();
    };
    var motherId;
        if($("#mother-outside-details").prop('checked')){
            motherId = Number(null);
        } else{
            motherId = $("#mother").val();
        };
        var fatherId;
        if($("#father-outside-details").prop('checked')){
            fatherId = Number(null);
        } else {
            fatherId = $("#father").val();
        };

    $.ajax({
        url: "/admin/api/animal/" + animalId,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            id : animalId,
            breedId : $("#breed").find(":selected").val(),
            name: $("#name-details").val(),
            lineageName: $("#lineage-name-details").val(),
            genderCode: $("#gender").find(":selected").val(),
            birthDate: $("#birth-date").val(),
            color: $("#color").val(),
            chipNumber: $("#chip-number").val(),
            lineageNumber: $("#lineage-number").val(),
            weight: $("#weight").val(),
            catteryStatusCode: $("#cattery-status").find(":selected").val(),
            motherId: motherId,
            fatherId: fatherId,
            price: $("#price").val(),
            saleStatusCode: $("#sale-status").find(":selected").val(),
            birthId: $("#births").find(":selected").val(),
            note: $("#note").val(),
            websiteDescription: $("#website-description").val(),
            websiteVisibilityStatus:  $("#website-visibility-status").find(":selected").val(),
            source: source
        })
    })
        .done(function () {
            $('#delete-object-modal').modal('hide');
            $("#operation-successful-modal").modal('show');
            $('#details-modal').modal('hide');
            findAnimals();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            $("#error-text").text(prepareErrorMessage(jqxhr.responseText));
            $("#invalid-data-modal").modal('show');

            $("#save-changes-button").prop( "disabled", false );
            $("#delete-object-modal").modal('hide');
        })
}

function getDictionaryMother() {
    $.ajax({
            url: "/admin/api/dictionary/" + "ANIMAL_MOTHER",
            type: "GET",
            dataType: "json"
        })
    .done(function(response) {
           $('#mother-create').empty();
           $('#mother').empty();
           response.forEach(function(mother){
                $('#mother-create').append('<option value='+ mother.id +'> '+ mother.value +' </option>');
                $('#mother').append('<option value='+ mother.id +'> '+ mother.value +' </option>');
           });
            getDictionaryFather();

    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation("Cannot get dict: " + name + " due to: " + jqxhr.responseText);
    });
}

function getDictionaryFather() {
    $.ajax({
            url: "/admin/api/dictionary/" + "ANIMAL_FATHER",
            type: "GET",
            dataType: "json"
        })
    .done(function(response) {
           $('#father-create').empty();
           $('#father').empty();
           response.forEach(function(father){
                $('#father-create').append('<option value='+ father.id +'> '+ father.value +' </option>');
                $('#father').append('<option value='+ father.id +'> '+ father.value +' </option>');
           });
                    refreshBirths();
    })
    .fail(function(jqxhr, textStatus, errorThrown) {
        displayErrorInformation("Cannot get dict: " + name + " due to: " + jqxhr.responseText);
    });
}

function refreshBirths(){
    $('#births-create').empty();
    $('#births').empty();
    findBirths();
}

function findBirths() {

          $.ajax({
              url: "/admin/api/births?" + prepareBirthsUrl(),
              type: "get",
              dataType: "json",
              contentType: "application/json"
          })
          .done(function (response) {
             response.forEach(function(birth){
                 $('#births-create').append('<option value='+ birth.id +'> '+ birth.name + "     (" + birth.birthDate.toString().replaceAll(',' , "-") + ")" + ' </option>');
                 $('#births').append('<option value='+ birth.id +'> '+ birth.name +"   (" + birth.birthDate.toString().replaceAll(',' , "-") + ")" + ' </option>');
             });
          })
          .fail(function(jqxhr, textStatus, errorThrown){
              displayErrorInformation(jqxhr.responseText);
          });
 }

function prepareBirthsUrl(){
    var url = "";

       var mother;
       if(($("#details-modal").data('bs.modal') || {})._isShown)   {
            mother = $("#mother").find(":selected").val();
        } else {
            mother = $("#mother-create").find(":selected").val();
        };
        if (mother != "") {
             url += "&mother_id=" + mother;
        }

        var father;
        if(($("#details-modal").data('bs.modal') || {})._isShown){
            father = $("#father").find(":selected").val();
        } else (
            father = $("#father-create").find(":selected").val());
        if (father != "") {
            url += "&father_id=" + father;
        };

        var birthsName = null;
         if(($("#details-modal").data('bs.modal') || {})._isShown){
            birthsName = $("#births").find(":selected").val();
         } else {
            birthsName = $("#births-create").find(":selected").val();
         };
         if (birthsName != "" && birthsName != null) {
            url += "&birth_name=" + birthsName;
         }
    return url;
}
