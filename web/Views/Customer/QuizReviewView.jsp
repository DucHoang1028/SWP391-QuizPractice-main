
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" href="./Assets/Images/Common/logo.ico">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./Assets/Styles/Customer/quizreview.css"/>
        <link rel="stylesheet" href="./Assets/Styles/Normalize.css"/>
        <script src="./Assets/Js/Common/jquery.min.js"></script>
        <script src="./Assets/Js/Customer/Popup.js"></script>

        <title>Quiz Review</title>
    </head>
    <body>
        <jsp:include page="./QuizReviewPopup.jsp"></jsp:include> 
        <jsp:include page="./ExplainPopup.jsp"></jsp:include> 

            <div class="quiz_main">
                <div class="content">
                    <div class="leftpanel">
                        <h2 class="leftpanel__title">Question ${requestScope.order}</h2>
                    <form action="quiz-review" method="POST">
                        <input type="hidden"value="${requestScope.recordid}" name="record_id"/> 
                        <input type="hidden"value="${requestScope.order}" name="order"/> 
                        <c:if test="${requestScope.mark == true}">
                            <input type="hidden"value="unmark" name="action"/> 
                        </c:if>
                        <c:if test="${requestScope.mark != true}">
                            <input type="hidden"value="mark" name="action"/> 
                        </c:if>
                        <input type="hidden"value="${requestScope.question.questionId}" name="question_id"/> 
                        <button class="leftpanel__butoon-mark" type="submit">

                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
                            <path d="M5.58579 3.58579C5.21071 3.96086 5 4.46957 5 5V21L12 17.5L19 21V5C19 4.46957 18.7893 3.96086 18.4142 3.58579C18.0391 3.21071 17.5304 3 17 3H7C6.46957 3 5.96086 3.21071 5.58579 3.58579Z" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                            <c:if test="${requestScope.mark == true}">
                                <p>Unmark Question</p>
                            </c:if>
                            <c:if test="${requestScope.mark != true}">
                                <p>Mark Question</p>
                            </c:if>
                        </button>
                        <div class="leftpanel__butoon-explain" id="explain-btn">View Explanation</div>
                    </form>
                </div>
                <div class="rightpanel">
                    <div class="rightpanel__question">
                        <p>${requestScope.question.content}</p>
                        <c:if test="${question.getTypeId() != 3}">
                            <p>Select ${question.getNumOfAnswers()}: </p>
                        </c:if>
                    </div>

                    <div class="rightpanel__answers">
                        <c:if test="${question.getTypeId() != 3}">
                            <c:forEach var="item" items="${requestScope.answer}" >
                                <c:if test="${item.value.is_correct == true}">
                                    <div class="rightpanel__right-answer">
                                        <div class="answer"><svg xmlns="http://www.w3.org/2000/svg" width="31" height="28" viewBox="0 0 31 28" fill="none">
                                            <rect width="28" height="28" rx="7" fill="#1BBA51"/>
                                            <path d="M8.15625 14.846C8.84561 15.9365 9.94541 16.62 10.8399 17.5297C11.0775 17.7713 11.3179 18.1874 11.702 18.1001C11.9829 18.0363 12.2109 17.7063 12.4086 17.5297C13.3779 16.6635 14.409 15.8582 15.4293 15.0535C17.1859 13.6681 18.9896 12.3427 20.7253 10.9308C21.3246 10.4432 21.9158 9.95525 22.5662 9.53711" stroke="#FBF9F9" stroke-width="3" stroke-linecap="round"/>
                                            </svg>
                                            <label>${item.value.content}</label>
                                        </div>
                                        <c:if test="${item.value.is_record == true}">
                                            <div class="correct"> Your correct answer</div> 
                                        </c:if>
                                        <c:if test="${item.value.is_record != true}">
                                            <div class="correct"> Correct answer</div> 
                                        </c:if> 
                                    </div>
                                </c:if>
                                <c:if test="${item.value.is_correct != true && item.value.is_record == true}">
                                    <div class="rightpanel__right-answer">
                                        <div class="answer"><svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 31 28" fill="none">
                                            <rect width="31" height="28" rx="7" fill="#FF0000"/>
                                            <path d="M8.15625 14.846C8.84561 15.9365 9.94541 16.62 10.8399 17.5297C11.0775 17.7713 11.3179 18.1874 11.702 18.1001C11.9829 18.0363 12.2109 17.7063 12.4086 17.5297C13.3779 16.6635 14.409 15.8582 15.4293 15.0535C17.1859 13.6681 18.9896 12.3427 20.7253 10.9308C21.3246 10.4432 21.9158 9.95525 22.5662 9.53711" stroke="#FBF9F9" stroke-width="3" stroke-linecap="round"/>
                                            </svg>
                                            <label>${item.value.content}</label>
                                        </div>
                                        <div class="wrong">Your wrong answer</div>
                                    </div>


                                </c:if>
                                <c:if test="${item.value.is_correct != true && item.value.is_record != true}">

                                    <div class="rightpanel__right-answer">
                                        <div class="answer">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="31" height="28" viewBox="0 0 31 28" fill="none">
                                            <rect x="0.5" y="0.5" width="28" height="28" rx="6.5" fill="white" stroke="black"/>
                                            </svg>
                                            <label>${item.value.content}</label>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <c:if test="${question.getTypeId() == 3}">
                            <c:forEach var="item" items="${requestScope.answer}" >
                                <div class="rightpanel__right-answer">
                                    <div class="answer">
                                        <label>${item.value.content}</label>
                                    </div>
                                    <div class="correct"> Correct answer</div> 
                                </div>
                                <input class="answer-input" type="text" 
                                       value="${recordAnswer.getContent()}"
                                       disabled
                                       />
                                <div class="${recordAnswer.getContent() eq item.value.content ? "correct": "wrong"}" > Your answer</div> 
                            </c:forEach>
                        </c:if>

                    </div>
                </div>
            </div>
            <div class="actions">
                <div class="left__actions">
                    <div class="left__actions-review" id="review-btn">Review Results</div>
                </div>
                <div class="right_actions">
                    <c:if test="${requestScope.order != 1}">
                        <a href="quiz-review?record_id=${requestScope.recordid}&order=${requestScope.order-1}"><div class="right_actions-previous">Previous Question</div></a>
                    </c:if>
                    <c:if test="${requestScope.order != requestScope.resultsize}">

                        <a href="quiz-review?record_id=${requestScope.recordid}&order=${requestScope.order+1}"><div class="right_actions-next">Next Question</div>
                        </a>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
