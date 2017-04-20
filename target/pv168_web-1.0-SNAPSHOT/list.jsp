<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <body>
        <table border="1">
            <thead>
            <tr>
                <th>name</th>
                <th>workingSince</th>
                <th>compromised</th>
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
        
        <table border="1">
            <thead>
            <tr>
                <th>name</th>
                <th>workingSince</th>
                <th>compromised</th>
                <th>name</th>
                <th>workingSince</th>
                <th>compromised</th>
            </tr>
            </thead>
            <c:forEach items="${agents}" var="agent">
                <tr>
                    <td><c:out value="${agent.name}"/></td>
                    <td><c:out value="${agent.workingSince}"/></td>
                    <td><c:out value="${agent.compromised}"/></td>
                    <form action="${pageContext.request.contextPath}/agents/update" method="post">
                    <td><input type="text" name="name" value="<c:out value='${param.name}'/>"/></td>
                    <td><input type="text" name="workingSince" value="<c:out value='${param.workingSince}'/>"/></td>
                    <td><input type="text" name="compromised" value="<c:out value='${param.compromised}'/>"/></td>
                    <input type="hidden" name="id" value='${agent.id}'/>
                    <td><input type="Submit" value="Update" /></td>
                    </form>
                    
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
                    <th>odkdy agent pracuje:</th>
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
