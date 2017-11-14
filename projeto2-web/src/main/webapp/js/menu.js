function goEditCar(i)
{
    document.ownedCars.action.value = "edit-car";
    document.ownedCars.id.value = i;
    document.ownedCars.submit();
}

function goDetailsCar(i){
    document.ownedCars.action.value = "detail-car";
    document.ownedCars.id.value = i;
    document.ownedCars.submit();
}

function followCar(i){
    document.ownedCars.action.value = "follow-car";
    document.ownedCars.id.value = i;
    document.ownedCars.submit();
}



function researchBrandModel(e) {
    var brand = document.getElementsByName("brand");
    var model = document.getElementsByName("model");
    var brand_selected = brand[0][brand[0].selectedIndex].value;
    var model_selected = model[0][model[0].selectedIndex].value;
    if(brand_selected!=""){
        if(model_selected!=""){
            document.forms["researchBrandAndModel"].elements["action"].value = "list-brand-model";
            document.forms["researchBrandAndModel"].submit();
        }else{
            document.forms["researchBrandAndModel"].elements["action"].value = "list-brand";
            document.forms["researchBrandAndModel"].submit();
        }
    }else{
        e.preventDefault();
    }

}

function researchPrice(e) {
    var form = document.forms["researchPrice"];
    if(form.elements["low_value"].value!="" && form.elements["up_value"].value!=""){
        form.submit();
    }else{
        e.preventDefault();
    }

}


function researchYear(e) {
    var form = document.forms["researchYear"];
    if(form.elements["year"].value!=""){
        form.submit();
    }else{
        e.preventDefault();
    }

}