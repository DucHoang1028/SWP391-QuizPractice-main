
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*, java.net.*, java.util.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User login popup</title>
        <link rel="stylesheet" href="./Assets/Styles/Login/Login.css"/>
        <script src="./Assets/Js/Common/jquery.min.js"></script>
        <script src="./Assets/Js/Login.js"></script>
    </head>
    <body >
        <%
             var message =  request.getParameter("action") ;
             var messageError =request.getParameter("message");
             if(messageError == null) messageError = "";
            String logincss="";

        %>
        <div class="main-component" id="main-component" >

            <div class="container">
                <div class="container__hedaer">
                    <div class="container__header-title">
                        <p>Welcome back</p>
                        <h1>Log In to your Account</h1>
                    </div>
                    <div id="close_button" class="container__header-close">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
                        <path d="M18 18L12 12M12 12L6 6M12 12L18 6M12 12L6 18" stroke="#1D2026" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                    </div>
                </div>
                <div class="container__content" >
                    <c:set value="${pageContext.request.cookies}" var="cookie"></c:set>
                        <form id="loginForm" action="login" method="POST">
                            <div class="input-container">
                                <label  for="email" class="input-label">Email</label>
                                <input  value="${cookie.email.value}" required name="email" type="email" id="email" class="input-field" placeholder="Enter your email address">
                        </div>
                        <div class="input-container" style="margin-top: 30px">
                            <label for="password" class="input-label">Password</label>
                            <input  value="${cookie.pass.value}" required name="password" type="password" id="password" class="input-field" placeholder="Enter your password">


                            <svg class="toggle-password" id="eyeIcon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" onclick="togglePassword()">
                            <path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7-11-7-11-7z"/>
                            <circle cx="12" cy="12" r="3"/>
                            </svg>

                            <svg class="toggle-password hidden" id="eyeSlashIcon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" onclick="togglePassword()">
                            <path d="M17.94 17.94A10.015 10.015 0 0 1 12 19c-7 0-11-7-11-7a14.36 14.36 0 0 1 5-5M1 1l22 22M3 3l18 18"/>
                            <path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7-11-7-11-7z"/>
                            <circle cx="12" cy="12" r="3"/>
                            </svg>
                        </div>

                        <div class="container__content-others">
                            <div class="remember-me">
                                <label class="checkbox-container">
                                    <input id="remember" name="remember" type="checkbox" ${cookie.remember.value eq 'true' ? 'checked' : ''}>
                                    <span class="checkmark"></span>
                                    Remember me
                                </label>
                            </div>
                            <div class="forgot-password">
                                <a href="/app/reset-password">Forgot Password?</a> 
                            </div>
                        </div>
                        <button class="submit_button">Login</button>
                        <!-- Add inline style becasue some error occure dont recognize css file -->
                        <div id="error__message" class="error__message" >
                            <p><%=messageError%></p>
                        </div>


                    </form>
                    <div class="social-container"><div class="signup-container">
                            <div class="line"></div>
                            <div class="text">Or Sign up With</div>
                            <div class="line"></div>
                        </div>
                        <div class="social-buttons"><a href="requestlogin-google"><div class="google-signup">
                                    <img src="./Assets/Images/Icons/googleico.png" alt="Google Sign Up">
                                </div>
                            </a>
                            <a href="requestFacebook"><div class="facebook-signup">
                                    <img src="./Assets/Images/Icons/facebookicon.png" alt="Facebook Sign Up">
                                </div>
                            </a>
                        </div>  
                    </div>
                    <div class="register">
                        New user?  <div id="register-button-login"><a id="register-link" onclick="openRegisterPopup()">Register now!</a></div>
                    </div>
                </div>
            </div>
        </div> 
    </div>
</body>
<%
if(message !=null && message.equals("login"))
             {
%>
<script>
    openLoginPopup();
</script>
<%
        }    
%>
</html>
