<%--
  Created by IntelliJ IDEA.
  User: rogeriocsilva
  Date: 12-11-2017
  Time: 12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${!empty user}">
        <c:redirect url="menu.jsp"/>
    </c:if>
    <!DOCTYPE html>
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>StandVirtual v2</title>
        <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
    </head>
    <body>
        <header class="w3-container w3-center">
            <h1>StandVirtual v2</h1>
            <p class="w3-text-red"><c:out value="${log}"/></p>
        </header>
        <div class="w3-container">
            <div class="w3-row">
                <div class="w3-col w3-center" style="width: 50%">
                        <form method="get" action="Main">
                            <input type="hidden" name="action" value="register"/>
                            <button>Register User</button>
                        </form>
                    </div>
                    <div class="w3-col w3-center" style="width: 50%">
                        <form method="get" action="Main">
                            <input type="hidden" name="action" value="login"/>
                            <button>Login</button>
                        </form>
                    </div>
            </div>
            
        </div>
    </body>
    </html>