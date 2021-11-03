$(document).ready(function () {
    findAnimals("M", "C", "V");
});

function findAnimals(gender, catteryStatus, websiteVisibilityStatus) {
    $.ajax({
        url: "/admin/api/animals?" + prepareUrl(gender, catteryStatus, websiteVisibilityStatus),
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

function prepareUrl(gender, catteryStatus, websiteVisibilityStatus) {
    var url = "";
    url += "&gender=" + gender;
    url += "&cattery_status=" + catteryStatus;
    url += "&website_visibility_status=" + websiteVisibilityStatus;
    url += preparePaginationUrl();

    return url;
}

function fillResults(response, gender) {
    clearRecords(gender);
    var animals = response;
    var secondAnimal;
    var firstAnimal;
    for (var i = 0; i < animals.length; i+=2) {
        firstAnimal = animals[i];
        if((i+1) != animals.length){
            secondAnimal = animals[i+1];
        }
        addAnimalsToTemplate(firstAnimal, secondAnimal, gender);
        firstAnimal = null;
        secondAnimal = null;
    }
    if(gender == 'M'){
        findAnimals("F", "C", "V");
    }
}

function clearRecords(gender){
    if(gender == 'M'){
          $("#records_animals_man").empty();
    } else if( gender == 'F'){
          $("#records_animals_woman").empty();
    }
}

function addAnimalsToTemplate(firstAnimal, secondAnimal, gender){
    if(gender == 'M'){
        addAnimals(firstAnimal, secondAnimal, 'records_animals_man');
    } else if(gender == 'F'){
        addAnimals(firstAnimal, secondAnimal, 'records_animals_woman');
    }
}

function addAnimals(firstAnimal, secondAnimal, id) {
    $('#' + id + '').append(
        '<div class="rounded bg-light shadow-sm my-5 p-3 text-center">' +
            '<div class="form-row text-center">' +
                '<div class="col-md-2"></div>' +
                prepareAnimalToShow(firstAnimal) +
                prepareAnimalToShow(secondAnimal) +
            '</div>' +
        '</div>'
    );
}

function prepareAnimalToShow(animal){
    if(animal != null){
        return '<div class="col-md-4 col-sm-10 col-xs-12 p-4 ml-4 text-center text-secondary border justify-content-center">' +
                    '<span><strong>' + animal.lineageName + '</strong></span><br><br>' +
                    prepareImageToShow(animal.image.imageFileName) +
                    '<br><br><span>' + animal.websiteDescription + '</span><br><br>'+
                '</div>';
    }  else {
        return '<div></div>'
    }

}

function prepareImageToShow(imageFileName){
    return '<img src="/admin/animal/file/'+ imageFileName +'/" class="img-thumbnail">';
}
