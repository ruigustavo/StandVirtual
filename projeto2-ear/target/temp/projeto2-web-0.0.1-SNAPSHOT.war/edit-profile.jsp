<%--
  Created by IntelliJ IDEA.
  User: rogeriocsilva
  Date: 12-11-2017
  Time: 16:06
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
        <div class="w3-col" style="width:60%">
            <h3></h3>
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
    <form class="w3-form" method="post" action="Main">
        <div class="w3-input-group">
            <label>E-mail</label>
            <input class="w3-input" name="email" type="text" value="${user.getEmail()}"/>
        </div>
        <div class="w3-input-group">
            <label>Password</label>
            <input class="w3-input" name="password" type="password" />
        </div>
        <div class="w3-input-group">
            <label>Name</label>
            <input class="w3-input" name="name" type="text" value="${user.getName()}"/>
        </div>

        <div class="w3-input-group">
            <label>Address</label>
            <input class="w3-input" name="address" type="text" value="${user.getAddress()}"/>
        </div>

        <div class="w3-input-group">
            <label>Phone</label>
            <input class="w3-input" name="phone" type="text" value="${user.getPhone()}"/>
        </div>
        <input type="hidden" name="action" value="edit-profile"/>
        <button type="submit" class="w3-btn dark-primary-color">Save Changes</button>
    </form>
</div>

</body>
</html>

