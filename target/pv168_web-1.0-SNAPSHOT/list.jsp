<%-- 
    Document   : list
    Created on : 13.4.2017, 12:23:53
    Author     : Boris
--%>

<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <body>
        <table border="1">
            <thead>
            <tr>
                <th>danger</th>
                <th>assignment</th>
                
            </tr>
            </thead>
            <c:forEach items="${missions}" var="mission">
                <tr>
                    <td><c:out value="${mission.danger}"/></td>
                    <td><c:out value="${mission.assignment}"/></td>
                    <td><form method="post" action="${pageContext.request.contextPath}/missions/delete?id=${mission.id}"
                              style="margin-bottom: 0;"><input type="submit" value="Smazat"></form></td>
                </tr>
            </c:forEach>
        </table>
        
        <table border="1">
            <thead>
            <tr>
                <th>danger</th>
                <th>assignment</th>
                <th>danger</th>
                <th>assignment</th>
            </tr>
            </thead>
            <c:forEach items="${missions}" var="mission">
                <tr>
                    <td><c:out value="${mission.danger}"/></td>
                    <td><c:out value="${mission.assignment}"/></td>
                    <form action="${pageContext.request.contextPath}/missions/update" method="post">
                    <td><input type="text" name="danger" value="<c:out value='${param.danger}'/>"/></td>
                    <td><input type="text" name="assignment" value="<c:out value='${param.assignment}'/>"/></td>
                    <input type="text" name="id" value='${mission.id}'/>
                    <td><input type="Submit" value="Update" /></td>
                    </form>
                    
                    </tr>
            </c:forEach>
        </table>
        
        <h2>Zadajte misiu</h2>
        <c:if test="${not empty chyba}">
            <div style="border: solid 1px red; background-color: yellow; padding: 10px">
                <c:out value="${chyba}"/>
            </div>
        </c:if>
        <form action="${pageContext.request.contextPath}/missions/add" method="post">
            <table>
                <tr>
                    <th>Level Nebezpecenstva:</th>
                    <td><input type="text" name="danger" value="<c:out value='${param.danger}'/>"/></td>
                </tr>
                <tr>
                    <th>Uloha:</th>
                    <td><input type="text" name="assignment" value="<c:out value='${param.assignment}'/>"/></td>
                </tr>
                
            </table>
            <input type="Submit" value="Zadat" />
        </form>
    </body>
</html>
