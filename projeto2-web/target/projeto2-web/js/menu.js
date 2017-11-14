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

function researchByBrand() {
    var select = document.getElementsByName("brand");
    var selected = select[0][select[0].selectedIndex].value;
    if(selected!="" || selected!=undefined){
        document.forms["reOrder"].elements["action"].value = "listByBrand";
        // document.forms["reOrder"].elements["brand"].value = selected;
        console.log(document.forms["reOrder"].elements["action"].value );
        console.log(selected);
    }
    document.forms["reOrder"].submit();

}