
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="./Assets/Styles/Customer/explain.css"/>
    </head>
    <body>
        <div class="explain" id="explain-container">
            <div class="explain__popup">
                <div class="explain__popup-title">
                    <div class="explain__popup-left">
                        <div class="explain__subtitle">From expert</div>
                        <div class="explain__title">Explanation</div>
                    </div>
                    <div class="explain__popup-right" id="close_button2">
                        <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 48 52" fill="none">
                        <path d="M35.9999 38.9999L24 26M24 26L12 13M24 26L36.0001 13M24 26L12 39.0001" stroke="#1D2026" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                    </div>
                </div>
                <div class="explain__popup-content">
                    ${requestScope.question.explaination}
                </div>
            </div>
        </div>
    </body>
</html>
