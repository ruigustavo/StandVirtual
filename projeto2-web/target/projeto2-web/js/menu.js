function goEditCar() {
    document.forms["ownedCars"].submit();
}

function goDetailsCar(){
    document.forms["list-cars"].submit();
}

function followCar(){
    document.forms["follow"].submit();
}
function unfollowCar(){
    document.forms["unfollow"].submit();
}

function researchBrandModel(e) {
    var brand = document.forms["researchBrandAndModel"].elements['brand'];
    var model = document.forms["researchBrandAndModel"].elements['model'];
    var brand_selected = brand[brand.selectedIndex];
    var model_selected = model[model.selectedIndex];
    if(brand_selected!=undefined){
        var value_brand = brand_selected.value;
        if(value_brand!=""){
            if(model_selected!=undefined) {
                var value_model = model_selected.value;
                if (value_model != "") {
                    document.forms["researchBrandAndModel"].elements["action"].value = "list-brand-model";
                    document.forms["researchBrandAndModel"].submit();
                } else {
                    document.forms["researchBrandAndModel"].elements["action"].value = "list-brand";
                    document.forms["researchBrandAndModel"].submit();
                }
            }
        }else{
            e.preventDefault();
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


function researchKm(e) {
    var form = document.forms["researchKm"];
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

function research(param) {
    switch (param){
        case ('price-asc'):
            if(getQueryVariable("low_value")!= undefined && getQueryVariable("up_value")!=undefined){
                document.forms["researchPriceAsc"].elements['low_value'].value = getQueryVariable("low_value");
                document.forms["researchPriceAsc"].elements['up_value'].value = getQueryVariable("up_value");
                document.forms["researchPriceAsc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchPriceAsc"].elements['order'].value = 1;
                document.forms["researchPriceAsc"].submit();

            }else if(getQueryVariable("model")!=undefined){
                document.forms["researchPriceAsc"].elements['brand'].value = getQueryVariable("brand");
                document.forms["researchPriceAsc"].elements['model'].value = getQueryVariable("model");
                document.forms["researchPriceAsc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchPriceAsc"].elements['order'].value = 1;
                document.forms["researchPriceAsc"].submit()
            }else if(getQueryVariable("brand")!=undefined){
                document.forms["researchPriceAsc"].elements['brand'].value = getQueryVariable("brand");
                document.forms["researchPriceAsc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchPriceAsc"].elements['order'].value = 1;
                document.forms["researchPriceAsc"].submit()
            }else if(getQueryVariable("year")!=undefined){
                document.forms["researchPriceAsc"].elements['year'].value = getQueryVariable("year");
                document.forms["researchPriceAsc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchPriceAsc"].elements['order'].value = 1;
                document.forms["researchPriceAsc"].submit()
            }else{
                document.forms["researchPriceAsc"].elements['action'].value = "list-all";
                document.forms["researchPriceAsc"].elements['order'].value = 1;
                document.forms["researchPriceAsc"].submit()
            }
            break;
        case ('price-desc'):
            if(getQueryVariable("low_value")!= undefined && getQueryVariable("up_value")!=undefined){
                document.forms["researchPriceDesc"].elements['low_value'].value = getQueryVariable("low_value");
                document.forms["researchPriceDesc"].elements['up_value'].value = getQueryVariable("up_value");
                document.forms["researchPriceDesc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchPriceDesc"].elements['order'].value = 2;
                document.forms["researchPriceDesc"].submit();

            }else if(getQueryVariable("model")!=undefined){
                document.forms["researchPriceDesc"].elements['brand'].value = getQueryVariable("brand");
                document.forms["researchPriceDesc"].elements['model'].value = getQueryVariable("model");
                document.forms["researchPriceDesc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchPriceDesc"].elements['order'].value = 2;
                document.forms["researchPriceDesc"].submit()
            }else if(getQueryVariable("brand")!=undefined){
                document.forms["researchPriceDesc"].elements['brand'].value = getQueryVariable("brand");
                document.forms["researchPriceDesc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchPriceDesc"].elements['order'].value = 2;
                document.forms["researchPriceDesc"].submit()
            }else if(getQueryVariable("year")!=undefined){
                document.forms["researchPriceDesc"].elements['year'].value = getQueryVariable("year");
                document.forms["researchPriceDesc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchPriceDesc"].elements['order'].value = 2;
                document.forms["researchPriceDesc"].submit()
            }else{
                document.forms["researchPriceDesc"].elements['action'].value = "list-all";
                document.forms["researchPriceDesc"].elements['order'].value = 2;
                document.forms["researchPriceDesc"].submit()
            }
            break;
        case ('brand-asc'):
            if(getQueryVariable("low_value")!= undefined && getQueryVariable("up_value")!=undefined){
                document.forms["researchBrandAsc"].elements['low_value'].value = getQueryVariable("low_value");
                document.forms["researchBrandAsc"].elements['up_value'].value = getQueryVariable("up_value");
                document.forms["researchBrandAsc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandAsc"].elements['order'].value = 3;
                document.forms["researchBrandAsc"].submit();

            }else if(getQueryVariable("model")!=undefined){
                document.forms["researchBrandAsc"].elements['brand'].value = getQueryVariable("brand");
                document.forms["researchBrandAsc"].elements['model'].value = getQueryVariable("model");
                document.forms["researchBrandAsc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandAsc"].elements['order'].value = 3;
                document.forms["researchBrandAsc"].submit()
            }else if(getQueryVariable("brand")!=undefined){
                document.forms["researchBrandAsc"].elements['brand'].value = getQueryVariable("brand");
                document.forms["researchBrandAsc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandAsc"].elements['order'].value = 3;
                document.forms["researchBrandAsc"].submit()
            }else if(getQueryVariable("year")!=undefined){
                document.forms["researchBrandAsc"].elements['year'].value = getQueryVariable("year");
                document.forms["researchBrandAsc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandAsc"].elements['order'].value = 3;
                document.forms["researchBrandAsc"].submit()
            }else{
                document.forms["researchBrandAsc"].elements['action'].value = "list-all";
                document.forms["researchBrandAsc"].elements['order'].value = 3;
                document.forms["researchBrandAsc"].submit()
            }
            break;
        case ('brand-desc'):
            if(getQueryVariable("low_value")!= undefined && getQueryVariable("up_value")!=undefined){
                document.forms["researchBrandDesc"].elements['low_value'].value = getQueryVariable("low_value");
                document.forms["researchBrandDesc"].elements['up_value'].value = getQueryVariable("up_value");
                document.forms["researchBrandDesc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandDesc"].elements['order'].value = 4;
                document.forms["researchBrandDesc"].submit();

            }else if(getQueryVariable("model")!=undefined){
                document.forms["researchBrandDesc"].elements['brand'].value = getQueryVariable("brand");
                document.forms["researchBrandDesc"].elements['model'].value = getQueryVariable("model");
                document.forms["researchBrandDesc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandDesc"].elements['order'].value = 4;
                document.forms["researchBrandDesc"].submit()
            }else if(getQueryVariable("brand")!=undefined){
                document.forms["researchBrandDesc"].elements['brand'].value = getQueryVariable("brand");
                document.forms["researchBrandDesc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandDesc"].elements['order'].value = 4;
                document.forms["researchBrandDesc"].submit()
            }else if(getQueryVariable("year")!=undefined){
                document.forms["researchBrandDesc"].elements['year'].value = getQueryVariable("year");
                document.forms["researchBrandDesc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandDesc"].elements['order'].value = 4;
                document.forms["researchBrandDesc"].submit()
            }else{
                document.forms["researchBrandDesc"].elements['action'].value = "list-all";
                document.forms["researchBrandDesc"].elements['order'].value = 4;
                document.forms["researchBrandDesc"].submit()
            }
            break;
        case ('brand-model-asc'):
            if(getQueryVariable("low_value")!= undefined && getQueryVariable("up_value")!=undefined){
                document.forms["researchBrandModelAsc"].elements['low_value'].value = getQueryVariable("low_value");
                document.forms["researchBrandModelAsc"].elements['up_value'].value = getQueryVariable("up_value");
                document.forms["researchBrandModelAsc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandModelAsc"].elements['order'].value = 5;
                document.forms["researchBrandModelAsc"].submit();

            }else if(getQueryVariable("model")!=undefined){
                document.forms["researchBrandModelAsc"].elements['brand'].value = getQueryVariable("brand");
                document.forms["researchBrandModelAsc"].elements['model'].value = getQueryVariable("model");
                document.forms["researchBrandModelAsc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandModelAsc"].elements['order'].value = 5;
                document.forms["researchBrandModelAsc"].submit()
            }else if(getQueryVariable("brand")!=undefined){
                document.forms["researchBrandModelAsc"].elements['brand'].value = getQueryVariable("brand");
                document.forms["researchBrandModelAsc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandModelAsc"].elements['order'].value = 5;
                document.forms["researchBrandModelAsc"].submit()
            }else if(getQueryVariable("year")!=undefined){
                document.forms["researchBrandModelAsc"].elements['year'].value = getQueryVariable("year");
                document.forms["researchBrandModelAsc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandModelAsc"].elements['order'].value = 5;
                document.forms["researchBrandModelAsc"].submit()
            }else{
                document.forms["researchBrandModelAsc"].elements['action'].value = "list-all";
                document.forms["researchBrandModelAsc"].elements['order'].value = 5;
                document.forms["researchBrandModelAsc"].submit()
            }
            break;
        case ('brand-model-desc'):
            if(getQueryVariable("low_value")!= undefined && getQueryVariable("up_value")!=undefined){
                document.forms["researchBrandModelDesc"].elements['low_value'].value = getQueryVariable("low_value");
                document.forms["researchBrandModelDesc"].elements['up_value'].value = getQueryVariable("up_value");
                document.forms["researchBrandModelDesc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandModelDesc"].elements['order'].value = 6;
                document.forms["researchBrandModelDesc"].submit();

            }else if(getQueryVariable("model")!=undefined){
                document.forms["researchBrandModelDesc"].elements['brand'].value = getQueryVariable("brand");
                document.forms["researchBrandModelDesc"].elements['model'].value = getQueryVariable("model");
                document.forms["researchBrandModelDesc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandModelDesc"].elements['order'].value = 6;
                document.forms["researchBrandModelDesc"].submit()
            }else if(getQueryVariable("brand")!=undefined){
                document.forms["researchBrandModelDesc"].elements['brand'].value = getQueryVariable("brand");
                document.forms["researchBrandModelDesc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandModelDesc"].elements['order'].value = 6;
                document.forms["researchBrandModelDesc"].submit()
            }else if(getQueryVariable("year")!=undefined){
                document.forms["researchBrandModelDesc"].elements['year'].value = getQueryVariable("year");
                document.forms["researchBrandModelDesc"].elements['action'].value = getQueryVariable("action");
                document.forms["researchBrandModelDesc"].elements['order'].value = 6;
                document.forms["researchBrandModelDesc"].submit()
            }else{
                document.forms["researchBrandModelDesc"].elements['action'].value = "list-all";
                document.forms["researchBrandModelDesc"].elements['order'].value = 6;
                document.forms["researchBrandModelDesc"].submit()
            }
            break;
    }
}

function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable && pair[1]!="") {
            return pair[1];
        }
    }
    return undefined;
}