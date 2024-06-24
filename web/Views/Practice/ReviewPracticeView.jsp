<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Practice Review</title>
        <link rel="stylesheet" href="./Assets/Styles/Practice/ReviewPractice.css">
        <link rel="icon" href="./Assets/Images/Common/logo.ico">
        <link rel="stylesheet" href="./Assets/Styles/main.css">
    </head>
    <body>
        <div class="container">
            <div class="header">
                <h1>Practice Review</h1>
                <a href="practicelist" class="btn back-btn">Back to Practice List</a>
            </div>
            <div class="practice-info">
                <div class="left">
                    <p><strong>Question Number:</strong> ${practice.numQuestions}</p>
                    <p><strong>Correct Number:</strong> <span class="score">${practice.getNumberOfCorrectQuestions()}</span></p>
                    <p><strong>Correct %:</strong> <span class="percentage">${practice.getCorrectPercentage()}</span></p>
                    <p><strong>Duration:</strong> <span class="duration">${practice.getDuration()}</span></p>
                    <p><strong>Questions are Selected By:</strong> 
                        <c:choose>
                            <c:when test="${practice.quizType == 1}">By Subject Topic</c:when>
                            <c:when test="${practice.quizType == 3}">By Specific Dimension</c:when>
                            <c:otherwise>N/A</c:otherwise>
                        </c:choose>
                    </p>
                </div>
                <div class="right">
                    <p><strong>Subject Name:</strong> ${practice.subjectName}</p>
                    <p><strong>Exam Name:</strong> ${practice.examName}</p>
                    <p><strong>Exam Date:</strong> <span class="datetime">${practice.examDate}</span></p>
                    <p><strong>
                        <c:choose>
                            <c:when test="${practice.quizType == 1}">Type</c:when>
                            <c:when test="${practice.quizType == 3}">Keyword</c:when>
                            <c:otherwise>N/A</c:otherwise>
                        </c:choose>:</strong> 
                        <c:choose>
                            <c:when test="${practice.type == null}">All <c:choose>
                                <c:when test="${practice.quizType == 1}">Type</c:when>
                                <c:when test="${practice.quizType == 3}">Keyword</c:when>
                                <c:otherwise>N/A</c:otherwise>
                            </c:choose>
                            </c:when>
                            <c:otherwise>${practice.type}</c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>
            <a href="quiz-review?record_id=${practice.quizId}&order=1" class="btn review-btn">Practice Review</a>
        </div>
        <script src="./Assets/Js/ScoreFormatter.js"></script>
        <script src="./Assets/Js/FormatDateTime.js"></script>
        <script src="./Assets/Js/PracticeReview.js"></script>
    </body>
</html>
