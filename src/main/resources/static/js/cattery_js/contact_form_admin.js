var objToDeleteId;
var contact;

$(document).ready(function () {
 $("#filter input, #filter select, [form='filter']").on("change", function () {
        findContacts();
    });

    findContacts();
});

function findContacts() {
    $.ajax({
        url: "/admin/api/contacts?" + prepareUrl(),
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

function getContact(id) {
    $.ajax({
        url: "/admin/api/contact/" + id,
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (contact) {
        showDetailsModal(contact);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function prepareUrl() {
    var url = "";

    var name = $("#name").val();
        if (name != "") {
            url += "&name=" + name;
        }

    var served;
        var served = $("#served").prop('checked');
         url += "&served=" + served;

    url += preparePaginationUrl();
    return url;
}

function fillResults(response) {
    $("#records").empty();
    var contacts = response;
    contacts.forEach(function(contact){
        fillRow(contact);
    });
}

function fillRow(contact) {
    $('#records').append(
        "<tr>" +
            "<td class='align-middle'>" + prepareTextToTable(contact.name) + "</td>" +
            "<td class='align-middle'>" + contact.email + "</td>" +
            "<td class='align-middle'>" + contact.phoneNumber + "</td>" +
            "<td class='align-middle'>" + prepareTextToTable(contact.messageTopic) + "</td>" +
            "<td class='align-middle'>" + contact.contactDateTime + "</td>" +
            "<td class='align-middle'>" + prepareServedFromDict(contact.served) + "</td>" +
            "<td class='align-middle'>" + prepareDetailsButton(contact.id) + "</td>" +
            "<td class='align-middle'>" + prepareDeleteButton(contact.id) + "</td>" +
        "</tr>"
    );
}

function prepareTextToTable(text){
    if(text.length > 20){
       return text = text.slice(0,17) + "...";
    } else
       return text;
}

function prepareServedFromDict(served) {
    for(i=0; i<yesNoDict.length; i++) {
        if(yesNoDict[i].code == String(served)) {
            return yesNoDict[i].value;
        }
    }
}


function prepareDetailsButton(id) {
    return '<button type="button" class="btn btn-primary" onclick="showDetails(' + id + ')">' + lang.Details + '</button>';
 }

function showDetailsModal(contact) {
    document.getElementById('id').value = contact.id;
    document.getElementById('topic').value = contact.messageTopic;
    document.getElementById('name-details').value = contact.name;
    document.getElementById('email-details').value = contact.email;
    document.getElementById('phone-number').value = contact.phoneNumber;
    document.getElementById('date').value = contact.contactDateTime;
    document.getElementById('topic').value = contact.messageTopic;
    document.getElementById('content').value = contact.content;
    document.getElementById('regulationAccepted').value = prepareServedFromDict(contact.regulationAccepted);
    document.getElementById('served-details').value = prepareServedFromDict(contact.served);

    hideUpdateButton(contact);

    $('#details-modal').modal('show');
}

function hideUpdateButton(contact) {
    if(contact.served === true){
        document.getElementById('update-button').style.visibility = 'hidden';
        } else {
        document.getElementById('update-button').style.visibility = 'visible';
        }
}

 function showDetails(id) {
    getContact(id);
}

function prepareDeleteButton(id) {
    return '<button type="button" class="btn btn-danger" onclick="setObjectToDeleteIdAndShowModal(' + id + ') ">' + lang.Delete +'</button>';
}

function setObjectToDeleteIdAndShowModal(id) {
    objToDeleteId = id;
    $('#delete-object-modal').modal('show');
}

function sendDeleteRequest(){
     $.ajax({
        url: "/admin/api/contact/" + objToDeleteId,
        type: "DELETE"
     })
     .done(function(response) {
        $('#delete-object-modal').modal('hide');
        $('#operation-successful-modal').modal('show');
            findContacts();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function setObjectToUpdate(id) {
      objToUpdate = id;
      $('#delete-object-modal').modal('show');
}

function updateContact() {
    var contactId =  document.getElementById('id').value;

    $.ajax({
        url: "/admin/api/contact/" + contactId,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            name: $("#name-details").val(),
            email: $("#email-details").val(),
            served : 'true'

        })
     })
     .done(function(response) {
        $('#details-modal').modal('hide');
        $('#operation-successful-modal').modal('show');
            findContacts();
        })
        .fail(function(jqxhr, textStatus, errorThrown){
            displayErrorInformation(jqxhr.responseText);
        });
}

function checkIfContactIsServed(){
    var contactId =  document.getElementById('id').value;
    var contact = getContact(contact);
    return contact.served;
}

