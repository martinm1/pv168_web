<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <body>
        <table border="1">
            <thead>
            <tr>
                <th>název</th>
                <th>autor</th>
            </tr>
            </thead>
            <c:forEach items="${agents}" var="agent">
                <tr>
                    <td><c:out value="${agent.name}"/></td>
                    <td><c:out value="${agent.workingSince}"/></td>
                    <td><c:out value="${agent.compromised}"/></td>
                    <td><form method="post" action="${pageContext.request.contextPath}/agents/delete?id=${agent.id}"
                              style="margin-bottom: 0;"><input type="submit" value="Smazat"></form></td>
                </tr>
            </c:forEach>
        </table>
        <h2>Zadejte agenta</h2>
        <c:if test="${not empty chyba}">
            <div style="border: solid 1px red; background-color: yellow; padding: 10px">
                <c:out value="${chyba}"/>
            </div>
        </c:if>
        <form action="${pageContext.request.contextPath}/agents/add" method="post">
            <table>
                <tr>
                    <th>jméno agenta:</th>
                    <td><input type="text" name="name" value="<c:out value='${param.name}'/>"/></td>
                </tr>
                <tr>
                    <th>odkdy agent pracuje (např. 1984-01-14 10:40):</th>
                    <td><input type="text" name="workingSince" value="<c:out value='${param.workingSince}'/>"/></td>
                </tr>
                <tr>
                    <th>je agent prozrazen:</th>
                    <td><input type="text" name="compromised" value="<c:out value='${param.compromised}'/>"/></td>
                </tr>
            </table>
            <input type="Submit" value="Zadat" />
        </form>
    </body>
</html>
