
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
        <link rel="stylesheet" href="./Assets/Styles/Login/ResetPass.css"/>
        <link rel="stylesheet" href="./Assets/Styles/Normalize.css"/>
        <script src="./Assets/Js/ResetPassword.js"></script>
        <link rel="icon" href="./Assets/Images/Common/logo.ico">

    </head>
    <body >
        <c:set scope="request" var="currentPage" value="Reset"/>

        <div class="container">

            <div class="container__hedaer">
                <div class="container__header-title" style="display: flex; justify-content: center;align-items: center">
                    <a href="home"> <div class="container__header-close">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
                            <path d="M19 12H5M5 12L11 18M5 12L11 6" stroke="#002379" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </div></a>
                    <h1>Forgot
                        password?</h1>
                </div>

            </div>
            <div class="container__subtitle">
                Don’t worry ! It happens. Please enter the email we will send the reset password link in this email address.

            </div>
            <div class="container__content">
                <form action="reset-password" method="POST" onsubmit="showmodal()">
                    <div class="input-container">
                        <input name="action" value="sendemail" hidden>
                        <label  for="email" class="input-label">Email</label>
                        <input required name="email" type="email" id="email" class="input-field" placeholder="Enter your email address">
                    </div>
                    <button id="sendButton"  class="submit_button">Continue</button>        
                </form>
            </div>
            <c:if test="${requestScope.action!=null}" >
                <div class="action__message">
                    ${requestScope.action}
                </div> 
            </c:if>
        </div>
    </div>
    <div id="modal" class="process-modal">
        <div class="loader"></div>
    </div>
</body>
</html>
