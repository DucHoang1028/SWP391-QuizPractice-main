<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Simulation Exam</title>
        <link rel="stylesheet" href="./Assets/Styles/Practice/PracticeList.css">
        <link rel="stylesheet" href="./Assets/Styles/main.css">
        <link rel="stylesheet" href="./Assets/Styles/Customer/simulation-exam.css">
        <link rel="icon" href="./Assets/Images/Common/logo.ico">

    </head>
    <body>
        <!-- Set current page variable -->
        <c:set scope="request" var="currentPage" value="Practices"/>
        <jsp:include page="../Customer/SimulationExamPopup.jsp"></jsp:include>
        <!-- Include the header view -->
        <jsp:include page="../Common/HeaderView.jsp"></jsp:include>
        
            <main class="main-content">
                <header class="header">
                    <!-- Filter and sort sections are grouped together for better vertical alignment -->
                    <div class="filter-sort-area">
                        <div class="filter-by">
                            <label for="filter">Filter by:</label>
                            <select id="filter" name="filter" onchange="handleFilterChange()">
                                <option value="-1" ${subject_id == '-1' ? 'selected' : ''}>All Subjects</option>
                            <c:forEach items="${subjects}" var="subject">
                                <option value="${subject.getSubject_id()}" ${subject_id == subject.getSubject_id() ? 'selected' : ''}>${subject.getTitle()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="sort-by">
                        <label for="sort">Sort by:&nbsp</label>
                        <select id="sort" name="sort" onchange="handleSortChange()">
                            <option value="1" ${sort == '1' ? 'selected' : ''}>Recently Added</option>
                            <option value="2" ${sort == '2' ? 'selected' : ''}>Title A-Z</option>
                            <option value="3" ${sort == '3' ? 'selected' : ''}>Highest Pass rate</option>
                            <option value="4" ${sort == '4' ? 'selected' : ''}>Level (easy to hard)</option>
                        </select>
                    </div>
                </div>
                <div class="controls">
                    <div class="practice-controls">
                        <div class="actions">
                            <a href="practicelist" class="btn">Practice List</a>
                            <div class="search-container">
                                <input type="text" class="search-input" placeholder="Search exam by title" id="search" name="search" value="${search}">
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
                <table>
                    <thead>
                        <tr>
                            <th>Subject</th>
                            <th>Simulation Exam</th>
                            <th>Level</th>
                            <th>#Questions</th>
                            <th>Duration</th>
                            <th>Pass Rate</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="exam" items="${exams}">
                            <tr class="practice-item" onclick="showPopup(
                                        `${exam.getQuizId()}`,
                                        `${exam.getSubjectName()}`, 
                                        `${exam.getName()}`, `${exam.getDuration()}`, `${exam.getLevel()}`, 
                                        `${exam.getNumQuestions()}`, `${exam.getPassRate()}`, 
                                        `${exam.GetQuizRecordsByUserId(currentUser.getUser_id())}`)">
                                <td>
                                    <div class="practice-title">
                                        <p class="questions">${exam.getSubjectName()}</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="practice-title">
                                        <p class="questions">${exam.getName()}</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="correct-info correct">
                                        <div class="percentage-square">
                                            <p class="percentage" hidden>
                                                ${1 / exam.getLevel() * 100}
                                            </p>
                                            <p class="questions">
                                                ${exam.getLevel() == 1 ? "Easy" : exam.getLevel() == 2 ? "Medium" : "Hard" }
                                            </p>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="practice-title">
                                        <p class="questions">${exam.numQuestions} Questions</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="question-info">
                                        <p class="questions">${exam.getDuration()} Mins</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="correct-info correct">
                                        <div class="percentage-square">
                                            <p class="percentage" hidden>
                                                ${exam.getPassRate()} %
                                            </p>
                                            <p class="questions">
                                                ${exam.getPassRate()} %
                                            </p>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </section>
            <br/><!-- comment -->
            <br/><!-- comment -->
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
        </main>
        <!-- Hidden form for handling searches and pagination -->
        <form id="form" action="simulation-exam" hidden>
            <input id="filter-input" name="subject_id" value="${subject_id}">
            <input id="search-input" name="search_value" value="${search}">
            <input id="sort-input" name="sort" value="${sort}">
            <input id="page-input" name="page" value="${page}">
        </form>
        <jsp:include page="../Common/FooterView.jsp"></jsp:include> 
        <script src="./Assets/Js/Pagination.js"></script>
        <script src="./Assets/Js/simulation-exam.js"></script>
        <script src="./Assets/Js/ScoreFormatter.js"></script> 
    </body>
</html>
