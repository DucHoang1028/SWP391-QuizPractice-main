<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blog List</title>
    <link rel="stylesheet" href="./Assets/Styles/main.css">
    <link rel="stylesheet" href="./Assets/Styles/Blog/BlogList.css">
    <link rel="icon" href="./Assets/Images/Common/logo.ico">
</head>

<body>
    <c:set scope="request" var="currentPage" value="Blogs"/>
    <jsp:include page="../Common/HeaderView.jsp"></jsp:include> 

    <main>
        <div class="content">
            <h2>Blogs</h2>
            <h3 class="results-count">Showing results of ${categoryMap[category_id]} Post
                <br>${result_length} results found.<br>
            </h3>
            <div class="sort-by">
                <label for="sort">Sort by:</label>
                <select id="sort" name="sort" onchange="handleSortChange()">
                    <option value="1" ${sort == 1 ? "selected" : ""}>Recently Updated</option>
                    <option value="2" ${sort == 2 ? "selected" : ""}>Title A-Z</option>
                </select>
            </div>
            <ul class="blog-list">
                <c:if test="${blogs == null || blogs.size() == 0}">
                    <h1>There is no result for your search. Try again later.</h1>
                </c:if>
                <c:forEach items="${blogs}" var="blog">
                    <li>
                        <a href="blogdetail?blog_id=${blog.post_id}" class="blog-item">
                            <div class="thumbnail" data-src="${blog.thumbnail}"></div>
                            <div class="blog-info">
                                <h3>${blog.title}</h3>
                                <span class="last-updated datetime">${blog.post_date}</span>
                                <div class="category">${categoryMap[blog.category_id]}</div>
                                <p class="summary">${blog.summary}</p>
                            </div>
                        </a>
                    </li>
                </c:forEach>
            </ul>
            <div class="pagination">
                <c:if test="${page > 1}">
                    <div class="button" onclick="handlePageClick(1)" >
                        <div class="arrow"><<</div>
                    </div>
                    <div class="button" onclick="handlePageClick(${page-1})" >
                        <div class="arrow"><</div>
                    </div>
                </c:if>

                <div id="pagination" class="pages" data-current-page="${page}" data-total-pages="${pages_number}">
                    <c:forEach begin="1" end="${pages_number}" var="p">
                        <div class="${page == p ? "current-page" : "page"}" data-page="${p}">
                            <span class="${page == p ? "current-page-number" : "page-number"}"
                                  onclick="handlePageClick(${p})">
                                ${p}
                            </span>
                        </div>
                    </c:forEach>
                </div>

                <c:if test="${page < pages_number}">
                    <div class="button" onclick="handlePageClick(${page+1})" >
                        <div class="arrow">></div>
                    </div>
                    <div class="button" onclick="handlePageClick(${pages_number})">
                        <div class="arrow">>></div>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="sider">
            <div class="search-bar">
                <input id="search-input-view" type="text" placeholder="Search By Title" value="${search_value}">
                <button class="register-btn" onclick="handleSearchByTitle()">Search</button>
            </div>
            <div class="categories">
                <h3>Categories</h3>
                <div class="category-list" id="category-list">
                    <c:forEach items="${categories}" var="category">
                        <div class="category-column">
                            <label>
                                <input type="checkbox" name="category" value="${category.category_id}" <c:if test="${fn:contains(category_id, category.category_id)}">checked</c:if>>
                                ${category.category_name}
                            </label>
                        </div>
                    </c:forEach>
                </div>
                <div class="toggle-button-container">
                    <button id="toggle-button" class="toggle-button" onclick="toggleCategories()">Show More <span class="arrow">&#9660;</span></button>
                </div>
                <button class="enter-category-btn" onclick="enterCategory()">Enter Category</button>
            </div>
            <div class="Latest-posts">
                <h3>Latest Posts <a href="bloglist?search=4" ></a></h3>
                <ul>
                    <c:forEach items="${latest_posts}" var="blog">
                        <li class="latest-item">
                            <a href="blogdetail?blog_id=${blog.post_id}" class="blog-link">
                                <div class="thumbnail" style="background-image: url('${blog.thumbnail}');"></div>
                                <div class="post-info">
                                    <h4>${blog.title}</h4>
                                    <p class="brief-info">${blog.summary}</p>
                                </div>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="contact-us mt-5">
                <h3>Contact Us</h3>
                <ul class="list-group">
                    <li class="list-group-item">
                        <strong>Phone:</strong> <a href="tel:+1234567890">+1 (234) 567-890</a>
                    </li>
                    <li class="list-group-item">
                        <strong>Email:</strong> <a href="mailto:info@example.com">info@example.com</a>
                    </li>
                    <li class="list-group-item">
                        <strong>Address:</strong>
                        <address>
                            123 Main Street,<br>
                            City, State, ZIP Code
                        </address>
                    </li>
                </ul>
            </div>

            <div class="links mt-5">
                <h3>Useful Links</h3>
                <ul class="list-group">
                    <li class="list-group-item"><a href="#">About Us</a></li>
                    <li class="list-group-item"><a href="#">Privacy Policy</a></li>
                    <li class="list-group-item"><a href="#">Terms of Service</a></li>
                </ul>
            </div>
    </main>

    <form id="form" action="bloglist" hidden>
        <input id="search-input" name="search" value="${search}">
        <input id="search-value-input" name="search_value" value="${search_value}">
        <input id="sort-input" name="sort" value="${sort}">
        <input id="page-input" name="page" value="${page}">
        <input id="category-id-input" name="category_id" value="${category_id}">
    </form>

    <jsp:include page="../Common/FooterView.jsp"></jsp:include> 

    <script src="./Assets/Js/Sider.js"></script>
    <script src="./Assets/Js/Pagination.js"></script>
    <script src="./Assets/Js/FormatDateTime.js"></script>
    <script src="./Assets/Js/BlogList.js"></script>
</body>

</html>
