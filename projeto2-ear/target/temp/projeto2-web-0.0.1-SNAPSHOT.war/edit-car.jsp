<%--
  Created by IntelliJ IDEA.
  User: rogeriocsilva
  Date: 13-11-2017
  Time: 0:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${empty user}">
    <c:redirect url="/Main"/>
</c:if>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>StandVirtual v2</title>
    <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
</head>
<body>
<header class="w3-container default-primary-color">
    <div class="w3-row">
        <div class="w3-col m6" style="width:30%">
            <h3 class="text-primary-color"><a href="/projeto2-web/">Welcome, <c:out value="${user.getName()}"/></a></h3>
        </div>
        <div class="w3-col" style="width:10%; margin-top: 10px;">
            <form class="w3-form" method="get" action="Main">
                <input type="hidden" name="action" value="edit-profile"/>
                <input type="submit" class="w3-btn accent-color secondary-text-color" value="Edit Profile"/>
            </form>
        </div>
        <div class="w3-col" style="width:10%; margin-top: 10px;">
            <form class="w3-form" method="post" action="Main">
                <input type="hidden" name="action" value="logout"/>
                <input type="submit" class="w3-btn accent-color secondary-text-color" value="Logout"/>
            </form>
        </div>
    </div>
</header>
<div class="w3-container ">
    <form class="w3-form" method="post" action="Main" enctype="multipart/form-data">

        <div class="w3-input-group">
            <label>Brand</label>
            <input class="w3-input" name="brand" type="text" value = "${car.getBrand()}"/>
        </div>
        <div class="w3-input-group">
            <label>Model</label>
            <input class="w3-input" name="model" type="text" value = "${car.getModel()}" />
        </div>

        <div class="w3-input-group">
            <label>Price</label>
            <input class="w3-input" name="price" type="number" value = "${car.getPrice()}" />
        </div>

        <div class="w3-input-group">
            <label>Kilometers</label>
            <input class="w3-input" name="km" type="number" value = "${car.getKm()}" />
        </div>

        <div class="w3-input-group">
            <label>Month</label>
            <input class="w3-input" name="registration_month" type="text" value = "${car.getRegistration_month()}" />
        </div>

        <div class="w3-input-group">
            <label>Year</label>
            <input class="w3-input" name="registration_year" type="number" value = "${car.getRegistration_year()}" />
        </div>

        <img style="width: 30%" src="data:image/*;base64,${car.getPictureEncoded()}">

        <div class="w3-input-group">
            <label>Picture</label>
            <input class="w3-input" name="picture" type="file" accept="image/*" />
        </div>
        <input type="hidden" name="id" value="${car.getId()}">
        <input type="hidden" name="action" value="edit-car"/>
        <button type="submit" class="w3-btn dark-primary-color">Save</button>
    </form>
</div>

</body>
</html>

