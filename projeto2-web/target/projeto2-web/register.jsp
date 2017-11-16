<%--
  Created by IntelliJ IDEA.
  User: rogeriocsilva
  Date: 12-11-2017
  Time: 13:09
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
        <div class="w3-input-group">
            <label>Name</label>
            <input class="w3-input" name="name" type="text" required/>
        </div>

        <div class="w3-input-group">
            <label>Address</label>
            <input class="w3-input" name="address" type="text" required/>
        </div>

        <div class="w3-input-group">
            <label>Phone</label>
            <input class="w3-input" name="phone" type="text" required/>
        </div>
        <input type="hidden" name="action" value="register"/>
        <button type="submit" class="w3-btn dark-primary-color">Register</button>
    </form>
</div>

</body>
</html>
