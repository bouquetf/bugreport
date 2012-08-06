<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Submit Bug</title>
</head>
<body>

<h2>Submit Bug</h2>

<s:form action="SaveBug">
    <s:textfield name="project" label="Project" />
    <s:textfield name="version" label="Version" />
    <s:textfield name="summary" label="Summary" />
    <s:submit name="Submit" value="Submit"/>
</s:form>

</body>
</html>