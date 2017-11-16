<%--
  Created by IntelliJ IDEA.
  User: rogeriocsilva
  Date: 13-11-2017
  Time: 21:46
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
    <script src="https://use.fontawesome.com/1dc1739919.js"></script>
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
        <c:if test="${carslist!=null}">
            <table class="w3-table" style="width:50%">
                <tr>
                    <th></th>
                    <th>
                        <div class="w3-cell-row">
                            <p class="w3-cell">Brand</p>
                            <form class="w3-cell" name="researchBrandDesc" method="get" action="Main">
                                <input type="hidden" name="action"/>
                                <input type="hidden" name="order"/>
                                <input type="hidden" name="low_value">
                                <input type="hidden" name="up_value">
                                <input type="hidden" name="brand">
                                <input type="hidden" name="model">
                                <input type="hidden" name="km">
                                <input type="hidden" name="year">
                                <button type="submit" onclick="research('brand-desc')">
                                    <i class="fa fa-arrow-up" aria-hidden="true"></i>
                                </button>
                            </form>

                            <form class="w3-cell" name="researchBrandAsc" method="get" action="Main">
                                <input type="hidden" name="action"/>
                                <input type="hidden" name="order"/>
                                <input type="hidden" name="low_value">
                                <input type="hidden" name="up_value">
                                <input type="hidden" name="brand">
                                <input type="hidden" name="model">
                                <input type="hidden" name="km">
                                <input type="hidden" name="year">
                                <button type="submit" onclick="research('brand-asc')">
                                    <i class="fa fa-arrow-down" aria-hidden="true"></i>
                                </button>
                            </form>
                        </div>
                    </th>
                    <th><div class="w3-cell-row">
                        <p class="w3-cell">Model</p>
                        <form class="w3-cell" name="researchBrandModelDesc" method="get" action="Main">
                            <input type="hidden" name="action"/>
                            <input type="hidden" name="order"/>
                            <input type="hidden" name="low_value">
                            <input type="hidden" name="up_value">
                            <input type="hidden" name="brand">
                            <input type="hidden" name="model">
                            <input type="hidden" name="km">
                            <input type="hidden" name="year">
                            <button type="submit" onclick="research('brand-model-desc')">
                                <i class="fa fa-arrow-up" aria-hidden="true"></i>
                            </button>
                        </form>

                        <form class="w3-cell" name="researchBrandModelAsc" method="get" action="Main">
                            <input type="hidden" name="action"/>
                            <input type="hidden" name="order"/>
                            <input type="hidden" name="low_value">
                            <input type="hidden" name="up_value">
                            <input type="hidden" name="brand">
                            <input type="hidden" name="model">
                            <input type="hidden" name="km">
                            <input type="hidden" name="year">
                            <button type="submit" onclick="research('brand-model-asc')">
                                <i class="fa fa-arrow-down" aria-hidden="true"></i>
                            </button>
                        </form>
                    </div></th>
                    <th>
                        <div class="w3-cell-row">
                            <p class="w3-cell">Price</p>
                            <form class="w3-cell" name="researchPriceDesc" method="get" action="Main">
                                <input type="hidden" name="action"/>
                                <input type="hidden" name="order"/>
                                <input type="hidden" name="low_value">
                                <input type="hidden" name="up_value">
                                <input type="hidden" name="brand">
                                <input type="hidden" name="model">
                                <input type="hidden" name="km">
                                <input type="hidden" name="year">

                                <button type="submit" onclick="research('price-desc')">
                                    <i class="fa fa-arrow-up" aria-hidden="true"></i>
                                </button>
                            </form>

                            <form class="w3-cell" name="researchPriceAsc" method="get" action="Main">
                                <input type="hidden" name="action"/>
                                <input type="hidden" name="order"/>
                                <input type="hidden" name="low_value">
                                <input type="hidden" name="up_value">
                                <input type="hidden" name="brand">
                                <input type="hidden" name="model">
                                <input type="hidden" name="km">
                                <input type="hidden" name="year">
                                <button type="submit" onclick="research('price-asc')">
                                    <i class="fa fa-arrow-down" aria-hidden="true"></i>
                                </button>
                            </form>
                        </div>

                    </th>
                </tr>
                <c:forEach items= "${carslist}" var = "i">
                    <tr>
                        <c:choose>
                            <c:when test="${empty i.getPicture()}">
                                <td></td>
                            </c:when>
                            <c:otherwise>
                                <td><img style="width: 200px" src="data:image/*;base64,${i.getPictureEncoded()}"></td>
                            </c:otherwise>
                        </c:choose>
                        <td><c:out value = "${i.getBrand()}"/></td>
                        <td><c:out value = "${i.getModel()}"/></td>
                        <td><c:out value = "${i.getPrice()}"/></td>
                        <form name="list-cars" method="get" action="Main">
                            <input type="hidden" name="id" value="${i.getId()}"/>
                            <input type="hidden" name="action" value="detail-car"/>
                            <td><button type="submit" class="w3-btn accent-color secondary-text-color" onclick="goDetailsCar()">Details</button></td>
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
                                        <button type="submit" class="w3-btn accent-color secondary-text-color" onclick="unfollowCar()">Unfollow</button>
                                    </form>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <form name="follow" method="post" action="Main">
                                        <input type="hidden" name="action" value="follow-car"/>
                                        <input type="hidden" name="id" value="${i.getId()}"/>
                                        <button type="submit" class="w3-btn accent-color secondary-text-color" onclick="followCar()">Follow</button>
                                    </form>
                                </td>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${user.getId() == i.getOwner().getId()}">
                            <form name="ownedCars" method="get" action="Main">
                                <input type="hidden" name="action" value="edit-car"/>
                                <input type="hidden" name="id" value="${i.getId()}"/>
                                <td><button type="submit" class="w3-btn accent-color secondary-text-color" onclick="goEditCar()">Edit</button></td>
                            </form>
                            <form method="post" action="Main">
                                <input type="hidden" name="id" value="${i.getId()}"/>
                                <input type="hidden" name="action" value="delete-car"/>
                                <td><input type="submit" class="w3-btn accent-color secondary-text-color" value="Delete"/></td>
                            </form>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>

    <div class="w3-row">
        <h2>ReSearch</h2>
        <div>
            <c:if test="${brandslist!=null && modelslist!=null}">
                <h4>Brand and Model</h4>
                <form class="w3-form" name="researchBrandAndModel" method="get" action="Main">
                    <input type="hidden" name="action"/>
                    <select name="brand">
                        <option value="" disabled selected>Select your option</option>
                        <c:forEach items= "${brandslist}" var = "i">
                            <option value="${i}"><c:out value="${i}"/></option>
                        </c:forEach>
                    </select>
                    <select name="model">
                        <option value="" disabled selected>Select your option</option>
                        <c:forEach items= "${modelslist}" var = "i">
                            <option value="${i}"><c:out value="${i}"/></option>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="order" value="1"/>
                    <button type="submit" class="w3-btn accent-color secondary-text-color" onclick="researchBrandModel(event)">Search</button>
                </form>
            </c:if>
        </div>
        <div>
            <h4>Price</h4>
            <form class="w3-form" name="researchPrice" method="get" action="Main">
                <input type="hidden" name="action" value="list-price"/>
                <input type="number" class="" name="low_value">
                <input type="number" class="" name="up_value">
                <input type="hidden" name="order" value="1"/>
                <button type="submit" class="w3-btn accent-color secondary-text-color" onclick="researchPrice(event)">Search</button>
            </form>

        </div>
        <div>
            <h4>Kilometers</h4>
            <form class="w3-form" name="researchKm" method="get" action="Main">
                <input type="hidden" name="action" value="list-km"/>
                <input type="number" class="" name="low_value">
                <input type="number" class="" name="up_value">
                <input type="hidden" name="order" value="1"/>
                <button type="submit" class="w3-btn accent-color secondary-text-color" onclick="researchKm(event)">Search</button>
            </form>

        </div>

        <div>
            <h4>Newer than</h4>
            <form class="w3-form" name="researchYear" method="get" action="Main">
                <input type="hidden" name="action" value="list-year"/>
                <input type="number" class="" name="year">
                <input type="hidden" name="order" value="1"/>
                <button type="submit" class="w3-btn accent-color secondary-text-color" onclick="researchYear(event)">Search</button>
            </form>

        </div>
    </div>

</div>
</body>
</html>
