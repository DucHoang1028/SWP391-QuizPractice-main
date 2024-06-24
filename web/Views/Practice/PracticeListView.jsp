<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Practice List</title>
        <link rel="stylesheet" href="./Assets/Styles/Practice/PracticeList.css">
        <link rel="icon" href="./Assets/Images/Common/logo.ico">
        <link rel="stylesheet" href="./Assets/Styles/main.css">
    </head>
    <body>
        <c:set scope="request" var="currentPage" value="Practices"/>
        <jsp:include page="../Common/HeaderView.jsp"></jsp:include>
        <main class="main-content">
            <header class="header">
                <div class="filter-sort-area">
                    <div class="filter-by">
                        <label for="filter">Filter by:</label>
                        <select id="filter" name="filter" onchange="document.getElementById('form').submit()">
                            <option value="all" ${filter == 'all' ? 'selected' : ''}>All Subjects</option>
                            <c:forEach items="${subjects}" var="subject">
                                <option value="${subject.getSubject_id()}" ${filter == Integer.toString(subject.getSubject_id()) ? 'selected' : ''}>${subject.getTitle()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="sort-by">
                        <label for="sort">Sort by:</label>
                        <select id="sort" name="sort" onchange="document.getElementById('form').submit()">
                            <option value="recent" ${sort == 'recent' ? 'selected' : ''}>Recently Updated</option>
                            <option value="title" ${sort == 'title' ? 'selected' : ''}>Title A-Z</option>
                            <option value="correct" ${sort == 'correct' ? 'selected' : ''}>Correct %</option>
                        </select>
                    </div>
                </div>
                <div class="controls">
                    <div class="practice-controls">
                        <div class="actions">
                            <a href="newpractice" class="btn">New Practice</a> 
                            <a href="simulation-exam" class="btn">Simulation Exam</a>
                            <div class="search-container">
                                <input type="text" class="search-input" placeholder="Search Practice by title" id="search" name="search" value="${search}">
                                <c:if test="${search != ''}">
                                    <button class="btn clear-search-btn" onclick="clearSearch()">x</button>
                                </c:if>
                                <button class="btn" onclick="handleSearch()">Search</button>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
            <section class="practice-list">
                <c:if test="${not empty practices}">
                    <table>
                        <thead>
                            <tr>
                                <th>Practice Title</th>
                                <th>Exam Date</th>
                                <th>Duration</th>
                                <th>Question Number</th>
                                <th>Correct %</th>
                                <th>Details</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="practice" items="${practices}">
                                <tr class="practice-item">
                                    <td>
                                        <div class="practice-title">
                                            <p class="subject-name">Subject Name: ${practice.subjectName}</p>
                                            <p class="exam-name">Exam Name: ${practice.examName}</p>
                                            <p class="test-type">
                                                <c:choose>
                                                    <c:when test="${practice.quizType == 1}">Type: ${practice.type == null ? 'All Type' : practice.type}</c:when>
                                                    <c:when test="${practice.quizType == 3}">Keyword: ${practice.type == null ? 'All Keyword' : practice.type}</c:when>
                                                </c:choose>
                                            </p>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="exam-info">
                                            <p class="date datetime">${practice.examDate}</p>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="duration-info">
                                            <p class="duration">${practice.getDuration()}</p>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="question-info">
                                            <p class="questions">${practice.numQuestions} Questions</p>
                                            <p class="correct score">${practice.getNumberOfCorrectQuestions()} Correct</p>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="correct-info correct">
                                            <div class="percentage-square">
                                                <p class="percentage"><c:out value="${practice.getCorrectPercentage()}" /> %</p>
                                                <p class="status">Correct</p>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <a href="practicereview?record_id=${practice.quizId}" class="details-btn"><img src="./Assets/Images/Practice/View More.png" alt="Details"></a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${empty practices}">
                    <p>No practices available</p>
                </c:if>
            </section>
            <div class="pagination">
                <c:if test="${page > 1}">
                    <div class="button" onclick="handlePageClick(1)">
                        <div class="arrow"><<</div>
                    </div>
                    <div class="button" onclick="handlePageClick(${page - 1})">
                        <div class="arrow"><</div>
                    </div>
                </c:if>
                <div id="pagination" class="pages" data-current-page="${page}" data-total-pages="${pages_number}">
                    <c:forEach begin="1" end="${pages_number}" var="p">
                        <div class="${page == p ? 'current-page' : 'page'}" data-page="${p}">
                            <span class="${page == p ? 'current-page-number' : 'page-number'}"
                                  onclick="handlePageClick(${p})">
                                ${p}
                            </span>
                        </div>
                    </c:forEach>
                </div>
                <c:if test="${page < pages_number}">
                    <div class="button" onclick="handlePageClick(${page + 1})">
                        <div class="arrow">></div>
                    </div>
                    <div class="button" onclick="handlePageClick(${pages_number})">
                        <div class="arrow">>></div>
                    </div>
                </c:if>
            </div>
            <form id="form" action="practicelist" hidden>
                <input id="filter-input" name="filter" value="${filter}">
                <input id="search-input" name="search" value="${search}">
                <input id="sort-input" name="sort" value="${sort}">
                <input id="page-input" name="page" value="${page}">
            </form>
            <jsp:include page="../Common/FooterView.jsp"></jsp:include>
            <script src="./Assets/Js/Pagination.js"></script>
            <script src="./Assets/Js/PracticeList.js"></script>
            <script src="./Assets/Js/FormatDateTime.js"></script>
            <script src="./Assets/Js/ScoreFormatter.js"></script>
    </body>
</html>
