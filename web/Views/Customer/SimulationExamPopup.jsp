
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="./Assets/Styles/Customer/explain.css"/>
        <link rel="stylesheet" href="./Assets/Styles/Customer/exam-popup.css"/>
    </head>
    <body>
        <div class="explain" id="explain-container">
            <div class="wrapper">
                <div class="explain__popup">
                    <div class="explain__popup-title">
                        <div class="explain__popup-left">
                        </div>
                        <div class="explain__popup-right" id="close_button2">
                            <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 48 52" fill="none">
                            <path d="M35.9999 38.9999L24 26M24 26L12 13M24 26L36.0001 13M24 26L12 39.0001" stroke="#1D2026" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </div>                   
                    </div>
                    <div class="explain__title">
                        START SIMULATION EXAM
                    </div>
                    <div class="exam-detail">
                        <div class="exam-detail-half">
                            <p>Subject : <strong id="subject-name">Math</strong></p>
                            <p>Exam : <strong id="exam-name"></strong></p>
                            <p>Duration : <strong id="duration"></strong></p>
                        </div>
                        <div class="exam-detail-half">
                            <p>Level : <strong class="colored-text" id="level">Medium</strong></p>
                            <p>Question number : <strong id="num-questions">20 questions</strong></p>
                            <p>Pass rate : <strong class="colored-text" id="pass-rate">40%</strong></p>
                        </div>
                    </div>
                    <div class="explain__title">
                        Recent Attempts
                    </div>
                    <div class="table">
                        <table>
                            <thead>
                            <th>Started On</th>
                            <th>Score</th>
                            <th>Time Taken</th>
                            <th>Status</th>
                            <th>Action</th>
                            </thead>

                            <tbody id="quiz-records">
                                <tr>
                                    <td>
                                        2024 Jun 16th <br/> 3:53 PM
                                    </td>
                                    <td>
                                        <div class="correct-info correct">
                                            <div class="percentage-square">
                                                <p class="percentage" hidden>
                                                    50 %
                                                </p>
                                                <p class="questions">
                                                    50
                                                </p>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        6 Mins 2 Secs
                                    </td>
                                    <td>
                                        <div class="correct-info correct">
                                            <div class="percentage-square">
                                                <p class="percentage" hidden>
                                                    100 %
                                                </p>
                                                <p class="questions">
                                                    Passed
                                                </p>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="btn view-btn">
                                            <p class="questions">
                                                View
                                            </p>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>

                        </table>
                    </div>

                    <div class="start-exam">
                        <a class="btn view-btn" id="start-quiz">
                            <p>Start Now</p>
                        </a>
                    </div>

                </div>
            </div>
        </div>
    </body>
</html>
