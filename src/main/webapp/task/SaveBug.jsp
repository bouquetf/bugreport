<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Bug saved</title>
</head>
<body>
<h1>Your bug has been submitted.</h1>

<ul>
    <li><s:a action="SubmitBug">Submit Bug</s:a></li>
    <li><s:a action="BugsReviewList">List of bugs to review</s:a></li>
</ul>

</body>
</html>