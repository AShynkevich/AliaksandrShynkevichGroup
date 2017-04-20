<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Basic web page</title>
    </head>
    <body>
        <h2>Data provided by Tomcat servlet:</h2>
        <p>
            <c:choose>
                <c:when test="${ not empty message }">
                    <h3>${message}</h3>
                </c:when>
                <c:otherwise>
                    <h3>none</h3>
                </c:otherwise>
            </c:choose>
        </p>
    
        <h2>Image provided by HTTP server:</h2>
        <img src="images/asf_logo.png">
    </body>
</html>
