var objToDeleteId;

$(document).ready(function () {

 $('#create-modal').on('hide.bs.modal', function (e) {
        clearCreateModal();
    });

// $('#details-modal').on('shown.bs.modal', function() {
//    refreshCustomers();
//    refreshAnimals();
// });


 $('#create-modal').on('shown.bs.modal', function() {
    refreshCustomers();
    refreshAnimals();
 });

 $("#filter input, #filter select, [form='filter']").on("change", function () {
    findTransactions();
 });

    findTransactions();
});

function findTransactions() {
    $.ajax({
        url: "/admin/api/transactions?" + prepareUrl(),
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


function getTransaction(id) {
    $.ajax({
        url: "/admin/api/transaction/" + id,
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (transaction) {
        $('#animal-details').append('<option value='+ transaction.animal.id +'> '+ transaction.animal.name + ' ' + prepareText(transaction.animal.lineageName) + ' </option>');
        showDetailsModal(transaction);
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
    var name = $("#name-filter").val();
         if (name != "") {
             url += "&animal_name=" + name;
         }
    var lineageName = $("#lineage-name-filter").val();
         if (lineageName != "") {
             url += "&lineage_name=" + lineageName;
         }

    var saleStatus = $("#sale-status-filter").find(":selected").val();
          if (saleStatus != "") {
             url += "&sale_status=" + saleStatus;
          }

    var transactionStatus = $("#transaction-status-filter").find(":selected").val();
          if (transactionStatus != "") {
             url += "&transaction_status=" + transactionStatus;
          }

    url += preparePaginationUrl();
    return url;
}

function fillResults(response) {
    $("#records").empty();
    var transactions = response;
    transactions.forEach(function(transaction){
        fillRow(transaction);
    });
}

function fillRow(transaction) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + prepareText(transaction.customer.firstName) + "</td>" +
            "<td class='align-middle'>" + prepareText(transaction.customer.lastName) + "</td>" +
            "<td class='align-middle'>" + prepareText(transaction.animal.name) + "</td>" +
            "<td class='align-middle'>" + prepareText(transaction.animal.lineageName) + "</td>" +
            "<td class='align-middle'>" + prepareTransactionStatusCodeFromDict(transaction.status) + "</td>" +
            "<td class='align-middle'>" + prepareDetailsButton(transaction.id) + "</td>" +
        "</tr>"
    );
}

function prepareTransactionStatusCodeFromDict(transactionStatusCode) {
    for(i=0; i<transactionStatusDict.length; i++) {
        if(transactionStatusDict[i].code === transactionStatusCode) {
            return transactionStatusDict[i].value;
        }
    }
}


function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="showDetails(' + id + ')">' + lang.Details + '</button>';
 }

function showDetails(id) {
//    getTransaction(id);
    refreshCustomers(id);
}


function hideCancelButton(transaction) {
    if(transaction.status === 'C'){
        document.getElementById('cancel-button').style.visibility = 'hidden';
        } else {
        document.getElementById('cancel-button').style.visibility = 'visible';
        }
}


function showDetailsModal(transaction) {
    $("#id").val(transaction.id),
    $("#customer-details").val(transaction.customer.id),
    $("#animal-details").val(transaction.animal.id),
    $("#reservation-date-details").val(transaction.reservationDate),
    $("#reservation-price-details").val(transaction.reservationPrice),
    $("#final-date-details").val(transaction.finalDate),
    $("#final-price-details").val(transaction.price),
    $("#transaction-status-details").val(transaction.status),
    $("#note-details").val(transaction.note),
     hideCancelButton(transaction);
    $('#details-modal').modal('show');

}

function clearCreateModal() {
     $("#customer-create").val(''),
     $("#animal-create").val(''),
     $("#reservation-date-create").val(''),
     $("#reservation-price-create").val(''),
     $("#final-date-create").val(''),
     $("#final-price-create").val(''),
     $("#note-create").val('');
}

function sendCreateRequest() {
         $.ajax({
             url: "/admin/api/transaction",
             method: "post",
             contentType: "application/json",
             data:
             JSON.stringify({
                 customerId: $("#customer-create").find(":selected").val(),
                 animalId: $("#animal-create").find(":selected").val(),
                 status: $("#transaction-status-create").find(":selected").val(),
                 reservationDate: $("#reservation-date-create").val(),
                 reservationPrice: $("#reservation-price-create").val(),
                 price: $("#final-price-create").val(),
                 finalDate: $("#final-date-create").val(),
                 note: $("#note-create").val()
             })
         })
         .done(function () {
              $("#create-modal").modal('hide');
              $("#operation-successful-modal").modal('show');
              $(".modal-backdrop").remove();
              findTransactions();
         })
         .fail(function (jqxhr, textStatus, errorThrown) {
               $("#create-button").prop( "disabled", false );
               $("#error-text").text(prepareErrorMessage(jqxhr.responseText));
               $("#invalid-data-modal").modal('show');
         })
}

function sendUpdateRequest(source) {

    var transactionId = $("#id").val();

    $.ajax({
        url: "/admin/api/transaction/" + transactionId,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            id : transactionId,
            customerId: $("#customer-details").find(":selected").val(),
            animalId: $("#animal-details").find(":selected").val(),
            status: $("#transaction-status-details").find(":selected").val(),
            reservationDate: $("#reservation-date-details").val(),
            reservationPrice: $("#reservation-price-details").val(),
            price: $("#final-price-details").val(),
            finalDate: $("#final-date-details").val(),
            note: $("#note-details").val(),
            source: source
        })
    })
        .done(function () {
            $('#delete-object-modal').modal('hide');
            $("#operation-successful-modal").modal('show');
            $('#details-modal').modal('hide');
            findTransactions();
        })
        .fail(function (jqxhr, textStatus, errorThrown) {
            $("#save-changes-button").prop( "disabled", false );
            $("#error-text").text(prepareErrorMessage(jqxhr.responseText));
            $("#invalid-data-modal").modal('show');
        })
}

function refreshCustomers(transactionId){
       $('#customer-create').empty();
       $('#customer-details').empty();
       findCustomers(transactionId);
   }

   function refreshAnimals(transactionId){
          $('#animal-create').empty();
          $('#animal-details').empty();
          findAnimals(transactionId);
      }

function findCustomers(transactionId) {

         $.ajax({
             url: "/admin/api/customers",
             type: "get",
             dataType: "json",
             contentType: "application/json"
         })
         .done(function (response) {
            response.forEach(function(customer){
                $('#customer-create').append('<option value='+ customer.id +'> '+ customer.firstName + ' ' + customer.lastName + ' </option>');
                $('#customer-details').append('<option value='+ customer.id +'> '+ customer.firstName + ' ' + customer.lastName + ' </option>');
            });
             refreshAnimals(transactionId);
         })
         .fail(function(jqxhr, textStatus, errorThrown){
             displayErrorInformation(jqxhr.responseText);
         });
}

function findAnimals(transactionId) {

         $.ajax({
             url: "/admin/api/animals?" + prepareAnimalUrl(transactionId),
             type: "get",
             dataType: "json",
             contentType: "application/json"
         })
         .done(function (response) {
            response.forEach(function(animal){
                $('#animal-create').append('<option value='+ animal.id +'> '+ animal.name + ' ' + prepareText(animal.lineageName) + ' </option>');
                $('#animal-details').append('<option value='+ animal.id +'> '+ animal.name + ' ' + prepareText(animal.lineageName) + ' </option>');
            });
                getTransaction(transactionId);

         })
         .fail(function(jqxhr, textStatus, errorThrown){
             displayErrorInformation(jqxhr.responseText);
         });
}

function prepareAnimalUrl(transactionId) {
    var url = "";
    url+= "&sale_status=F";
    url+= "&cattery_status=FS";

    return url;
}