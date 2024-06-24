/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.QuizDAO;
import Models.Quiz.Answer;
import Models.Quiz.Question;
import Models.Quiz.Quiz;
import Models.Quiz.QuizRecord;
import Models.Quiz.RecordAnswer;
import Models.Quiz.RecordResult;
import Models.Role;
import Models.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;

public class QuizHandleController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        // if not logged in customer account, cannot do the quiz
        if (currentUser == null || currentUser.getRole() != Role.ROLE_CUSTOMER) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {

            try {
                // get parameters
                String action = request.getParameter("action");

                // if action = start, start a new quiz record, initialize a session timer to calculate the time
                switch (action) {
                    case "start" -> {
                        StartRecord(request, response);
                    }
                    case "process" -> {
                        ProcessRecord(request, response);
                    }
                    case "submit" -> {
                        SubmitRecord(request, response);
                    }
                    case "exit" -> {
                        Exit(request, response);
                    }
                    default -> {
                        throw new Exception();
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String record_id = request.getParameter("record_id");
        String question_id = request.getParameter("question_id");
        String order = request.getParameter("order");
        String action = request.getParameter("mark");
        if (record_id != null && question_id != null && action != null) {
            if (action.equals("1")) {
                QuizDAO dao = new QuizDAO();
                dao.AddMarkQuestion(Integer.parseInt(record_id), Integer.parseInt(question_id));
                response.sendRedirect("quiz-handle?action=process&question=" + order);
            } else {
                QuizDAO dao = new QuizDAO();
                dao.DeleteRecordMark(Integer.parseInt(record_id), Integer.parseInt(question_id));
                response.sendRedirect("quiz-handle?action=process&question=" + order);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void ProcessRecord(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            int question_no = Integer.parseInt(request.getParameter("question"));
            int prev = 0;
            if (request.getParameter("prev") != null) {
                prev = Integer.parseInt(request.getParameter("prev"));
            }
            int changed = 0;
            if (request.getParameter("changed") != null) {
                changed = Integer.parseInt(request.getParameter("changed"));
            }
            String answer = request.getParameter("answer");
            HttpSession session = request.getSession();
            QuizRecord qr = (QuizRecord) session.getAttribute("quizRecord");
            Quiz quiz = (Quiz) session.getAttribute("quiz");

            // update the record with the prev question
            if (prev != 0 && changed == 1) {
                Question prev_question = GetQuestion(quiz.getQuizId(), prev);
                // if question type is single choice then param answer is id of one answer
                // if question type is multiple choice then param answer is id of multiple answers
                // if question type is fill blank then param answer is the answer string
                // so we need to check the question type to update the record
                switch (prev_question.getTypeId()) {
                    case Question.TYPE_SINGLE_CHOICE -> {
                        if (answer.length() == 0) {
                            new QuizDAO().DeleteAnswer(qr.getRecord_id(), prev_question.getQuestionId());
                        } else {
                            Answer ans = GetAnswersByQues(prev_question.getQuestionId()).get(Integer.valueOf(answer));
                            UpdateAnswer(qr, prev_question.getQuestionId(), ans.getAnswerId());
                        }
                    }
                    case Question.TYPE_MULTIPLE_CHOICE -> {
                        if (answer.length() == 0) {
                            new QuizDAO().DeleteAnswer(qr.getRecord_id(), prev_question.getQuestionId());
                        } else {
                            String[] answers = answer.split(",");
                            // convert the string array to int array
                            int[] answer_ids = new int[answers.length];
                            for (int i = 0; i < answers.length; i++) {
                                answer_ids[i] = Integer.parseInt(answers[i]);
                            }
                            UpdateAnswer(qr, prev_question.getQuestionId(), answer_ids);
                        }

                    }
                    case Question.TYPE_FILL_BLANK -> {
                        UpdateAnswer(qr, prev_question.getQuestionId(), answer);
                    }
                }
            }

            // check timer, if time is up, or if action is submit, submit the record
            if (isTimeUp(quiz.getDuration(), qr.getCreated_at())
                    || "submit".equals(request.getParameter("action"))) {

                // if quiz type not practice, then submit the record
                if (quiz.getQuizType() == 2) {
                    SubmitRecord(request, response);
                    response.sendRedirect("home");
                    return;
                }
            }

            // get the question and answers by the order
            Question question = GetQuestion(quiz.getQuizId(), question_no);
            HashMap<Integer, Answer> answers = GetAnswersByQues(question.getQuestionId());

            // check question mark status
            boolean isMarked = new QuizDAO().GetMarkQuestion(qr.getRecord_id(), question.getQuestionId());

            // get the record answer of the question
            HashMap<Integer, RecordAnswer> recordAnswers = GetRecordAnswersByQues(question.getQuestionId(), qr.getRecord_id());

            // helper for review progress popup
            HashMap<Integer, RecordResult> recordres = new QuizDAO()
                    .GetRecordResult(qr.getRecord_id());
            request.setAttribute("result", recordres);
            request.setAttribute("resultsize", recordres.size());

            // set the question and answers to the request attribute
            request.setAttribute("mark", isMarked);
            request.setAttribute("question", question);
            request.setAttribute("answers", answers);
            request.setAttribute("recordAnswers", recordAnswers);
            request.setAttribute("question_no", question_no);

            // if the question type is fill in blank, set the record answer to the request attribute
            if (question.getTypeId() == Question.TYPE_FILL_BLANK) {
                request.setAttribute("recordAnswer", new QuizDAO().getRecordAnswerByQues(qr.getRecord_id(), question.getQuestionId()));
            }

            // forward to the quiz page
            request.getRequestDispatcher("./Views/Customer/QuizHandleView.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println(e);
            System.out.println(((QuizRecord) request.getSession().getAttribute("quizRecord")).getRecord_id());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    private void SubmitRecord(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // get the record and submit
            HttpSession session = request.getSession();
            QuizRecord qr = (QuizRecord) session.getAttribute("quizRecord");
            Quiz quiz = (Quiz) session.getAttribute("quiz");

            QuizDAO dao = new QuizDAO();
            dao.SubmitRecord(qr.getRecord_id(), quiz.getQuizId());
            // remove the session attributes 
            session.removeAttribute("quizRecord");
            session.removeAttribute("quiz");
            session.removeAttribute("seed");

            request.setAttribute("record_id", qr.getRecord_id());
            request.getRequestDispatcher("./Views/Customer/QuizResultView.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void StartRecord(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("currentUser");

            String raw_quiz_id = request.getParameter("quiz_id");

            int user_id = currentUser.getUser_id();
            int quiz_id = Integer.parseInt(raw_quiz_id);

            // check if user have access to the quiz
            if (!CheckUserAccess(user_id, quiz_id)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                QuizRecord qr = CreateNewRecord(user_id, quiz_id);
                Quiz quiz = GetQuizById(quiz_id);
                // random number to be seed
                int seed = (int) (Math.random() * 1000);

                session.setAttribute("quizRecord", qr);
                session.setAttribute("quiz", quiz);
                session.setAttribute("seed", seed);

                response.sendRedirect("quiz-handle?action=process&question=1");
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getStackTrace());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    private Quiz GetQuizById(int quiz_id) {
        QuizDAO dao = new QuizDAO();
        return dao.GetQuizById(quiz_id);
    }

    private Question GetQuestion(int quiz_id, int question_id) {
        QuizDAO dao = new QuizDAO();
        return dao.GetQuestion(quiz_id, question_id);
    }

    private boolean CheckUserAccess(int user_id, int quiz_id) {
        QuizDAO dao = new QuizDAO();
        return dao.CheckUserAccess(user_id, quiz_id);
    }

    private QuizRecord CreateNewRecord(int user_id, int quiz_id) {
        QuizDAO quizDAO = new QuizDAO();
        return quizDAO.CreateNewRecord(user_id, quiz_id);
    }

    private HashMap<Integer, Answer> GetAnswersByQues(int questionId) {
        QuizDAO dao = new QuizDAO();
        return dao.GetAnswersByQues(questionId);
    }

    private void UpdateAnswer(QuizRecord qr, int questionId, int answerId) {
        QuizDAO dao = new QuizDAO();
        dao.UpdateAnswer(qr.getRecord_id(), questionId, answerId);

    }

    private void UpdateAnswer(QuizRecord qr, int questionId, String answer) {
        QuizDAO dao = new QuizDAO();
        dao.UpdateAnswer(qr.getRecord_id(), questionId, answer);
    }

    private void UpdateAnswer(QuizRecord qr, int questionId, int[] answer_ids) {
        QuizDAO dao = new QuizDAO();
        dao.UpdateAnswer(qr.getRecord_id(), questionId, answer_ids);
    }

    private boolean isTimeUp(int duration, LocalDateTime created_at) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(created_at.plusMinutes(duration));
    }

    private HashMap<Integer, RecordAnswer> GetRecordAnswersByQues(int questionId, int recordId) {
        QuizDAO dao = new QuizDAO();
        return dao.GetRecordAnswersByQues(questionId, recordId);
    }

    private void Exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // get the record and submit
            HttpSession session = request.getSession();
            QuizRecord qr = (QuizRecord) session.getAttribute("quizRecord");
            Quiz quiz = (Quiz) session.getAttribute("quiz");

            QuizDAO dao = new QuizDAO();
            dao.SubmitRecord(qr.getRecord_id(), quiz.getQuizId());
            // remove the session attributes 
            session.removeAttribute("quizRecord");
            session.removeAttribute("quiz");
            session.removeAttribute("seed");
            if (quiz.getQuizType() == 2)
                response.sendRedirect("simulation-exam");
            else {
                response.sendRedirect("practicelist");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
