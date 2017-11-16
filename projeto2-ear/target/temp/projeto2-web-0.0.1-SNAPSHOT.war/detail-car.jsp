<%--
  Created by IntelliJ IDEA.
  User: rogeriocsilva
  Date: 13-11-2017
  Time: 21:05
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
    <script src="js/menu.js"></script>
</head>
<body>
<header class="w3-container default-primary-color">
    <div class="w3-row">
        <div class="w3-col" style="width:30%">
            <h3 class="text-primary-color"><a href="/projeto2-web/">Welcome, <c:out value="${user.getName()}"/></a></h3>
        </div>

        <div class="w3-col" style="width:10%; margin-top: 10px;">
            <form class="w3-form"  method="get" action="Main">
                <input type="hidden" name="action" value="new-car"/>
                <input type="submit" class="w3-btn accent-color secondary-text-color" value="New Car"/>
            </form>
        </div>
        <div class="w3-col" style="width:10%; margin-top: 10px;">
            <form class="w3-form"  method="get" action="Main">
                <input type="hidden" name="action" value="my-cars"/>
                <input type="submit" class="w3-btn accent-color secondary-text-color" value="My Cars"/>
            </form>
        </div>

        <div class="w3-col" style="width:10%; margin-top: 10px;">
            <form class="w3-form" method="get" action="Main">
                <input type="hidden" name="action" value="list-all"/>
                <input type="hidden" name="order" value="1"/>
                <input type="submit" class="w3-btn accent-color secondary-text-color" value="All Cars"/>
            </form>
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


<div class="w3-container">
    <div class="w3-row">
        <div>
            <c:choose>
                <c:when test="${empty car.getPicture()}">
                    <td></td>
                </c:when>
                <c:otherwise>
                    <td><img style="width: 200px; height: 200px;" src="data:image/*;base64,${car.getPictureEncoded()}"></td>
                </c:otherwise>
            </c:choose>
        </div>
        <div>
            <label><strong>Brand</strong></label>
            <p><c:out value = "${car.getBrand()}"/></p>
        </div>
        <div>
            <label><strong>Model</strong></label>
            <p><c:out value = "${car.getModel()}"/></p>
        </div>
        <div>
            <label><strong>Price</strong></label>
            <p><c:out value = "${car.getPrice()}"/></p>
        </div>
        <div>
            <label><strong>Kilometers</strong></label>
            <p><c:out value = "${car.getKm()}"/></p>
        </div>
        <div>
            <label><strong>Registration Year</strong></label>
            <p><c:out value = "${car.getRegistration_year()}"/></p>
        </div>
        <div>
            <label><strong>Registration Month</strong></label>
            <p><c:out value = "${car.getRegistration_month()}"/></p>
        </div>
        <div>

        <c:if test="${user.getId() == car.getOwner().getId()}">
            <div class="w3-col" style="width:10%;">
                <form name="ownedCars" method="get" action="Main">
                    <input type="hidden" name="action" value="edit-car"/>
                    <input type="hidden" name="id" value="${car.getId()}"/>
                    <button type="submit" class="w3-btn accent-color secondary-text-color" onclick="goEditCar()">Edit</button>
                </form>
            </div>
            <div class="w3-col" style="width:10%;">
                <form method="post" action="Main">
                    <input type="hidden" name="id" value="${car.getId()}"/>
                    <input type="hidden" name="action" value="delete-car"/>
                    <input type="submit" class="w3-btn accent-color secondary-text-color" value="Delete"/>
                </form>
            </div>
        </c:if>
        <c:set var="contains" value="false" />
        <c:if test="${not empty car.getFollowers()}">
            <c:forEach items="${car.getFollowers()}" var="item">
                <c:if test="${item.getId() eq user.getId()}">
                    <c:set var="contains" value="true" />
                </c:if>
            </c:forEach>
        </c:if>
        <div class="w3-col" style="width:10%;">
            <c:choose >
                <c:when test="${contains}">
                        <form name="unfollow" method="post" action="Main">
                            <input type="hidden" name="action" value="unfollow-car"/>
                            <input type="hidden" name="id" value="${car.getId()}"/>
                            <button type="submit" class="w3-btn accent-color secondary-text-color" onclick="unfollowCar()">Unfollow</button>
                        </form>
                </c:when>
                <c:otherwise>
                    <form name="follow" method="post" action="Main">
                        <input type="hidden" name="action" value="follow-car"/>
                        <input type="hidden" name="id" value="${car.getId()}"/>
                        <button type="submit" class="w3-btn accent-color secondary-text-color" onclick="followCar()">Follow</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
        </div>
    </div>
</div>
</body>
</html>
