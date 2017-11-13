function goEditCar(i)
{
    document.ownedCars.action.value = "edit-car";
    document.ownedCars.id.value = i;
    document.ownedCars.submit();
}