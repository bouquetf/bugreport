<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Bugs review tasks</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>Creation Date</th>
        <th>Name</th>
        <th>Execution Id</th>
        <th>Assigned to</th>
    </tr>
    </thead>

    <tbody>
    <s:iterator value="bugs">
        <tr>
            <td><s:property value="CreationDate"/></td>
            <td><s:property value="Name"/></td>
            <td><s:property value="ExecutionId"/></td>
            <td><s:property value="Assignedto"/></td>
            <td>
                <s:url var="urlId" action="ReviewBug"><s:param name="bugId" value="%{ExecutionId}"/> </s:url>
                <s:a href="%{urlId}">Review Bug</s:a>
            </td>
        </tr>
    </s:iterator>
    </tbody>
</table>
</body>
</html>