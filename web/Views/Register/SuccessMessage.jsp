<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Successfully!</title>
        <link rel="stylesheet" href="./Assets/Styles/Login/Message.css"/>
        <link rel="stylesheet" href="./Assets/Styles/Normalize.css"/>
    </head>
    <body>
        <div class="container">
            <div class="container__image">
                <img src="./Assets/Images/Login/success.svg" alt="alt"/>
            </div>
            <div class="container__message">
                <h1>Successfully</h1>
                <p>${requestScope.message}</p>
            </div>
            <div class="container__content">
                <a href="home"><button class="submit_button">${requestScope.butt}</button></a>
            </div>
        </div>
    </div>
</body>
</html>
