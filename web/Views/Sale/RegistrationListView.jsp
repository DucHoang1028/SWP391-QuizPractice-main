<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrations Management</title>
        <link rel="stylesheet" href="./Assets/Styles/Sale/table.css"/>
        <link rel="stylesheet" href="./Assets/Styles/Sale/RegistrationList.css"/>
        <link rel="stylesheet" href="./Assets/Styles/Sale/fixedColumns.dataTables.css"/>

        <link rel="icon" href="./Assets/Images/Common/logo.ico">
        <script src="./Assets/Js/Common/jquery.min.js"  type="text/javascript" defer></script>
        <script src="./Assets/Js/Common/dataTables.js"  type="text/javascript" defer></script>
        <script src="./Assets/Js/Common/dataTables.fixedColumns.js"  type="text/javascript" defer></script>
        <script src="./Assets/Js/Common/fixedColumns.dataTables.js"  type="text/javascript" defer></script>

        <script src="./Assets/Js/Sales/mainList.js"></script>
        <link rel="stylesheet" href="./Assets/Styles/Sale/Popup.css"/>

    </head>
    <body>
        <jsp:include page="./RegistrationFilterPopup.jsp"></jsp:include> 
        <c:set scope="request" var="currentPage" value="Registration Management"></c:set>
        <jsp:include page="../Common/SideBarView.jsp"></jsp:include> 


            <div class="main">
                <div class="main__title">
                    <div class="main__title-text">Customer Registration</div>
                    <div class="main__title-subtext">View and manage all regstration from customer</div>
                </div>
                <div class="main__navigation">

                <c:if test="${param.status == 3 || param.status == null}"><div class="main__navigation-item main__navigation-select" id="all">
                    </c:if> 
                    <c:if test="${param.status != 3 && param.status != null}"><div class="main__navigation-item" id="all">
                        </c:if> 
                        <c:if test="${param.status == 3 || param.status == null}"><div class="menu-select"></div></c:if> 
                            All registration
                        </div>
                    <c:if test="${param.status != 0}"><div class="main__navigation-item" id="pending"></c:if>
                        <c:if test="${param.status == 0}"><div class="main__navigation-item main__navigation-select" id="pending"></c:if>
                            <c:if test="${param.status == 0}"><div class="menu-select"></div></c:if> 
                                Pending
                            </div>
                        <c:if test="${param.status != 1}"><div class="main__navigation-item" id="paid"></c:if>
                            <c:if test="${param.status == 1}"><div class="main__navigation-item main__navigation-select" id="paid"></c:if>
                                <c:if test="${param.status == 1}"><div class="menu-select"></div></c:if> 
                                    Paid</div>
                            <c:if test="${param.status != 2}"><div class="main__navigation-item" id="cancel"></c:if>
                                <c:if test="${param.status == 2}"><div class="main__navigation-item main__navigation-select" id="cancel"></c:if>

                                    <c:if test="${param.status == 2}"><div class="menu-select"></div></c:if> 
                                        Canceled</div>
                                </div>
                                <div class="main__data">
                                    <div class="data__header">
                                        <div class="data__header-title">Registrations</div> 
                                        <div class="data__header-add"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
                                            <path d="M15 11H11V15H9V11H5V9H9V5H11V9H15M10 0C8.68678 0 7.38642 0.258658 6.17317 0.761205C4.95991 1.26375 3.85752 2.00035 2.92893 2.92893C1.05357 4.8043 0 7.34784 0 10C0 12.6522 1.05357 15.1957 2.92893 17.0711C3.85752 17.9997 4.95991 18.7362 6.17317 19.2388C7.38642 19.7413 8.68678 20 10 20C12.6522 20 15.1957 18.9464 17.0711 17.0711C18.9464 15.1957 20 12.6522 20 10C20 8.68678 19.7413 7.38642 19.2388 6.17317C18.7362 4.95991 17.9997 3.85752 17.0711 2.92893C16.1425 2.00035 15.0401 1.26375 13.8268 0.761205C12.6136 0.258658 11.3132 0 10 0Z" fill="white"/>
                                            </svg>Add Registration</div>
                                    </div>
                                    <div class="data__filter">
                                        <div class="search-bar">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
                                            <path fill-rule="evenodd" clip-rule="evenodd" d="M9.14645 0.888672C7.84497 0.888783 6.56239 1.20012 5.40571 1.7967C4.24904 2.39329 3.2518 3.25782 2.49722 4.31818C1.74263 5.37853 1.25257 6.60396 1.06792 7.89223C0.883265 9.18049 1.00938 10.4942 1.43574 11.7238C1.8621 12.9535 2.57635 14.0633 3.51888 14.9607C4.46142 15.8582 5.60491 16.5172 6.85396 16.8829C8.10301 17.2485 9.4214 17.3102 10.6991 17.0627C11.9769 16.8153 13.1769 16.2658 14.1991 15.4603L17.7058 18.9668C17.8869 19.1417 18.1294 19.2385 18.3812 19.2363C18.6329 19.2341 18.8738 19.1332 19.0518 18.9551C19.2298 18.7771 19.3308 18.5363 19.333 18.2845C19.3352 18.0328 19.2384 17.7902 19.0635 17.6091L15.5568 14.1026C16.5055 12.8991 17.0962 11.4529 17.2613 9.92941C17.4264 8.40593 17.1592 6.86674 16.4904 5.488C15.8215 4.10926 14.778 2.94667 13.4792 2.13328C12.1804 1.31989 10.6789 0.888569 9.14645 0.888672ZM2.90508 9.05015C2.90508 7.3949 3.56265 5.80745 4.73313 4.63701C5.90362 3.46657 7.49114 2.80902 9.14645 2.80902C10.8018 2.80902 12.3893 3.46657 13.5598 4.63701C14.7302 5.80745 15.3878 7.3949 15.3878 9.05015C15.3878 10.7054 14.7302 12.2929 13.5598 13.4633C12.3893 14.6337 10.8018 15.2913 9.14645 15.2913C7.49114 15.2913 5.90362 14.6337 4.73313 13.4633C3.56265 12.2929 2.90508 10.7054 2.90508 9.05015Z" fill="#280559"/>
                                            </svg>
                                            <input id="myInputTextField"  placeholder="Search for registrations email here">
                                        </div>
                                        <div><div class="data__filter-btn" id="data__filter-btn">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="14" viewBox="0 0 20 14" fill="none">
                                                <path d="M8.90063 13.1966H10.9393C11.4999 13.1966 11.9586 12.738 11.9586 12.1773C11.9586 11.6167 11.4999 11.158 10.9393 11.158H8.90063C8.34 11.158 7.88131 11.6167 7.88131 12.1773C7.88131 12.738 8.34 13.1966 8.90063 13.1966ZM0.746094 1.98416C0.746094 2.54478 1.20479 3.00348 1.76541 3.00348H18.0745C18.6351 3.00348 19.0938 2.54478 19.0938 1.98416C19.0938 1.42354 18.6351 0.964844 18.0745 0.964844H1.76541C1.20479 0.964844 0.746094 1.42354 0.746094 1.98416ZM4.82336 8.10006H15.0165C15.5771 8.10006 16.0358 7.64137 16.0358 7.08074C16.0358 6.52012 15.5771 6.06143 15.0165 6.06143H4.82336C4.26274 6.06143 3.80404 6.52012 3.80404 7.08074C3.80404 7.64137 4.26274 8.10006 4.82336 8.10006Z" fill="#280559"/>
                                                </svg>  Filters
                                            </div>

                                        </div>       
                                    </div>
                                    <div class="data__list">
                                        <table id="data">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Email</th>
                                                    <th>Registration Time</th>
                                                    <th>Subject</th>
                                                    <th>Package</th>
                                                    <th>Valid from</th>
                                                    <th>Valid to</th>
                                                    <th>Last Updated</th>
                                                    <th>Cost</th>
                                                    <th>Status</th>
                                                    <th>Details</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="registration" items="${requestScope.registrationList}">
                                                <tr>
                                                    <th>${registration.registrationId}</th>
                                                    <th>${registration.userEmaill}</th>
                                                    <td class="datetime">${registration.registrationTime}</td>
                                                    <td>${registration.subjectName}</td>
                                                    <td>${registration.packageName}</td>
                                                    <td class="datetime">${registration.validFrom}</td>
                                                    <td class="datetime">${registration.validTo}</td>
                                                    <td>${registration.lastUpdated}</td>
                                                    <td>${registration.totalCost}$</td>
                                                    <c:if test="${registration.status == 0}">
                                                        <th><div class="status-table">Pending</div></th>
                                                        </c:if>
                                                        <c:if test="${registration.status == 1}">
                                                        <th><div class="status-table-paid">Paid</div></th>
                                                        </c:if>
                                                        <c:if test="${registration.status == 2}">
                                                        <th><div class="status-table-cancel">Canceled</div></th>
                                                        </c:if>
                                                    <th><a href="registration-details?registration_id=${registration.registrationId}&action=edit"><div class="option"><img src="./Assets/Images/Icons/option.svg"></div></a></th>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="paginition">
                                    <div class="paginition-entry">
                                        <P>${(requestScope.page-1) * 10+1}</P> - ${(requestScope.page) * 10 <= requestScope.size ? (requestScope.page) * 10 :requestScope.size} of ${requestScope.size}
                                    </div>
                                    <div class="paginition-actions">
                                        <div class="paginition-actions-text"> 
                                            The page you’re on
                                        </div>
                                        <div class="paginition-actions-select"> 
                                            <select id="pageSelect"> 
                                                <c:forEach varStatus="loop" begin="1" end="${requestScope.numberOfPage}">
                                                    <c:if test="${requestScope.page == loop.index}"><option selected>${loop.index}</option></c:if>
                                                    <c:if test="${requestScope.page != loop.index}"><option>${loop.index}</option></c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <c:if test="${requestScope.page != 1}">
                                            <div class="paginition-actions-previous">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="46" height="46" viewBox="0 0 46 46" fill="none">
                                                <path d="M30.0374 19.7291L19.2849 19.7291L23.9825 15.0315C24.3579 14.6561 24.3579 14.04 23.9825 13.6646C23.6071 13.2892 23.0006 13.2892 22.6252 13.6646L16.2815 20.0083C16.1923 20.0973 16.1215 20.2031 16.0732 20.3196C16.0249 20.436 16 20.5609 16 20.6869C16 20.813 16.0249 20.9378 16.0732 21.0543C16.1215 21.1708 16.1923 21.2765 16.2815 21.3656L22.6156 27.7189C22.7047 27.808 22.8105 27.8787 22.9269 27.9269C23.0434 27.9752 23.1682 28 23.2942 28C23.4202 28 23.5451 27.9752 23.6615 27.9269C23.7779 27.8787 23.8837 27.808 23.9729 27.7189C24.062 27.6298 24.1327 27.524 24.1809 27.4075C24.2291 27.2911 24.254 27.1663 24.254 27.0402C24.254 26.9142 24.2291 26.7894 24.1809 26.673C24.1327 26.5565 24.062 26.4507 23.9729 26.3616L19.2849 21.6544L30.0374 21.6544C30.5668 21.6544 31 21.2212 31 20.6918C31 20.1623 30.5668 19.7291 30.0374 19.7291Z" fill="#280559"/>
                                                <defs>
                                            </div>
                                        </c:if>
                                        <c:if test="${requestScope.page != requestScope.numberOfPage}">
                                            <div class="paginition-actions-next">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="46" height="46" viewBox="0 0 46 46" fill="none">
                                                <path d="M16.9626 21.2709H27.7151L23.0175 25.9685C22.6421 26.3439 22.6421 26.96 23.0175 27.3354C23.3929 27.7108 23.9994 27.7108 24.3748 27.3354L30.7185 20.9917C30.8077 20.9027 30.8785 20.7969 30.9268 20.6804C30.9751 20.564 31 20.4391 31 20.3131C31 20.187 30.9751 20.0622 30.9268 19.9457C30.8785 19.8292 30.8077 19.7235 30.7185 19.6344L24.3844 13.2811C24.2953 13.192 24.1895 13.1213 24.0731 13.0731C23.9566 13.0248 23.8318 13 23.7058 13C23.5798 13 23.4549 13.0248 23.3385 13.0731C23.2221 13.1213 23.1163 13.192 23.0271 13.2811C22.938 13.3702 22.8673 13.476 22.8191 13.5925C22.7709 13.7089 22.746 13.8337 22.746 13.9598C22.746 14.0858 22.7709 14.2106 22.8191 14.327C22.8673 14.4435 22.938 14.5493 23.0271 14.6384L27.7151 19.3456H16.9626C16.4332 19.3456 16 19.7788 16 20.3082C16 20.8377 16.4332 21.2709 16.9626 21.2709Z" fill="#280559"/>
                                                <defs>
                                            </div></a
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            </body>
                            </html>
