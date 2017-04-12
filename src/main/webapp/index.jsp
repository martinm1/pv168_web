<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
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
        <form action="${pageContext.request.contextPath}/AgentServlet/add" method="post">
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
                    <th>odkdy agent pracuje:</th>
                    <td><input type="text" name="compromised" value="<c:out value='${param.compromised}'/>"/></td>
                </tr>
            </table>
            <input type="Submit" value="Zadat" />
        </form>
        
    
        <table border="1">
            <thead>
            <tr>
                <th>název</th>
                <th>autor</th>
            </tr>
            </thead>
            <c:forEach items="${agents}" var="agent">
                <tr>
                    <td><c:out value="${mission.danger}"/></td>
                    <td><c:out value="${mission.assignment}"/></td>
                    <td><form method="post" action="${pageContext.request.contextPath}/agents/delete?id=${agent.id}"
                              style="margin-bottom: 0;"><input type="submit" value="Smazat"></form></td>
                </tr>
            </c:forEach>
        </table>
        <h2>Zadejte misi</h2>
        <form action="${pageContext.request.contextPath}/MissionServlet/add" method="post">
            <table>
                <tr>
                    <th>stupeň nebezpečí:</th>
                    <td><input type="text" name="danger" value="<c:out value='${param.name}'/>"/></td>
                </tr>
                <tr>
                    <th>co je cílem mise:</th>
                    <td><input type="text" name="assignment" value="<c:out value='${param.compromised}'/>"/></td>
                </tr>
            </table>
            <input type="Submit" value="Zadat" />
        </form>
    </body>
</html>
