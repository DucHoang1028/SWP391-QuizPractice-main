<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registered Subject List</title>
     <link rel="stylesheet" href="./Assets/Styles/Practice/PracticeList.css">
        <link rel="icon" href="./Assets/Images/Common/logo.ico">
        <link rel="stylesheet" href="./Assets/Styles/main.css">
        <link rel="stylesheet" href="./Assets/Styles/Normalize.css">
</head>
<body>
    <!-- Set current page variable -->
    <c:set scope="request" var="currentPage" value="RegisteredSubjects"/>
    <!-- Include the header view -->
    <jsp:include page="../Common/HeaderView.jsp"></jsp:include>
    <main class="main-content">
        <section class="registered-subject-list">
            <header class="header">
                <h2>Registered Subjects</h2>
            </header>
            <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Subject</th>
                <th>Registration Time</th>
                <th>Package</th>
                <th>Total Cost</th>
                <th>Status</th>
                <th>Valid From</th>
                <th>Valid To</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="registration" items="${registrations}">
                <tr>
                    <td>${registrations.id}</td>
                    <td>${registrations.subject.title}</td>
                    <td>${registrations.registrationTime}</td>
                    <td>${registrations.pricePackage.name}</td>
                    <td>${registrations.totalCost}</td>
                    <td>${registrations.status}</td>
                    <td>${registrations.validFrom}</td>
                    <td>${registrations.validTo}</td>
                    <td>
                        <c:if test="${registrations.status == 0}">
                            <form action="MyRegistrationController" method="post" style="display:inline;">
                                <input type="hidden" name="registrationId" value="${registrations.id}">
                                <input type="hidden" name="action" value="cancel">
                                <button type="submit">Cancel</button>
                            </form>
                            <a href="SubjectRegister.jsp?registrationId=${registrations.id}">Edit</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
        </section>
    </main>
    
    <div class="sider">
                <div class="search-bar">
                    <input id="search-input-view" type="text" placeholder="Search By Title">
                    <button class="register-btn" onclick="handleSearchByTitle()">Search</button>
                </div>
                <div class="categories">
                    <h3>Categories</h3>
                    <div class="category-list" id="category-list">
                        <c:forEach items="${categories}" var="category">
                            <div class="category-column">
                                <label>
                                    <input type="checkbox" 
                                           onclick="filterByCategory('${category.getCategory_id()}')" 
                                           value="${category.getCategory_id()}" >
                                    ${category.getCategory_name()}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="toggle-button-container">
                        <button id="toggle-button" class="toggle-button" onclick="toggleCategories()">Show More <span class="arrow">&#9660;</span></button>
                    </div>
                </div>
                <div class="featured-subjects">
                    <h3>Featured Subjects </h3>
                    <ul>
                        <c:forEach items="${featured_subjects}" var="subject">
                            <li >
                                <a href="subject-details?id=${subject.getSubject_id()}" class="featured-item">
                                    <div class="thumbnail" data-src="${subject.getThumbnail()}"></div>
                                    <div class="subject-info">
                                        <h4>${subject.getTitle()}</h4>
                                        <p class="tagline">${subject.getTag_line()}</p>
                                    </div> 
                                </a>

                            </li>
                        </c:forEach>

                    </ul>
                    <div class="toggle-button-container">
                        <a href="subjectlist?search=3" class="toggle-button">Show More <span class="arrow">&#9660;</span></a>   
                    </div>

                </div>
                <div class="contact-info">
                    <h3>Contact Us</h3>
                    <ul>
                        <li>Email: <a href="#">se1823.swp391.group4@gmail.com</a></li>
                        <li>Phone: <a href="#">+123 456 7890</a></li>
                        <li>Address: <a href="#">FPT Univesity, Hanoi, Vietnam</a></li>
                    </ul>
                </div>
                <div class="links">
                    <h3>Useful Links</h3>
                    <ul>
                        <li><a href="#">About Us</a></li>
                        <li><a href="#">Privacy Policy</a></li>
                        <li><a href="#">Terms of Service</a></li>
                    </ul>
                </div>
            </div>
    <form id="form" action="practicelist" hidden>
            <input id="filter-input" name="filter" value="${filter}">
            <input id="search-input" name="search" value="${search}">
            <input id="search-value-input" name="search_value" value="${search_value}">
            <input id="sort-input" name="sort" value="${sort}">
            <input id="page-input" name="page" value="${page}">
        </form>
    <!-- Include the footer view -->
    <jsp:include page="../Common/FooterView.jsp"></jsp:include> 
    <script src="./Assets/Js/Pagination.js"></script>
    <script src="./Assets/Js/FormatDateTime.js"></script>
    <script src="./Assets/Js/MyRegistration.js"></script>
    <script src="./Assets/Js/Sider.js"></script>
    <script src="./Assets/Js/Modals.js"></script>
</body>
</html>
