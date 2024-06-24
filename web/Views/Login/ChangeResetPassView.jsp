

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./Assets/Styles/Login/ChangePass.css"/>
        <link rel="stylesheet" href="./Assets/Styles/Normalize.css"/>
        <title>Change password</title>
    </head>
    <body>
        <div class="container">
            <div class="container__hedaer">
                <div class="container__header-title" style="display: flex; justify-content: center;align-items: center">
                    <h1>Reset password</h1>
                </div>

            </div>
            <div class="container__subtitle">
                Enter new password and confirmation to reset your password
            </div>
            <div class="container__content">
                <form action="reset-password" method="POST" >
                    <input name="action" value="changePassword" hidden>
                    <input name="token" value="${requestScope.token}" hidden>
                    
                    <div class="input-container">
                        <label  for="newPass" class="input-label">Password</label>
                        <input required name="newPass" type="password" id="email" class="input-field" placeholder="Enter your new password">
                    </div>
                    <div class="input-container">
                        <label  for="confirmation" class="input-label">Confirmation</label>
                        <input required name="confirmation" type="password" id="email" class="input-field" placeholder="Enter your password again">
                    </div>
                    <button id="sendButton"  class="submit_button">Continue</button>        
                </form>
            </div>
            <c:if test="${requestScope.fail!=null}" >
                <div class="action__message">
                    ${requestScope.fail}
                </div> 
            </c:if>
        </div>
    </body>
</html>
