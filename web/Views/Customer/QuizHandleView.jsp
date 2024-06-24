<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="icon" href="./Assets/Images/Common/logo.ico">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" href="./Assets/Styles/Customer/quizreview.css" />
            <link rel="stylesheet" href="./Assets/Styles/Customer/quizhandle.css" />
            <link rel="stylesheet" href="./Assets/Styles/Normalize.css" />
            <title>Quiz Handle</title>
        </head>

        <body>
            <jsp:include page="./QuizHandlePopup.jsp"></jsp:include>
            <jsp:include page="./ExplainPopup.jsp"></jsp:include>
            <div class="quiz_main">
                <div class="content">
                    <div class="leftpanel">
                        <h2 class="leftpanel__title">Question ${question_no}</h2>
                        <form id="form-post" action="quiz-handle" method="POST">
                            <input type="hidden" value="${quizRecord.getRecord_id()}" name="record_id" id="record_id" />
                            <input type="hidden" value="${question_no}" name="order" id="order" />
                            <c:if test="${requestScope.mark == true}">
                                <input type="hidden" value="0" name="mark" />
                            </c:if>
                            <c:if test="${requestScope.mark != true}">
                                <input type="hidden" value="1" name="mark" />
                            </c:if>
                            <input type="hidden" value="${question.questionId}" name="question_id" />

                        </form>
                        <button class="leftpanel__butoon-mark" onclick="Afetch()">

                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                fill="none">
                                <path
                                    d="M5.58579 3.58579C5.21071 3.96086 5 4.46957 5 5V21L12 17.5L19 21V5C19 4.46957 18.7893 3.96086 18.4142 3.58579C18.0391 3.21071 17.5304 3 17 3H7C6.46957 3 5.96086 3.21071 5.58579 3.58579Z"
                                    stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
                            </svg>
                            <c:if test="${requestScope.mark == true}">
                                <p>Unmark Question</p>
                            </c:if>
                            <c:if test="${requestScope.mark != true}">
                                <p>Mark Question</p>
                            </c:if>
                        </button>
                        <div class="leftpanel__butoon-explain btn" id="explain-btn" ${quiz.getQuizType()==2 ? "hidden"
                            : "" }>Peek at Answer</div>
                    </div>
                    <div class="rightpanel">
                        <div class="rightpanel__question">
                            <div class="question_content">
                                <p>${question.getContent()}</p>
                                <c:if test="${question.getTypeId() != 3}">
                                    <p>Select ${question.getNumOfAnswers()}: </p>
                                </c:if>
                                
                                <br/>
                            </div>
                            <div class="timer">
                                ${quiz.getQuizType() == 2 ? "Time remaining:" : "Time used:"}
                                <span id="quiz-timer">00:00:00</span>
                            </div>
                        </div>
                        <div class="rightpanel__answers" id="answer-list">
                            <c:forEach items="${answers}" var="ans">
                                <div class="rightpanel__right-answer">
                                    <div class="answer">
                                        <c:choose>
                                            <c:when test="${question.getTypeId() == 1}">
                                                <input class="answer-input" type="radio" name="radio-answer-input"
                                                    id="${ans.getValue().getAnswerId()}"
                                                    value="${ans.getValue().getAnswerId()}" onchange="updateRadio()"
                                                    ${recordAnswers.get(ans.getValue().getAnswerId()) !=null ? "checked"
                                                    : "" } />
                                                <label
                                                    for="${ans.getValue().getAnswerId()}">${ans.getValue().getContent()}</label>
                                            </c:when>
                                            <c:when test="${question.getTypeId() == 2}">
                                                <input class="answer-input" type="checkbox"
                                                    id="${ans.getValue().getAnswerId()}"
                                                    value="${ans.getValue().getAnswerId()}" onchange="updateCheckbox()"
                                                    ${recordAnswers.get(ans.getValue().getAnswerId()) !=null ? "checked"
                                                    : "" } />
                                                <label
                                                    for="${ans.getValue().getAnswerId()}">${ans.getValue().getContent()}</label>
                                            </c:when>
                                            <c:when test="${question.getTypeId() == 3}">
                                                <input class="answer-input" type="text" placeholder="Fill in the blank"
                                                    id="${ans.getValue().getAnswerId()}" onblur="updateText()"
                                                    value="${recordAnswer.getContent()}" />
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>
                            </c:forEach>


                        </div>

                    </div>
                </div>
                <div class="actions">
                    <div class="left__actions">
                        <div onclick="fetchAnswer()" class="left__actions-review btn" id="review-btn" >Review Progress</div>
                    </div>
                    <div class="right_actions">
                        <div class="right_actions-previous btn" onclick="prevQuestion(${question_no})" ${question_no==1
                            ? "hidden" : "" }>Previous Question</div>
                        <div class="right_actions-next btn" onclick="nextQuestion(${question_no})"
                            ${question_no==quiz.getNumQuestions() ? "hidden" : "" }>Next Question</div>
                    </div>
                </div>
            </div>
        </body>
        <script src="./Assets/Js/QuizHandle.js"></script>
        <script src="./Assets/Js/Common/jquery.min.js"></script>
        <script src="./Assets/Js/Customer/Popup.js"></script>
        <c:choose>
            <c:when test="${quiz.getQuizType() == 2}">
                <script>
                    calculateTime(${ quiz.getDuration() }, '${quizRecord.getCreated_at()}');
                    setInterval(() => {
                        calculateTime(${ quiz.getDuration() }, '${quizRecord.getCreated_at()}');
                    }, 200);
                </script>
            </c:when>
            <c:otherwise>
                <script>
                    countUp('${quizRecord.getCreated_at()}');
                    setInterval(() => {
                        countUp('${quizRecord.getCreated_at()}');
                    }, 200);
                </script>
            </c:otherwise>

        </c:choose>

        <!-- hidden form -->
        <form action="quiz-handle" id="quiz-form" hidden>
            <input type="hidden" name="action" id="action" value="process" />
            <input type="hidden" name="question" id="question" value="${question_no}" />
            <input type="hidden" id="prev" value="${question_no}" />
            <input type="hidden" id="changed" value="0" />
            <input type="hidden" id="record-answer" value="" />
        </form>

        </html>