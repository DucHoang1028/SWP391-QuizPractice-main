<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="Filters.SecurityConfig, jakarta.servlet.http.HttpSession,
        Models.Role,Models.User,java.util.*,
        java.util.Map.Entry" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <link rel="stylesheet" href="./Assets/Styles/Common/header.css" />
        <script src="./Assets/Js/Common/jquery.min.js"></script>
        <script src="./Assets/Js/Popup.js"></script>

    </head>

    <body>
        <% User currentUser=(User) session.getAttribute("currentUser"); int role=Role.ROLE_GUEST; if(currentUser
            !=null) role=currentUser.getRole(); List<Entry<String, String>> MenuItems =
            SecurityConfig.GetInstance().GetRoleMenu(role);
            request.setAttribute("menuitems",MenuItems);
        %>
        <jsp:include page="../Register/RegisterView.jsp"></jsp:include>
        <jsp:include page="../Login/LoginView.jsp"></jsp:include>
        <jsp:include page="../Registered/UserProfileView.jsp"></jsp:include>
        <jsp:include page="../Registered/ChangePasswordView.jsp"></jsp:include>



            <div class="main">
                <div class="navigation">
                    <ul class="navigation__menu">
                    <c:forEach var="item" items="${requestScope.menuitems}">
                        <c:if test="${requestScope.currentPage == item.key}">
                            <a href="${item.value}">
                                <div class="navigation__menu-item selected">
                                    ${item.key }
                                </div>
                            </a>
                        </c:if>
                        <c:if test="${requestScope.currentPage != item.key}">
                            <a href="${item.value}">
                                <div class="navigation__menu-item ">
                                    ${item.key }
                                </div>
                            </a>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>
            <div class="header">
                <diV class="header__left">
                    <a href="home">
                        <div class="header__left-logo">
                            <img src="./Assets/Images/logo.png" alt="alt" />
                            <div class="header__left-title">
                                QPS
                            </div>
                        </div>
                    </a>

                </diV>
                <diV class="header__right">
                    <!-- Button login and register -->
                    <c:if test="${sessionScope.currentUser == null}">
                        <div id="register-button" class="header__right-register">
                            Create Account
                        </div>
                        <div id="login-button" class="header__right-login">
                            Sign In
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.currentUser != null}">
                        <div class="header__right-userprofile" onclick="menuToggle();">
                            <img src="${currentUser.getAvatar()}" alt="alt" />
                        </div>
                        <div class="header__right-menu">
                            <h3>${currentUser.getFull_name()}<br /><span>${currentUser.getRoleName()}</span>
                            </h3>
                            <ul>
                                <li>
                                    <div id="profile-button" class="profile-button"><img
                                            src="./Assets/Images/Icons/user.png" />My Profile</div>
                                </li>
                                <li>
                                    <div id="changepassword-button" class="changepassword-button"><img
                                            src="./Assets/Images/Icons/settings.png" />Change Password</div>
                                </li>
                                <li><img src="./Assets/Images/Icons/question.png" /><a href="#">Help</a>
                                </li>
                                <li onclick="menuToggle();">
                                    <a href="login?action=logout"> <img
                                            src="./Assets/Images/Icons/log-out.png" />Logout
                                    </a>
                                </li>

                            </ul>
                        </div>
                    </c:if>

                </div>
            </diV>
        </div>

    </body>

</html>