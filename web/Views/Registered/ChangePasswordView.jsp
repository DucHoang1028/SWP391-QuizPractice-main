<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>

        <!DOCTYPE html>

        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Change Password</title>
            <link rel="stylesheet" href="./Assets/Styles/User/ChangePass.css" />
            <script src="./Assets/Js/Common/jquery.min.js"></script>
            <script src="./Assets/Js/ChangePass.js"></script>
        </head>

        <body>
            <div class="main-component-changepassword" id="main-component-changepassword">
                <div class="container-change">
                    <div class="container__hedaer-change">
                        <div class="container__header-title-change">
                            <h1>Change Password</h1>
                        </div>
                        <div id="close_changebutton" class="container__header-close-change">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                fill="none">
                                <path d="M18 18L12 12M12 12L6 6M12 12L18 6M12 12L6 18" stroke="#1D2026" stroke-width="2"
                                    stroke-linecap="round" stroke-linejoin="round" />
                            </svg>
                        </div>
                    </div>
                    <div class="container__content-change">
                        <form id="changePasswordForm">
                            <input name="action" value="change" hidden>
                            <div class="input-container-change">
                                <label for="currentpassword" class="input-label-change">Current Password</label>
                                <input required name="currentpassword" type="password" id="currentpassword"
                                    class="input-field-change" placeholder="Enter your current password">
                            </div>
                            <div class="input-container-change">
                                <label for="newpassword" class="input-label-change">New Password</label>
                                <input required name="newpassword" type="password" id="newpassword"
                                    class="input-field-change" placeholder="Enter your new password">
                            </div>
                            <div class="input-container-change">
                                <label for="confirmpassword" class="input-label-change">Confirm </label>
                                <input required name="confirmpassword" type="password" id="confirmpassword"
                                    class="input-field-change" placeholder="Confirm you password">
                            </div>
                            <p id="message-change"></p>
                            <button class="submit_button-change">Continue</button>
                        </form>
                    </div>
                </div>
            </div>
            <div id="modal-change" class="process-modal-change">
                <div class="loader-change"></div>
            </div>
        </body>

        </html>