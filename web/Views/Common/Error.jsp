
<%@page isErrorPage="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Error</title>
        <link rel="stylesheet" href="./Assets/Styles/Error.css">
        <style>

        </style>
    </head>
    <body>
        <div class="container">   
            <c:set var="errorData" value="${pageContext.errorData}"></c:set>       
            <h1>${errorData.statusCode}</h1>
            <h2>URI: ${errorData.requestURI}</h2>
            <c:choose>
                <c:when test="${errorData.statusCode eq 404}">
                    <h2>NOT FOUND</h2>
                    <p>We couldn't find the page you're looking for. It might have been moved or deleted.</p>
                </c:when>
                <c:when test="${errorData.statusCode eq 400}">
                    <h2>BAD REQUEST</h2>
                    <p>Oops! It seems like there was a problem with your request. Please check the URL and try again.</p>
                </c:when>
                <c:when test="${errorData.statusCode eq 403}">
                    <h2>FORBIDDEN</h2>
                    <p>Access to this resource is forbidden. You may not have the necessary permissions to view it.</p>
                </c:when>
                <c:otherwise>
                    <!-- Default case if none of the conditions match -->
                    <h2>Error</h2>
                    <p>Sorry, an unexpected error occurred. Please try again later.</p>
                </c:otherwise>
            </c:choose>
            <a href="/app/home">Go to Homepage</a>
        </div>
    </body>
</html>
