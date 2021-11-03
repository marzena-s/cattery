var objToDeleteId;

$(document).ready(function () {

 $('#create-modal').on('hide.bs.modal', function (e) {
        clearCreateModal();
    });

 $("#filter input, #filter select, [form='filter']").on("change", function () {
        findCustomers();
    });

    findCustomers();
});

function findCustomers() {
    $.ajax({
        url: "/admin/api/customers?" + prepareUrl(),
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


function getCustomer(id) {
    $.ajax({
        url: "/admin/api/customer/" + id,
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (customer) {
        showDetailsModal(customer);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}


function prepareUrl() {
    var url = "";

    var firstName = $("#first-name-filter").val();
         if (firstName != "") {
            url += "&first_name=" + firstName;
         }
    var lastName = $("#last-name-filter").val();
         if (lastName != "") {
             url += "&last_name=" + lastName;
         }
    var city = $("#city-filter").val();
         if (city != "") {
             url += "&city=" + city;
         }

    url += "&page_size=" + 10;
    url += preparePaginationUrl();
    return url;
}

function fillResults(response) {
    $("#records").empty();
    var customers = response;
    customers.forEach(function(customer){
        fillRow(customer);
    });
}

function fillRow(customer) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + prepareText(customer.firstName) + "</td>" +
            "<td class='align-middle'>" + prepareText(customer.lastName) + "</td>" +
            "<td class='align-middle'>" + prepareText(customer.city) + "</td>" +
            "<td class='align-middle'>" + prepareText(customer.phoneNumber) + "</td>" +
            "<td class='align-middle'>" + prepareText(customer.email) + "</td>" +
            "<td class='align-middle'>" + prepareDetailsButton(customer.id) + "</td>" +
            "<td class='align-middle'>" + prepareDeleteButton(customer.id) + "</td>" +
        "</tr>"
    );
}


function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="showDetails(' + id + ')">' + lang.Details + '</button>';
 }

function showDetails(id) {
    getCustomer(id);
}

function prepareDeleteButton(id) {
      $("#id-delete").val(id);
    return '<button type="button" class="btn btn-danger" id="delete-button" onclick="$(\'#delete-object-modal\').modal(\'show\')">' + lang.Delete +'</button>';
  }

function showDetailsModal(customer) {
    $("#id").val(customer.id),
    $("#first-name-details").val(customer.firstName),
    $("#last-name-details").val(customer.lastName),
    $("#country-details").val(customer.country),
    $("#street-details").val(customer.street),
    $("#building-no-details").val(customer.buildingNo),
    $("#flat-no-details").val(customer.flatNo),
    $("#postal-code-details").val(customer.postalCode),
    $("#city-details").val(customer.city),
    $("#phone-number-details").val(customer.phoneNumber),
    $("#note-details").val(customer.note),
    $("#email-details").val(customer.email),
    $("#personal-id-number-details").val(customer.personalIdNumber),
    $('#details-modal').modal('show');
}

     function clearCreateModal() {
         $("#first-name-create").val(''),
         $("#last-name-create").val(''),
         $("#street-create").val(''),
         $("#building-no-create").val(''),
         $("#flat-no-create").val(''),
//         image: $("#create-file").val(),
         $("#note-create").val(''),
         $("#city-create").val(''),
         $("#postal-code-create").val(''),
         $("#phone-number-create").val(''),
         $("#email-create").val(''),
         $("#personal-id-number-create").val('')
     }

function sendCreateRequest() {
         $.ajax({
             url: "/admin/api/customer",
             method: "post",
             contentType: "application/json",
             data:
             JSON.stringify({
                 firstName: $("#first-name-create").val(),
                 lastName: $("#last-name-create").val(),
                 country: $("#country-create").find(":selected").val(),
                 street: $("#street-create").val(),
                 buildingNo: $("#building-no-create").val(),
                 flatNo: $("#flat-no-create").val(),
//                 image: $("#create-file").val(),
                 note: $("#note-create").val(),
                 city: $("#city-create").val(),
                 postalCode: $("#postal-code-create").val(),
                 phoneNumber: $("#phone-number-create").val(),
                 email: $("#email-create").val(),
                 personalIdNumber: $("#personal-id-number-create").val()
             })
         })
         .done(function () {
              $("#create-modal").modal('hide');
              $("#operation-successful-modal").modal('show');
              $(".modal-backdrop").remove();
              findCustomers();
         })
         .fail(function (jqxhr, textStatus, errorThrown) {
               $("#create-button").prop( "disabled", false );
               $("#error-text").text(prepareErrorMessage(jqxhr.responseText));
               $("#invalid-data-modal").modal('show');
         })
}

function sendUpdateRequest(source) {
   var customerId;
   if(source == "delete"){
        customerId = $("#id-delete").val();
   } else {
        customerId = $("#id").val();
   }

    $.ajax({
        url: "/admin/api/customer/" + customerId,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            id : customerId,
            firstName: $("#first-name-details").val(),
            lastName: $("#last-name-details").val(),
            country: $("#country-details").find(":selected").val(),
            street: $("#street-details").val(),
            buildingNo: $("#building-no-details").val(),
            flatNo: $("#flat-no-details").val(),
            note: $("#note-details").val(),
            city: $("#city-details").val(),
            postalCode: $("#postal-code-details").val(),
            phoneNumber: $("#phone-number-details").val(),
            email: $("#email-details").val(),
            personalIdNumber: $("#personal-id-number-details").val(),
            source: source
        })
    })
        .done(function () {
            $('#delete-object-modal').modal('hide');
            $("#operation-successful-modal").modal('show');
            $('#details-modal').modal('hide');
            findCustomers();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            $("#save-changes-button").prop( "disabled", false );
            $("#error-text").text(prepareErrorMessage(jqxhr.responseText));
            $("#invalid-data-modal").modal('show');
        })
}


