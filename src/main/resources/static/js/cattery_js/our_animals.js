$(document).ready(function () {
    findAnimals("M");
    //findAnimals("F");

});

function findAnimals(gender) {
    $.ajax({
        url: "/admin/api/animals?" + prepareUrl(gender),
        type: "get",
        dataType: "json",
        contentType: "application/json"
    })
    .done(function (response) {
        fillResults(response, gender);
    })
    .fail(function(jqxhr, textStatus, errorThrown){
        displayErrorInformation(jqxhr.responseText);
    });
}

function prepareUrl(gender) {
    var url = "";
    url += "&gender=" + gender;
    url += "&cattery_status=C";
    url += "&website_visibility_status=V";
    url += preparePaginationUrl();

    return url;
}

function fillResults(response, gender) {
    $("#records_animals_man").empty();
//    $("#records_animals_woman").empty();
    var animals = response;
    if(gender == 'M'){
        var counter = 0;
        animals.forEach(function(animal){
            counter++;
            if(counter%2 != 0){
                addOddAnimalMen(animal);
            } else if(counter%2 == 0){
                addEvenAnimalMen(animal);
            }
        });
    }

}

function addOddAnimalMen(animal) {
    $('#records_animals_man').append(
        '<div class="rounded bg-light shadow-sm my-5 p-3 text-center">' +
            '<div class="form-row text-center">' +
                '<div class="col-md-2"></div>' +
                prepareAnimalToShow(animal) +
            '</div>' +
        '</div>'
    );
}

function addEvenAnimalMen(animal) {
    $('#records_animals_man').append(
//        '<div class="rounded bg-light shadow-sm my-5 p-3 text-center">' +
//            '<div class="form-row text-center">' +
//                '<div class="col-md-2"></div>' +
                prepareAnimalToShow(animal)
//            '</div>' +
//        '</div>'
    );
}

function prepareAnimalToShow(animal){
    return '<div class="col-md-4 col-sm-10 col-xs-12 p-4 ml-4 text-center text-secondary border justify-content-center">' +
                                 '<span><strong>' + animal.lineageName + '</strong></span><br><br>' +
                                  prepareImageToShow(animal.image.imageFileName) +
                                 '<br><br><span>' + animal.websiteDescription + '</span><br><br>'+
                     '</div>';
}

 function prepareImageToShow(imageFileName){
    return '<img src="/admin/animal/file/'+ imageFileName +'/" class="img-thumbnail">';
 }
