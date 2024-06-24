<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*, java.net.*, java.util.*" %>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Register</title>
        <link rel="stylesheet" href="./Assets/Styles/Register/Register.css"/>
        <script src="./Assets/Js/Common/jquery.min.js"></script>
        <script src="./Assets/Js/Register.js"></script>
    </head>

    <body>
        <div class="main-component-regi" id="main-component-regi">
            <div class="container-regi">
                <div class="container__hedaer-regi">
                    <div class="container__header-title-regi">
                        <p>Welcome</p>
                        <h1>Register your new Account</h1>
                    </div>
                    <div id="close_regibutton" class="container__header-close-regi">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
                        <path d="M18 18L12 12M12 12L6 6M12 12L18 6M12 12L6 18" stroke="#1D2026" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                    </div>
                </div>
                <div class="container__content-regi">
                    <form id="registerForm" method="post" action="register">
                        <div class="input-container-regi">
                            <label  for="fullname" class="input-label-regi">Full Name</label>
                            <input required name="fullname" type="text" id="fullname" class="input-field-regi" placeholder="Enter your full name">
                        </div>
                        <div class="input-container-regi gender">
                            <label for="" class="popup-check">Gender</label>
                            <br>
                            <label class="male">
                                <input id="gender-male" type="radio" name="gender" value="1" required>Male
                            </label>
                            <label>
                                <input id="gender-female" type="radio" name="gender" value="0" required>Female
                            </label>
                        </div>
                        <div class="input-container-regi">
                            <label  for="email" class="input-label-regi">Email</label>
                            <input required name="email" type="email" id="register-email" class="input-field-regi" placeholder="Enter your email">
                        </div>
                        <div class="input-container-regi">
                            <label  for="mobile" class="input-label-regi">Mobile</label>
                            <input required name="mobile" type="tel" pattern="[0-9]{10}" id="mobile" class="input-field-regi" placeholder="Enter your mobile">
                        </div>
                        <div class="input-container-regi">
                            <label  for="password" class="input-label-regi">Password</label>
                            <input required name="password" type="password" id="password-register" class="input-field-regi" placeholder="Enter your password">
                        </div>
                        <div class="input-container-regi">
                            <label  for="password" class="input-label-regi">Confirm</label>
                            <input required name="password" type="password" id="confirm-password-register" class="input-field-regi" placeholder="Confirm your password">
                        </div>
                        <button class="submit_button-regi">Continue</button>
                        <p id="message-register"></p>
                        <div class="login">
                            Already have an account?  <div id="login-button-regi"><a id="login-link" onclick="openLoginPopup()">Login in here!</a></div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="container-regi-others social-container"><div class="signup-container">
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
        </div>
        <div id="modal-register" class="process-modal-register">
            <div class="loader-register"></div>
        </div>
    </body>
</html>
