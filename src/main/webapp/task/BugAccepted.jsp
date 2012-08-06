<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Bug Accepted</title>
</head>
<body>
<h1>Bug accepted.</h1>

<ul>
    <li><s:a action="SubmitBug">Submit Bug</s:a></li>
    <li><s:a action="BugsReviewList">List of bugs to review</s:a></li>
</ul>

</body>
</html>