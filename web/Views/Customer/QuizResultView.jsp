
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quiz Finished</title>
        <link rel="icon" href="./Assets/Images/Common/logo.ico">
        <link rel="stylesheet" href="./Assets/Styles/Error.css">
        <style>

        </style>
    </head>
    <body>
        <div class="container">   
            <h1>Submitted</h1>
            <h2>Your record has been saved successfully!!</h2>
            <h3>Thank you for your participation</h3>
            <h3>Click the button below to review your quiz</h3>
            <a href="/app/quiz-review?record_id=${record_id}&order=1">Review now</a>
            <a href="/app/home">Back to Home</a>
        </div>
    </body>
</html>
