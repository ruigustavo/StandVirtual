<%--
  Created by IntelliJ IDEA.
  User: rogeriocsilva
  Date: 12-11-2017
  Time: 12:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>StandVirtual v2</title>
    <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
</head>
<body>
<header class="w3-container default-primary-color">
    <h3 class="text-primary-color">Authentication</h3>
    <p class="text-primary-color"><c:out value="${error}"/></p>
</header>
<div class="w3-container">
    <form class="w3-form" method="post" action="Main">
        <div class="w3-input-group">
            <label>E-mail</label>
            <input class="w3-input" name="email" type="email" required/>
        </div>
        <div class="w3-input-group">
            <label>Password</label>
            <input class="w3-input" name="password" type="password" required/>
        </div>
        <input type="hidden" name="action" value="login"/>
        <button type="submit" class="w3-btn dark-primary-color">Login</button>
    </form>
</div>
</body>
