<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="Filters.SecurityConfig, jakarta.servlet.http.HttpSession,
        Models.Role, Models.User, Models.MenuRole, java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="./Assets/Styles/Common/SideBarNav.css"/>
        <script src="./Assets/Js/Common/jquery.min.js"></script>
        <script src="./Assets/Js/Popup.js"></script>


    </head>
    <body>
        <%-- Retrieve the current user and their role --%>
        <%
            User currentUser = (User) session.getAttribute("currentUser");
            int role = Role.ROLE_GUEST;
            if (currentUser != null) {
                role = currentUser.getRole();
            }
            List<MenuRole> menuItems = SecurityConfig.GetInstance().GetRoleMenuForSideNavBar(role);
            request.setAttribute("menuitems", menuItems);
        %>
        <jsp:include page="../Registered/UserProfileView.jsp"></jsp:include>
        <jsp:include page="../Registered/ChangePasswordView.jsp"></jsp:include>

            <div class="marketing-sidebar">
                <div class="marketing-sidebar-logo">
                    <a href="home">
                        <i class="marketing-sidebar-logo-icon">
                            <img src="./Assets/Images/logo.png" alt="alt" />
                        </i>
                        <div class="marketing-sidebar-logo-name">QPS</div>
                    </a>
                    <i id="marketing-btn">
                        <svg class="profile-svg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--!Font Awesome Free 6.5.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.--><path fill="#ffffff" d="M0 96C0 78.3 14.3 64 32 64H416c17.7 0 32 14.3 32 32s-14.3 32-32 32H32C14.3 128 0 113.7 0 96zM0 256c0-17.7 14.3-32 32-32H416c17.7 0 32 14.3 32 32s-14.3 32-32 32H32c-17.7 0-32-14.3-32-32zM448 416c0 17.7-14.3 32-32 32H32c-17.7 0-32-14.3-32-32s14.3-32 32-32H416c17.7 0 32 14.3 32 32z"/></svg>
                    </i>
                </div>

                <ul class="marketing-sidebar-navlist">
                <c:choose>
                    <c:when test="${requestScope.menuitems!=null}">
                        <c:forEach var="item" items="${menuitems}">
                            <c:if test="${requestScope.currentPage == item.name}">
                                <div class="selected">
                                    <li>
                                        <a href="${item.getLink()}">
                                            <i>
                                                ${item.getSvgtag()}
                                            </i>
                                            <span class="marketing-links_name">${item.getName()}</span>
                                        </a>
                                        <span class="marketing-tooltip">${item.getName()}</span>
                                    </li>
                                </div>
                            </c:if>
                            <c:if test="${requestScope.currentPage != item.name}">
                                <li>
                                    <a href="${item.getLink()}">
                                        <i>
                                            ${item.getSvgtag()}
                                        </i>
                                        <span class="marketing-links_name">${item.getName()}</span>
                                    </a>
                                    <span class="marketing-tooltip">${item.getName()}</span>
                                </li>
                            </c:if>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <h1>We have some problems with side bar</h1>
                    </c:otherwise>
                </c:choose>
                <c:if test="${sessionScope.currentUser!=null}">
                    <li>
                        <a id="profile-button">
                            <i>
                                <svg class="profile-svg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--!Font Awesome Free 6.5.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.--><path fill="#ffffff" d="M224 256A128 128 0 1 0 224 0a128 128 0 1 0 0 256zm-45.7 48C79.8 304 0 383.8 0 482.3C0 498.7 13.3 512 29.7 512H418.3c16.4 0 29.7-13.3 29.7-29.7C448 383.8 368.2 304 269.7 304H178.3z"/></svg>
                            </i>
                            <span class="marketing-links_name">My Profile</span>
                        </a>
                        <span class="marketing-tooltip">My Profile</span>
                    </li>
                    <li>
                        <a id="changepassword-button">
                            <i>
                                <svg width="20" height="26" viewBox="0 0 20 26" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M12 2V9H18" fill="#280559"/>
                                <path d="M12 2V9H18" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M5.66667 16.3333C5.48986 16.3333 5.32029 16.2631 5.19526 16.1381C5.07024 16.013 5 15.8435 5 15.6667C5 15.4899 5.07024 15.3203 5.19526 15.1953C5.32029 15.0702 5.48986 15 5.66667 15H15C15.1768 15 15.3464 15.0702 15.4714 15.1953C15.5964 15.3203 15.6667 15.4899 15.6667 15.6667C15.6667 15.8435 15.5964 16.013 15.4714 16.1381C15.3464 16.2631 15.1768 16.3333 15 16.3333H5.66667ZM5.66667 19.6667C5.48986 19.6667 5.32029 19.5964 5.19526 19.4714C5.07024 19.3464 5 19.1768 5 19C5 18.8232 5.07024 18.6536 5.19526 18.5286C5.32029 18.4036 5.48986 18.3333 5.66667 18.3333H15C15.1768 18.3333 15.3464 18.4036 15.4714 18.5286C15.5964 18.6536 15.6667 18.8232 15.6667 19C15.6667 19.1768 15.5964 19.3464 15.4714 19.4714C15.3464 19.5964 15.1768 19.6667 15 19.6667H5.66667Z" fill="white" stroke="white" stroke-width="0.5"/>
                                <path fill-rule="evenodd" clip-rule="evenodd" d="M11.9133 1H3C2.46957 1 1.96086 1.21071 1.58579 1.58579C1.21071 1.96086 1 2.46957 1 3V23C1 23.5304 1.21071 24.0391 1.58579 24.4142C1.96086 24.7893 2.46957 25 3 25H17.6667C18.1971 25 18.7058 24.7893 19.0809 24.4142C19.456 24.0391 19.6667 23.5304 19.6667 23V9.26933C19.6665 8.76863 19.4786 8.28619 19.14 7.91733L13.388 1.648C13.2005 1.44366 12.9727 1.28053 12.7188 1.16898C12.4649 1.05743 12.1906 0.99988 11.9133 1ZM2.33333 3C2.33333 2.82319 2.40357 2.65362 2.5286 2.5286C2.65362 2.40357 2.82319 2.33333 3 2.33333H11.9133C12.0058 2.33323 12.0974 2.35238 12.1821 2.38957C12.2668 2.42676 12.3428 2.48117 12.4053 2.54933L18.1573 8.81867C18.2704 8.94155 18.3332 9.10237 18.3333 9.26933V23C18.3333 23.1768 18.2631 23.3464 18.1381 23.4714C18.013 23.5964 17.8435 23.6667 17.6667 23.6667H3C2.82319 23.6667 2.65362 23.5964 2.5286 23.4714C2.40357 23.3464 2.33333 23.1768 2.33333 23V3Z" fill="white" stroke="white" stroke-width="0.5"/>
                                <path d="M7.84494 7.84467C8.03534 7.8505 8.22498 7.81803 8.4026 7.74919C8.58022 7.68035 8.7422 7.57655 8.87895 7.44392C9.01569 7.3113 9.12441 7.15257 9.19865 6.97714C9.2729 6.80171 9.31115 6.61316 9.31115 6.42267C9.31115 6.23217 9.2729 6.04362 9.19865 5.86819C9.12441 5.69276 9.01569 5.53403 8.87895 5.40141C8.7422 5.26879 8.58022 5.16498 8.4026 5.09614C8.22498 5.0273 8.03534 4.99484 7.84494 5.00067C7.47534 5.01199 7.12467 5.16676 6.86723 5.43221C6.60979 5.69765 6.46582 6.05289 6.46582 6.42267C6.46582 6.79244 6.60979 7.14769 6.86723 7.41313C7.12467 7.67857 7.47534 7.83335 7.84494 7.84467Z" fill="white"/>
                                <path fill-rule="evenodd" clip-rule="evenodd" d="M10.688 10.925C10.688 9.41303 9.41467 8.31836 7.844 8.31836C6.27333 8.31836 5 9.41169 5 10.925V11.637C5.00035 11.7627 5.05052 11.8831 5.1395 11.9718C5.22848 12.0605 5.34901 12.1104 5.47467 12.1104H10.2147C10.3401 12.11 10.4603 12.06 10.549 11.9713C10.6377 11.8826 10.6876 11.7625 10.688 11.637V10.925Z" fill="white"/>
                                </svg>
                            </i>
                            <span class="marketing-links_name">Change Password</span>
                        </a>
                        <span class="marketing-tooltip">Change Password</span>
                    </li>
                    <div class="sidebar-logout">
                        <li class="dashboard-profile">
                            <div class="dashboard-profile-profile-details">
                                <img src="${currentUser.getAvatar()}" alt="profileImg" />
                                <div class="name_job">
                                    <div class="name">${currentUser.getFull_name()}</div>
                                    <div class="job">${currentUser.getRoleName()}</div>
                                </div>
                            </div>

                            <a href="login?action=logout">
                                <i id="log_out">
                                    <svg width="19" height="19" viewBox="0 0 19 19" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M18.8495 14.0547C18.8506 10.6032 18.853 7.15175 18.8489 3.70029C18.8466 1.6997 17.3908 0.164613 15.4487 0.157011C13.746 0.150578 11.0234 0.147069 8.73842 0.146484C8.22645 0.146484 7.81152 0.564028 7.81152 1.07982V1.08508C7.81152 1.60029 8.22587 2.01841 8.73842 2.01841C11.0077 2.01841 13.7082 2.019 15.3935 2.02017C16.3082 2.02075 16.991 2.70379 16.9922 3.62602C16.9963 7.54414 16.9963 11.4629 16.9922 15.3816C16.991 16.3026 16.3059 16.9839 15.3906 16.9845C13.7065 16.9857 11.0077 16.9863 8.73901 16.9863C8.22703 16.9863 7.8121 17.4038 7.8121 17.9196C7.8121 18.4348 8.22645 18.8523 8.73842 18.8529C11.006 18.8535 13.7036 18.8564 15.386 18.8517C17.2531 18.8465 18.7083 17.4822 18.8344 15.6085C18.8687 15.0927 18.8495 14.5728 18.8495 14.0547Z" fill="#FF6636"/>
                                    <path d="M3.54834 8.57571C3.62621 8.57571 3.7035 8.57571 3.78137 8.57571C6.30463 8.57571 8.82789 8.57454 11.3512 8.5763C11.9416 8.57688 12.3519 8.9763 12.3408 9.52893C12.3315 10.0003 11.9671 10.3921 11.4999 10.4301C11.4064 10.4377 11.3116 10.4365 11.2175 10.4365C8.73782 10.4371 6.25814 10.4365 3.77847 10.4365C3.70118 10.4365 3.62447 10.4365 3.50069 10.4365C3.57216 10.519 3.60936 10.5681 3.65236 10.6114C3.98477 10.9476 4.32124 11.2792 4.65074 11.6184C5.04126 12.0207 5.05056 12.5979 4.67863 12.9687C4.30671 13.34 3.73139 13.3295 3.33565 12.9342C2.43083 12.0289 1.52834 11.1213 0.628748 10.2108C0.199294 9.7763 0.201619 9.23302 0.633397 8.79618C1.52834 7.89092 2.42502 6.98799 3.32519 6.08799C3.73372 5.6798 4.30729 5.66694 4.6827 6.04647C5.05753 6.426 5.0401 6.99676 4.63331 7.41314C4.26778 7.78682 3.89527 8.15349 3.52626 8.52308C3.53207 8.54062 3.5402 8.55817 3.54834 8.57571Z" fill="#FF6636"/>
                                    </svg>
                                </i>
                            </a>
                    </div>
                    </li>
                </c:if>
            </ul>
        </div>
        <script src="./Assets/Js/SideBar.js"></script>
    </body>
</html>
