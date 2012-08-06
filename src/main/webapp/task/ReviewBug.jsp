<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Review Bug</title>
</head>
<body>

<h2>Review Bug</h2>

<s:form action="">
    <s:textfield label="Project" name="project" value="%{bug.project}" readonly="true" />
    <s:textfield label="Version" name="version" value="%{bug.version}" readonly="true" />
    <s:textfield label="Description" name="description" value="%{bug.description}" readonly="true" />
    <s:textfield label="Priority" name="priority" />
    <s:submit value="Accept" name="accept" action="AcceptBug" />
    <s:submit value="Reject" name="reject" action="RejectBug" />
</s:form>

</body>
</html>