

<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <body>
        <table border="1">
            <thead>
            <tr>
                <th>Danger</th>
                <th>Assignment</th>
                
            </tr>
            </thead>
            <c:forEach items="${missions}" var="mission">
                <tr>
                    <form action="${pageContext.request.contextPath}/missions/update?id=${mission.id}" method="post">
                        <td><input type="text" name="dangerUpdate" value="<c:out value='${mission.danger}'/>"/></td>
                        <td><input type="text" name="assignmentUpdate" value="<c:out value='${mission.assignment}'/>"/></td>
                        <td><input type="Submit" value="Update" /></td>
                    </form>
                    <td><form method="post" action="${pageContext.request.contextPath}/missions/delete?id=${mission.id}"
                              style="margin-bottom: 0;"><input type="submit" value="Smazat"></form></td>
                    
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
