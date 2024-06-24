<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Blog Details</title>
        <!-- Link to CSS files -->
        <link rel="stylesheet" href="./Assets/Styles/main.css">
        <link rel="stylesheet" href="./Assets/Styles/Blog/BlogDetail.css">
        <link rel="icon" href="./Assets/Images/Common/logo.ico">
    </head>

    <body>
        <!-- Set current page variable -->
        <c:set scope="request" var="currentPage" value="Blogs" />
        <!-- Include the header view -->
        <jsp:include page="../Common/HeaderView.jsp"></jsp:include>

            <main>
                <!-- Content area -->
                <div class="content">
                    <h2>Blog Detail</h2>
                    <div class="post-title">${post.title}</div>
                <div class="category">${categoryName}</div>
                <div class="post-meta">
                    <div class="author-info">
                        <img src="${authorAvatar}" alt="Author Avatar" class="author-avatar">
                        <span>Created by: ${authorName}</span>
                    </div>
                    <div class="post-date">
                        <span class="datetime">${post.post_date}</span>
                    </div>
                </div>
                <img class="post-image" src="${post.thumbnail}" alt="Post Image">
                <div class="thumbnail-description">Description</div> <!-- Move it here -->
                <div class="description">
                    ${post.content}
                </div>
                <!-- Back to Top Button -->
                <button id="back-to-top" class="back-to-top">Back to Top</button>
            </div>

            <!-- Sidebar area -->
            <div class="sider">
                <!-- Search bar -->
                <div class="search-bar">
                    <input id="search-input-view" type="text" placeholder="Search By Title">
                    <button class="register-btn" onclick="handleSearchByTitle()">Search</button>
                </div>

                <!-- Categories section -->
                <div class="categories">
                    <h3>Categories 
                        <button class="register-btn" onclick="handleCategoryFilter()">Enter Category</button>
                    </h3>
                    <div class="category-list" id="category-list">
                        <c:forEach items="${categories}" var="category">
                            <div class="category-column">
                                <label>
                                    <input type="checkbox" class="category-checkbox" value="${category.category_id}">
                                    ${category.category_name}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="toggle-button-container">
                        <button id="toggle-button" class="toggle-button" onclick="toggleCategories()">Show More <span class="arrow">&#9660;</span></button>
                    </div>
                </div>

                <!-- Featured blogs section -->
                <div class="latest-posts">
                    <div class="latest-posts-header">
                        <h3>Latest Posts</h3>
                        <a href="bloglist" class="back-to-bloglist">Back to Bloglist</a>
                    </div>

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

                <!-- Contact Us Section -->
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

                <!-- Useful Links Section -->
                <div class="links mt-5">
                    <h3>Useful Links</h3>
                    <ul class="list-group">
                        <li class="list-group-item"><a href="#">About Us</a></li>
                        <li class="list-group-item"><a href="#">Privacy Policy</a></li>
                        <li class="list-group-item"><a href="#">Terms of Service</a></li>
                    </ul>
                </div>
            </div>
        </main>

        <!-- Include the footer view -->
        <jsp:include page="../Common/FooterView.jsp"></jsp:include>

        <!-- Hidden form for handling searches -->
        <form id="form" action="bloglist" hidden>
            <input id="search-input" name="search" value="2">
            <input id="search-value-input" name="search_value" value="">
            <input id="sort-input" name="sort" value="1">
            <input id="page-input" name="page" value="1">
            <input id="category-id-input" name="category_id" value="">
        </form>

        <!-- Link to JavaScript files -->
        <script src="./Assets/Js/Sider.js"></script>
        <script src="./Assets/Js/Pagination.js"></script>
        <script src="./Assets/Js/FormatDateTime.js"></script>
        <script src="./Assets/Js/BlogDetail.js"></script>

    </body>

</html>
