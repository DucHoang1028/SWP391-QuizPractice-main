
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="./Assets/Styles/Customer/quizreviewpopup.css"/>
    </head>
    <body>
        <div id="popup-container" class="popup-container">
            <div class="popup">
                <div class="popup__header">
                    <div class="popup__header-title">Review Progress</div>
                    <div class="popup__header-close" id="close_button"><svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 48 52" fill="none">
                        <path d="M35.9999 38.9999L24 26M24 26L12 13M24 26L36.0001 13M24 26L12 39.0001" stroke="#1D2026" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg></div>
                </div>
                <div class="popup__title-subtitle" id="question-filter-title">
                    All questions
                </div>
                <div class="popup__content">
                    <div class="popup__content-left">
                        <div class="popup__content-questions">
                            <c:forEach begin="1" end="${5}" varStatus="loop">
                                <div class="questions__row">

                                    <c:forEach varStatus="loop2" begin="${(loop.index*10-10)+1}" end="${(loop.index*10-10)+1+ 
                                                                          (requestScope.resultsize>=(loop.index*10)?9:
                                                                          (requestScope.resultsize-((loop.index-1)*10)-1)) }">

                                               <c:if test="${requestScope.result[loop2.index].numberOfAnswer != 0}">
                                                   <a href="quiz-handle?action=process&question=${loop2.index}">   
                                                       <div id="ques_${loop2.index}" class="questions__cell answered ${result[loop2.index].mark != 0 ? "marked": ""}">
                                                           <c:if test="${requestScope.result[loop2.index].mark != 0}">
                                                               <img src="./Assets/Images/Icons/bookmark.svg" alt="alt"/>
                                                           </c:if>
                                                           ${loop2.index}
                                                       </div> </a>
                                                   </c:if>
                                                   <c:if test="${requestScope.result[loop2.index].numberOrCorrect == 0
                                                                 && requestScope.result[loop2.index].numberOfAnswer == 0}">
                                                   <a href="quiz-handle?action=process&question=${loop2.index}">  
                                                       <div id="ques_${loop2.index}" class="questions__cell unanswered ${result[loop2.index].mark != 0 ? "marked": ""}">
                                                           <c:if test="${requestScope.result[loop2.index].mark != 0}">
                                                               <img src="./Assets/Images/Icons/bookmark.svg" alt="alt"/>
                                                           </c:if>   ${loop2.index}
                                                       </div></a> 
                                                   </c:if>
                                               </c:forEach>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="popup__content-right">
                        <div class="popup__right-explain">
                            <div class="popup__right-mark btnm" onclick="filterQuestions('marked')">
                                <img src="./Assets/Images/Icons/bookmark.svg" alt="alt"/>
                                <h1>Marked</h1>
                            </div>
                            <div class="popup__right-mark btnm" onclick="filterQuestions('unanswered')">
                                <div class="unan" ></div>
                                <h1>Unanswered</h1>
                            </div>
                            <div class="popup__right-mark btnm" onclick="filterQuestions('answered')">
                                <div class="unan4"></div>
                                <h1>Answered</h1>
                            </div>
                            <div class="popup__right-mark all-ques btnm" onclick="filterQuestions('all')">
                                <h1>All questions</h1>
                            </div>
                        </div>
                        <div class="popup__content-end" onclick="showPopup()">
                            Submit
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="submit-overlay" class="submit-overlay"></div>
        <div id="submit-popup" class="submit-popup">
            <div id="popupMessage"></div>
            <div class="confirm-actions">
                <div class="right_actions-previous btn" id="submit-action" onclick="exit()">Yes</div>
                <div class="right_actions-next btn" onclick="cancel()">Cancel</div>
            </div>

        </div>

    </body>
</html>
