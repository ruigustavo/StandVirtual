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

function researchByBrand(i) {
    document.forms["orderByBrand"].elements["action"].value = "listByBrand";
    document.forms["orderByBrand"].elements["brand"].value = i;
    document.forms["orderByBrand"].submit();

}