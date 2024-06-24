<%-- Document : SubjectRegister Created on : May 23, 2024, 3:13:48 PM Author : Dumb Trung --%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/Assets/Styles/SubjectRegister/Layout.css">

            <title>Subject Registration </title>
        </head>

        <body>
            <div class="subject-register-component" id="subject-register-component">
                <div class="subject-register-container popup">
                    <span class="close" id="close-subject-register-popup">&times;</span>
                    <form action="subject-register" method="post">
                        <h2>Register for Subject</h2>
                        <div class="personal-details">
                            <span class="title">Personal Details</span>

                            <div class="fields">
                                <div class="subject-input-field">
                                    <label>Full Name</label>
                                    <c:choose>
                                        <c:when test="${currentUser != null}">
                                            <input value="${currentUser.getFull_name()}" disabled>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" placeholder="Enter your name" required>
                                        </c:otherwise>
                                    </c:choose>


                                </div>
                                <div class="subject-input-field">
                                    <label>Email</label>
                                    <c:choose>
                                        <c:when test="${currentUser != null}">
                                            <input value="${currentUser.getEmail()}" disabled>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" placeholder="Enter your email" required>
                                        </c:otherwise>
                                    </c:choose>

                                </div>

                                <div class="subject-input-field">
                                    <label>Mobile Number</label>
                                    <c:choose>
                                        <c:when test="${currentUser != null}">
                                            <input value="${currentUser.getMobile()}" disabled>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="tel" pattern="[0-9]{10}" placeholder="Enter mobile number"
                                                required>
                                        </c:otherwise>
                                    </c:choose>

                                </div>

                                <div class="subject-input-field">
                                    <label>Gender</label>
                                    <c:choose>
                                        <c:when test="${currentUser != null}">
                                            <input value="${currentUser.getGenderName()}" disabled>
                                        </c:when>
                                        <c:otherwise>
                                            <select required>
                                                <option>Male</option>
                                                <option>Female</option>
                                            </select>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                                <div class="subject-details">
                                    <span class="title">Subject Details</span>
                                    <div class="fields">
                                        <div class="subject-input-field">
                                            <label for="subjectID">Subject ID:</label>
                                            <input type="text" id="subject_id" disabled />
                                        </div>
                                        <div class="subject-input-field">
                                            <label for="subjectName">Subject Name:</label>
                                            <input type="text" name="subjectName" id="title" disabled />
                                        </div>

                                        <div class="subject-input-field">
                                            <label for="pricePackage">Choose Price Package:</label>
                                            <select name="pricePackage" id="pricePackage" required>
                                                <!-- Options will be populated by JavaScript -->
                                            </select>
                                        </div>
                                    </div>
                                    <div class="submit">
                                        <button class="backBtn">

                                            <span class="btnText">Submit</span>
                                        </button>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <script src="Assets/Js/SubjectRegister.js"></script>


        </body>

        </html>