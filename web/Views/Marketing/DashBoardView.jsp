<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <link rel="stylesheet" href="./Assets/Styles/Marketing/Dashboard.css"/>
        <link rel="icon" href="./Assets/Images/Common/logo.ico">
        <link rel="stylesheet" href="./Assets/Styles/Marketing/daterangepicker.css"/>
        <link rel="stylesheet" href="./Assets/Styles/Marketing/Revenue.css"/>
        <script src="./Assets/Js/Common/chart.js"></script>
        <script src="./Assets/Js/Common/jquery.min.js"></script>

    </head>

    <body>
        <c:set scope="request" var="currentPage" value="Dashboard"/>
        <jsp:include page="../Common/SideBarView.jsp"></jsp:include> 


            <main>
                <div class="empty"></div>
                <div class="dashboard">
                    <div class="dashboard-title">
                        <h1>Dashboard</h1>
                        <p>View total statistics for marketing team</p>
                    </div>
                <c:choose>
                    <c:when test="${currentUser.getRole()== 2 || currentUser.getRole() == 3 || currentUser.getRole() == 5}">
                        <div class="dashboard-content">
                            <div class="dashboard-content-kpicards">
                                <div class="dashboard-content-kpicards-items">
                                    <h1>${requestScope.totalSubjectsCount}</h1>
                                    <p>Total Subjects</p>
                                </div>
                                <div class="dashboard-content-kpicards-items">
                                    <h1>${requestScope.todayNewSubCount}</h1>
                                    <p>Today's New Subjects</p>
                                </div>
                                <div class="dashboard-content-kpicards-items">
                                    <h1>${requestScope.newlyRegisteredCustomer}</h1>
                                    <p>Newly Registered Customer</p>
                                </div>
                                <div class="dashboard-content-kpicards-items">
                                    <h1>${requestScope.newlyBoughtCustomer}</h1>
                                    <p>Newly Bought Customer</p>
                                </div>
                                <div class="dashboard-content-kpicards-items">
                                    <h1>$${requestScope.totalRevenue}</h1>
                                    <p>Total Revenue</p>
                                </div>
                            </div>
                            <div class="dashboard-content-list">
                                <div class="dashboard-content-list-items">
                                    <div class="dashboard-content-list-items-header">
                                        <p>New Subjects by Categories</p>
                                        <button onclick="ViewAllSubject()" class="dashboard-categorylist-button">View All</button>
                                    </div>
                                    <div class="dashboard-content-list-items-headerlist">
                                        <span>Categories</span>
                                        <span>Subjects</span>
                                    </div>
                                    <ul class="dashboard-content-list-items-list">
                                        <c:forEach items = "${subjectsCountByCategories}" var = "items">
                                            <li>
                                                <span>${items.key}</span>
                                                <span>${items.value}</span>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                                <div class="dashboard-content-list-items">
                                    <div class="dashboard-content-list-items-header total-registr">
                                        <p>Total Subjects Registration</p>
                                    </div>
                                    <div class="piechart">
                                        <canvas id="myPieChart" width="100" height="200"></canvas>
                                    </div>
                                    <div class="dashboard-content-piechart-details">
                                        <ul class="dashboard-content-legend">
                                            <li><span class="dash-color" style="background-color: #8DD6C2;"></span> <span class="dash-label">Successfully Registration</span><span class="dash-num" id = "success">${successfullyRegistration}</span></li>
                                            <li><span class="dash-color" style="background-color: #F8A1A4;"></span> <span class="dash-label">Cancelled Registration</span><span class="dash-num" id="cancelled">${cancelledRegistration}</span></li>
                                            <li><span class="dash-color" style="background-color: #F9D369;"></span> <span class="dash-label">Submitted Registration</span><span class="dash-num" id="submitted">${submittedRegistration}</span></li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="dashboard-content-list-items">
                                    <div class="dashboard-content-list-items-header">
                                        <p>Revenues by Categories</p>
                                        <button onclick="ViewAll()" class="dashboard-categorylist-button">View All</button>
                                    </div>
                                    <input type="hidden" name="action" value="view">
                                    <div class="dashboard-content-list-items-headerlist">
                                        <span>Categories</span>
                                        <span>Revenues</span>
                                    </div>
                                    <ul class="dashboard-content-list-items-list">
                                        <c:forEach items = "${formattedRevenuesByCategories}" var = "items">
                                            <li>
                                                <span>${items.key}</span>
                                                <span>$${items.value}</span>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                            <div class="dashboard-select-date">
                                <div id="reportrange">
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--!Font Awesome Free 6.5.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.--><path fill="#000000" d="M96 32V64H48C21.5 64 0 85.5 0 112v48H448V112c0-26.5-21.5-48-48-48H352V32c0-17.7-14.3-32-32-32s-32 14.3-32 32V64H160V32c0-17.7-14.3-32-32-32S96 14.3 96 32zM448 192H0V464c0 26.5 21.5 48 48 48H400c26.5 0 48-21.5 48-48V192z"/></svg>&nbsp;
                                    <span></span> <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 320 512"><!--!Font Awesome Free 6.5.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.--><path fill="#000000" d="M137.4 374.6c12.5 12.5 32.8 12.5 45.3 0l128-128c9.2-9.2 11.9-22.9 6.9-34.9s-16.6-19.8-29.6-19.8L32 192c-12.9 0-24.6 7.8-29.6 19.8s-2.2 25.7 6.9 34.9l128 128z"/></svg>
                                </div>
                            </div>
                            <div class="dashboard-content-linechart">
                                <div class="dashboard-content-linechart-content">
                                    <div class="dashboard-content-list-items-header">
                                        <p>Total Subjects Registration</p>
                                        <div id="message-linechart"></div>
                                    </div>
                                    <canvas id="myChart" width="100" height="30"></canvas>
                                    <div class="order-data">
                                        <div id="reportrange">
                                            <span></span>
                                        </div>
                                        <div id="successOrderContainer">
                                            <c:forEach items = "${successOrderCount}" var = "items">
                                                <span>${items.key}</span>
                                                <span>${items.value}</span>

                                            </c:forEach>
                                        </div>
                                        <div id="allOrderContainer">>
                                            <c:forEach items = "${allOrderCount}" var = "items">
                                                <span>${items.key}</span>
                                                <span>${items.value}</span>

                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="dashboard-content-noaccess">
                            <h1>You don't have permission to access this page! Please go to <a href="home">Home Page</a></h1>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
        <div id="revenuePopup" class="revenue-popup">
            <div class="revenue-popup-content">
                <span class="revenue-close" onclick="hidePopup()">&times;</span>
                <h2>Revenues by Categories</h2>
                <div class="revenue-dropdown">
                    <label for="categories">Categories</label>
                    <select id="categories" onchange="updateList()">
                        <c:forEach items="${categoryList}" var="category">
                            <option value="${category.category_id}">${category.category_name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="dashboard-popup">
                    <table>
                        <thead>
                            <tr>
                                <th>Subject</th>
                                <th>Revenue</th>
                            </tr>
                        </thead>
                        <tbody id="revenue-list">
                            <c:forEach items="${revenueBySubject}" var="subject">
                                <tr>
                                    <td>${subject.key}</td>
                                    <td>$${subject.value}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div id="subjectListPopup" class="revenue-popup">
            <div class="revenue-popup-content">
                <span class="revenue-close" onclick="hidePopup()">&times;</span>
                <h2>New Subjects by Categories</h2>
                <div class="revenue-dropdown">
                    <label for="categories">Categories</label>
                    <select id="subjects" onchange="updateSubjectList()">
                        <c:forEach items="${categoryList}" var="categoryChoose">
                            <option value="${categoryChoose.category_id}">${categoryChoose.category_name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="dashboard-popup">
                    <table>
                        <thead>
                            <tr>
                                <th>Subject</th>
                                <th>Status</th>
                                <th>Created At</th>
                            </tr>
                        </thead>
                        <tbody id="subject-list">
                            <c:forEach items="${subjectList}" var="subject">
                                <tr>
                                    <td>${subject.getTitle()}</td>
                                    <td >${subject.getSubjectStatus(subject.getStatus())}</td>
                                    <td class="datetime">${subject.getFormattedDate(subject.getCreated_at())}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <!-- loop -->
        <!-- key, value -->
        <!-- script addItem (key, value) -->

        <script src="./Assets/Js/Dashboard.js"></script>
        <c:forEach items = "${successOrderCount}" var = "items">
            <script>
                        addSuccessItem('${items.key}', '${items.value}');
            </script>
        </c:forEach>
        <c:forEach items = "${allOrderCount}" var = "items">
            <script>
                addAllItem('${items.key}', '${items.value}');
            </script>
        </c:forEach>
        <c:forEach items="${revenueBySubject}" var="subject">
            <script>
                addRevenueItem('${subject.key}', '${subject.value}');
            </script>
        </c:forEach>
        <script src="./Assets/Js/Common/lastestjquery.min.js"></script>
        <script src="./Assets/Js/Common/moment.min.js"></script>
        <script src="./Assets/Js/Common/daterangepicker.min.js"></script>
        <!--<script src="./Assets/Js/FormatDateTime.js"></script>-->


    </body>

</html>
