<%--
  Created by IntelliJ IDEA.
  User: rogeriocsilva
  Date: 15-11-2017
  Time: 22:01
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
        <c:if test="${carslist.size() > 0}">
            <table class="w3-table" style="width:50%">
                <caption>My Cars</caption>
                <tr>
                    <th></th>
                    <th>Brand</th>
                    <th>Model</th>
                    <th>Price</th>
                </tr>
                <c:forEach items= "${carslist}" var = "i">
                    <tr>
                        <c:choose>
                            <c:when test="${empty i.getPicture()}">
                                <td><img style="width: 200px; height: 150px;" src="https://vignette.wikia.nocookie.net/pixar/images/3/3c/LightningMcQueenCars3Artwork.jpg/revision/latest/scale-to-width-down/282?cb=20170807222423"></td>
                            </c:when>
                            <c:otherwise>
                                <td><img style="width: 200px; height: 150px;" src="data:image/*;base64,${i.getPictureEncoded()}"></td>
                            </c:otherwise>
                        </c:choose>
                        <td><c:out value = "${i.getBrand()}"/></td>
                        <td><c:out value = "${i.getModel()}"/></td>
                        <td><c:out value = "${i.getPrice()}"/></td>
                        <form name="ownedCars" method="get" action="Main">
                            <input type="hidden" name="id" value="${i.getId()}"/>
                            <input type="hidden" name="action" value="edit-car"/>
                            <td><button class="w3-btn accent-color secondary-text-color" type="submit" onclick="goEditCar()">Edit</button></td>
                        </form>
                        <form name="list-cars" method="get" action="Main">
                            <input type="hidden" name="id" value="${i.getId()}"/>
                            <input type="hidden" name="action" value="detail-car"/>
                            <td><button class="w3-btn accent-color secondary-text-color" type="submit" onclick="goDetailsCar()">Details</button></td>
                        </form>

                        <form method="post" action="Main">
                            <input type="hidden" name="id" value="${i.getId()}"/>
                            <input type="hidden" name="action" value="delete-car"/>
                            <td><input type="submit" class="w3-btn accent-color secondary-text-color" value="Delete"/></td>
                        </form>

                        <c:set var="contains" value="false" />
                        <c:if test="${not empty i.getFollowers()}">
                            <c:forEach items="${i.getFollowers()}" var="item">
                                <c:if test="${item.getId() eq user.getId()}">
                                    <c:set var="contains" value="true" />
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <c:choose >
                            <c:when test="${contains}">
                                <td>
                                    <form name="unfollow" method="post" action="Main">
                                        <input type="hidden" name="action" value="unfollow-car"/>
                                        <input type="hidden" name="id" value="${i.getId()}"/>
                                        <button class="w3-btn accent-color secondary-text-color" type="submit" onclick="unfollowCar()">Unfollow</button>
                                    </form>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <form name="follow" method="post" action="Main">
                                        <input type="hidden" name="action" value="follow-car"/>
                                        <input type="hidden" name="id" value="${i.getId()}"/>
                                        <button class="w3-btn accent-color secondary-text-color" type="submit" onclick="followCar()">Follow</button>
                                    </form>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </table>

        </c:if>
    </div>

</div>
</body>
</html>

