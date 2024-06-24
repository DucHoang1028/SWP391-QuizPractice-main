<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>New Practice</title>
        <link rel="stylesheet" href="./Assets/Styles/Practice/NewPractice.css">
        <link rel="icon" href="./Assets/Images/Common/logo.ico">
        <link rel="stylesheet" href="./Assets/Styles/main.css">
    </head>
    <body>
        <div class="container">
            <div class="header">
                <h1>New Practice</h1>
                <a href="practicelist" class="btn back-btn">Back to Practice List</a>
            </div>

            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>

            <form action="newpractice" method="post" id="selectionForm">
                <input type="hidden" name="createPractice" value="true">
                <label for="subject">Subject</label>
                <select id="subject" name="subject" onchange="this.form.submit();">
                    <c:forEach var="subject" items="${registeredSubjects}">
                        <option value="${subject.subject_id}" <c:if test="${subject.subject_id == selectedSubjectId}">selected</c:if>>${subject.title}</option>
                    </c:forEach>
                </select>
                <label for="examName">Exam Name</label>
                <input type="text" id="examName" name="examName" required>

                <label for="numQuestions">Number of Practicing Questions</label>
                <input type="number" id="numQuestions" name="numQuestions" value="20" required>

                <label for="selectionType">Questions are Selected by Topic(s) or a Specific Dimension?</label>
                <select id="selectionType" name="selectionType" onchange="this.form.submit();">
                    <option value="topic" <c:if test="${selectedSelectionType == 'topic'}">selected</c:if>>By Subject Topic</option>
                    <option value="dimension" <c:if test="${selectedSelectionType == 'dimension'}">selected</c:if>>By Specific Dimension</option>
                </select>
                <label for="questionGroup">Question Group (Choose One or All Topics/Dimensions)</label>
                <select id="questionGroup" name="questionGroup">
                    <option value="all">All</option>
                    <c:forEach var="group" items="${groups}">
                        <option value="${group.id}">${group.name}</option>
                    </c:forEach>
                </select>

                <button type="submit" class="btn" name="action" value="create">Create Practice</button>
            </form>
        </div>
    </body>
</html>
