<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home</title>
        <link rel="stylesheet" href="./Assets/Styles/main.css">
        <link rel="stylesheet" href="./Assets/Styles/Common/Home.css">
        <link rel="stylesheet" href="./Assets/Styles/bootstrap/css/bootstrap.css">
        <link rel="icon" href="./Assets/Images/Common/logo.ico">
    </head>
    <body>
        <!-- Slider Section -->
        <c:set scope="request" var="currentPage" value="Home"/>
        <jsp:include page="HeaderView.jsp"></jsp:include> 

            <div class="slider-container mb-4">
                <div class="slider">
                    <!-- Loop through the sliders list and generate each slide -->
                <c:forEach var="slider" items="${sliders}">
                    <div class="slide ${slider.status == 1 ? 'active' : ''}">
                        <div class="text-content">
                            <h2>${slider.title}</h2>
                        </div>
                        <div class="image-content">
                            <img src="${slider.image}" alt="promote">
                        </div>
                        <a href="${slider.back_link}" target="_blank" class="backlink"></a>
                    </div>
                </c:forEach>
            </div>

            <!-- Navigation buttons for the slider -->
            <button class="prev"><img src="./Assets/Images/Home/chevronleft-3@2x.png" alt="Previous"></button>
            <button class="next"><img src="./Assets/Images/Home/chevronright-3@2x.png" alt="Next"></button>

            <!-- Dots for slide indicators -->
            <div class="slider-dots">
                <c:forEach var="slider" items="${sliders}">
                    <span class="dot"></span>
                </c:forEach>
            </div>
        </div>
        <div class="border-bottom mb-4"></div>
        
     <div class="mt-5">
        <div class="row">
            <div class="col-md-10 border-end pe-4">
                <div class="best-post-container">
                    <div class="best-post">
                        <h3>School post</h3>
                        <div class="row" id="hotPostsContainer">
                            <c:if test="${hotPosts == null || hotPosts.isEmpty()}">
                                <p>No posts available.</p>
                            </c:if>
                            <c:forEach var="post" items="${hotPosts}">
                                <div class="col-md-2">
                                    <div class="card">
                                        <a href="blogdetail?blog_id=${post.post_id}" class="card-link">
                                            <img src="${post.thumbnail}" class="card-img-top" alt="${post.title} Thumbnail">
                                            <div class="card-body">
                                                <h5 class="card-title">${post.title}</h5>
                                                <p class="card-text">Post Date: <span class="datetime">${post.post_date}</span></p>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="pagination" id="hotPostsPagination" data-current-page="${hotPostsPage}" data-total-pages="${hotPostsTotalPages}">
                        <c:forEach var="i" begin="1" end="${hotPostsTotalPages}">
                            <span class="page" data-page="${i}" onclick="handlePageClick('hotPosts', ${i})">${i}</span>
                        </c:forEach>
                    </div>
                </div>

                    <div class="our-feature-subject mt-5">
                    <h3>The Right Course For You</h3>
                    <div class="featured-subjects-container">
                        <div class="row">
                            <c:if test="${featuredSubjects == null || featuredSubjects.isEmpty()}">
                                <p>No subjects available.</p>
                            </c:if>
                            <c:forEach var="subject" items="${featuredSubjects}">
                                <div class="col-md-4">
                                    <div class="card feature-card mb-4">
                                        <a href="subject-details?id=${subject.subject_id}" class="card-link">
                                            <div class="row no-gutters">
                                                <div class="col-md-4">
                                                    <img src="${subject.thumbnail}" class="card-img" alt="${subject.title}">
                                                </div>
                                                <div class="col-md-8">
                                                    <div class="card-body">
                                                        <h5 class="card-title text-orange">${subject.title}</h5>
                                                        <p class="card-text">${subject.tag_line}</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="pagination" id="featuredSubjectsPagination" data-current-page="${featuredSubjectsPage}" data-total-pages="${featuredSubjectsTotalPages}">
                        <c:forEach var="i" begin="1" end="${featuredSubjectsTotalPages}">
                            <span class="page" data-page="${i}" onclick="handlePageClick('featuredSubjects', ${i})">${i}</span>
                        </c:forEach>
                    </div>
                </div>

                    <div class="border-bottom mb-4 mt-5"></div>
                    <!-- Why Choose Us Section -->
                    <div class="why-choose-us mt-6">
                        <div class="row align-items-center">
                            <div class="col-md-6">
                                <img src="Assets/Images/Home/whychooseus.png" alt="Why Choose Us" class="img-fluid">
                            </div>
                            <div class="col-md-6">
                                <h3>Why Choose Us</h3>
                                <p>Not to be an overstatement but our education system is just purely the best of the best, i mean there is no one that can even dream to top us.</p>
                                <ul class="list-unstyled">
                                    <li>Good Teachers and Staffs</li>
                                    <li>We Value Good Characters</li>
                                    <li>Your Children are Safe</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Latest Posts and Contact Us Section -->
                <div class="col-md-2">
                    <div class="latest-posts">
                        <h3>Latest Posts</h3>
                        <!-- Check if there are no latest posts -->
                        <c:if test="${latestPosts == null || latestPosts.isEmpty()}">
                            <p>No posts available.</p>
                        </c:if>
                        <!-- Loop through the latestPosts list and generate each post -->
                        <c:forEach var="post" items="${latestPosts}">
                            <div class="card mb-3">
                                <a href="blogdetail?blog_id=${post.post_id}" class="card-link">
                                    <img src="${post.thumbnail}" class="card-img-top" alt="${post.title} Thumbnail">
                                    <div class="card-body">
                                        <h5 class="card-title">${post.title}</h5>
                                    </div>
                                </a>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="border-bottom mb-4 mt-5 "></div>
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
                    <div class="links mt-5 p-3 border bg-white rounded">
                        <h3>Useful Links</h3>
                        <ul class="list-group">
                            <li class="list-group-item border-0 p-0"><a href="#">About Us</a></li>
                            <li class="list-group-item border-0 p-0"><a href="#">Privacy Policy</a></li>
                            <li class="list-group-item border-0 p-0"><a href="#">Terms of Service</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="FooterView.jsp"></jsp:include> 
        <script src="Assets/Js/FormatDateTime.js"></script>
        <script src="Assets/Js/slider.js"></script>
        <script src="Assets/Js/HomePagination.js"></script>
         <script src="Assets/Js//Common/jquery.min.js"></script>
    </body>
</html>
