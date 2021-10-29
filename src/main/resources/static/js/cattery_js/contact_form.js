function sendCreateRequest() {
         $.ajax({
             url: "/api/contact_form",
             method: "post",
             contentType: "application/json",
             data:
             JSON.stringify({
                 name: $("#name").val(),
                 email: $("#e_mail").val(),
                 phoneNumber: $("#phone_number").val(),
                 messageTopic: $("#message_topic").val(),
                 content: $("#content").val(),
                 regulationAccepted: $("#regulation").prop('checked')regulationAccepted: $("#regulation").prop('checked')
             })
         })
         .done(function () {
              $("#send-successful-modal").modal('show');
              clearContactForm();
         })
         .fail(function (jqxhr, textStatus, errorThrown) {
                $("#error-text").text(prepareErrorMessage(jqxhr.responseText));
               $("#invalid-data-modal").modal('show');
         })
     };


function clearContactForm() {
         $("#name").val(''),
         $("#e_mail").val(''),
         $("#phone_number").val(''),
         $("#message_topic").val(''),
         $("#content").val(''),
         $("#content").val(''),
         $("#regulation").prop('checked', false)
}
