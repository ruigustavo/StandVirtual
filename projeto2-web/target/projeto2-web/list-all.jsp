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
    <script src="js/menu.js"></script>
</head>
<body>
<header class="w3-container default-primary-color">
    <div class="w3-row">
        <div class="w3-col" style="width:30%">
            <h3 class="text-primary-color"><a href="/projeto2-web/">Welcome, <c:out value="${user.getName()}"/></a></h3>
        </div>
        <div class="w3-col" style="width:10%; margin-top: 10px;">
            <form class="w3-form" method="get" action="Main">
                <input type="hidden" name="action" value="search-car"/>
                <input type="search" class="w3-input" name="search-value"/>
            </form>
        </div>

        <div class="w3-col" style="width:10%; margin-top: 10px;">
            <form class="w3-form" method="get" action="Main">
                <input type="hidden" name="action" value="list-all"/>
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
            <form name="ownedCars" method="get" action="Main">
                <input type="hidden" name="id"/>
                <input type="hidden" name="action"/>
                <table class="w3-table" style="width:50%">
                    <tr>
                        <th></th>
                        <th>Brand</th>
                        <th>Price</th>
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
                            <td><c:out value = "${i.getPrice()}"/></td>
                            <td><button type="submit" onclick="goDetailsCar(${i.getId()})">Details</button></td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
        </c:if>
    </div>

    <div class="w3-row">
        <%--<div class="w3-col" style="width:10%; margin-top: 10px;">--%>
            <%--&lt;%&ndash;<form class="w3-form" method="get" action="Main">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<input type="hidden" name="action" value="search-car"/>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<input type="search" class="w3-input" name="search-value"/>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</form>&ndash;%&gt;--%>
        <%--</div>--%>
        <c:if test="${carslist!=null}">
            <h4>Brand</h4>
            <c:forEach items= "${carslist}" var = "i">
                <form class="w3-form" name="orderByBrand" method="get" action="Main">
                    <input type="hidden" name="action"/>
                    <input type="hidden" name="brand"/>
                    <button type="submit" onclick="researchByBrand('${i.getBrand()}')"><c:out value="${i.getBrand()}"/></button>
                </form>
            </c:forEach>

        </c:if>
    </div>

</div>
</body>
</html>
