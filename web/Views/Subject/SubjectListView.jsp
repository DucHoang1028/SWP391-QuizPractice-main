<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Subject List</title>
        <link rel="icon" href="./Assets/Images/Common/logo.ico">
        <link rel="stylesheet" href="./Assets/Styles/main.css">
        <link rel="stylesheet" href="./Assets/Styles/Normalize.css">
        <link rel="stylesheet" href="./Assets/Styles/SubjectList.css">
    </head>

    <body>
        <c:set scope="request" var="currentPage" value="Subjects"/>
        <jsp:include page="../Common/HeaderView.jsp"></jsp:include> 
        <jsp:include page="./SubjectRegister.jsp"></jsp:include> 
            <main>
                <div class="content">
                    <h3 class="search-message">${message}</h3>
                <h3 class="search-message">${result_length} results found.</h3>
                <div class="sort-by">
                    <label for="sort">Sort by:</label>
                    <select id="sort" name="sort" onchange="handleSortChange()">
                        <option value="1" ${sort == 1 ? "selected":""}>Recently Updated</option>
                        <option value="2" ${sort == 2 ? "selected":""}>Title A-Z</option>
                        <option value="3" ${sort == 3 ? "selected":""}>Lowest Price</option>

                    </select>
                </div>
                <ul class="subject-list">

                    <c:if test="${subjects == null || subjects.size() == 0}">
                        <h1>There is no result for your search. Try again later.</h1>
                    </c:if>
                    <c:forEach items="${subjects}" var="subject">
                        <li>
                            <div class="subject-item" >
                                <a href="subject-details?id=${subject.getSubject_id()}">
                                    <div class="thumbnail" data-src="${subject.getThumbnail()}"></div>
                                </a>
                                <div class="subject-info">
                                    <a href="subject-details?id=${subject.getSubject_id()}">
                                        <h3>
                                            ${subject.getTitle()} ${subject.isIs_featured() ? 
                                              "<span class='featured-tick'>&#10003;</span>": ""}
                                        </h3>
                                    </a>

                                    <span class="last-updated datetime">${subject.getUpdated_at()}</span>
                                    <div class="category">${subject.getCategory()}</div>
                                    <p class="price">
                                        <span class="original-price">$${subject.getList_price()}</span> 
                                        $${subject.getSale_price()}
                                    </p>
                                    <button class="register-btn" 
                                            onclick="showRegisterPopup(${subject.getSubject_id()},
                                                            '${subject.getTitle()}', '${subject.getCategory()}',
                                                            `${subject.getPricePackage()}`)">
                                        Register Now
                                    </button>
                                    <p class="tagline">${subject.getTag_line()}</p>
                                </div>
                            </div>

                        </li>
                    </c:forEach>

                </ul> 
                <div class="pagination">
                    <c:if test="${page > 1}">
                        <div class="button" onclick="handlePageClick(1)"  >
                            <div class="arrow">
                                <<
                            </div>
                        </div>
                        <div class="button" onclick="handlePageClick(${page-1})" >
                            <div class="arrow">
                                <
                            </div>
                        </div>
                    </c:if>

                    <div id="pagination" class="pages" data-current-page="${page}" data-total-pages="${pages_number}">
                        <c:forEach begin="1" end="${pages_number}" var="p">
                            <div class="${page == p ? "current-page" : "page"}" data-page="${p}">
                                <span class="${page == p ? "current-page-number": "page-number"}"
                                      onclick="handlePageClick(${p})">
                                    ${p}
                                </span>
                            </div>
                        </c:forEach>
                    </div>

                    <c:if test="${page < pages_number}">
                        <div class="button" onclick="handlePageClick(${page+1})" >
                            <div class="arrow">
                                >
                            </div>
                        </div>
                        <div class="button" onclick="handlePageClick(${pages_number})">
                            <div class="arrow">
                                >>
                            </div>
                        </div>
                    </c:if>

                </div>
            </div>
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
                                           onclick="filterByCategory()" 
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
        </main>

        <form id="form" action="subjectlist" hidden>
            <input id="search-input" name="search" value="${search}">
            <input id="category-input" name="categories" value="${filtered_categories}">
            <input id="search-value-input" name="search_value" value="${search_value}">
            <input id="sort-input" name="sort" value="${sort}">
            <input id="page-input" name="page" value="${page}">       
        </form>


        <jsp:include page="../Common/FooterView.jsp"></jsp:include> 


        <script src="./Assets/Js/Sider.js"></script>
        <script src="./Assets/Js/SubjectList.js"></script>
        <script src="./Assets/Js/Pagination.js"></script>
        <script src="./Assets/Js/FormatDateTime.js"></script>
    </body>

</html>