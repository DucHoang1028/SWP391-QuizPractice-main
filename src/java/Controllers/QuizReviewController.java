package Controllers;

import DAOs.QuizDAO;
import Models.Quiz.Answer;
import Models.Quiz.Question;
import Models.Quiz.Quiz;
import Models.Quiz.QuizRecord;
import Models.Quiz.RecordAnswer;
import Models.Quiz.RecordResult;
import Models.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;

public class QuizReviewController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /// Get curent information about this quiz from paramaters
        String record_id = request.getParameter("record_id");
        String order = request.getParameter("order");
        if (record_id != null && order != null) {
            /// get curent user is user logged in for double check
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("currentUser");
            if (currentUser != null) {
                /// if logged in and has authorization
                QuizDAO dao = new QuizDAO();
                QuizRecord currentRecord = dao.GetQuizRecordByRecordId(
                        Integer.parseInt(record_id));

                if (currentRecord == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    if (currentRecord.getUser_id() == currentUser.getUser_id()) {
                        /// if quiz record belong to current user handle next
                        Quiz currentQuiz = dao.GetQuizById(currentRecord.getQuiz_id());
                        Question currentQuestion = dao.GetQuestion(currentQuiz.getQuizId(), Integer.parseInt(order));
                        if (currentQuestion != null) {
                            HashMap<Integer, Answer> currentAnswerLi = ReProcessAnswer(dao
                                    .GetAnswersByQues(currentQuestion.getQuestionId()),
                                    currentRecord.getRecord_id(),
                                    currentQuestion.getQuestionId());
                            HashMap<Integer, RecordResult> recordres = dao
                                    .GetRecordResult(currentRecord.getRecord_id());
                            request.setAttribute("result", recordres);
                            request.setAttribute("resultsize", recordres.size());
                            if (recordres.size() % 10 == 0) {
                                request.setAttribute("resultsize2", (int) Math.floor((float) (recordres.size()) / 10));
                            } else {
                                request.setAttribute("resultsize2", (int) Math.floor((float) (recordres.size()) / 10) + 1);
                            }
                            boolean isMarked = dao.GetMarkQuestion(Integer.parseInt(record_id),
                                    currentQuestion.getQuestionId());
                            request.setAttribute("mark", isMarked);
                            request.setAttribute("correct", recordres.values().stream().filter(x -> x.getNumberOrCorrect() > 0).toList().size());
                            request.setAttribute("quiz", currentQuiz);
                            request.setAttribute("question", currentQuestion);
                            request.setAttribute("answer", currentAnswerLi);
                            request.setAttribute("order", order);
                            request.setAttribute("recordid", record_id);

                            // if the question type is fill in blank, set the record answer to the request attribute
                            if (currentQuestion.getTypeId() == Question.TYPE_FILL_BLANK) {
                                request.setAttribute("recordAnswer", dao.getRecordAnswerByQues(currentRecord.getRecord_id(), currentQuestion.getQuestionId()));
                            }

                            request.getRequestDispatcher("./Views/Customer/QuizReviewView.jsp").forward(request, response);
                        } else {
                            /// Some error occurs
                            response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        }
                    } else {
                        /// Some error occurs
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String record_id = request.getParameter("record_id");
        String question_id = request.getParameter("question_id");
        String order = request.getParameter("order");
        String action = request.getParameter("action");
        if (record_id != null && question_id != null && action != null) {
            if (action.equals("mark")) {
                QuizDAO dao = new QuizDAO();
                dao.AddMarkQuestion(Integer.parseInt(record_id), Integer.parseInt(question_id));
                response.sendRedirect("quiz-review?record_id=" + record_id + "&order=" + order);
            } else {
                QuizDAO dao = new QuizDAO();
                dao.DeleteRecordMark(Integer.parseInt(record_id), Integer.parseInt(question_id));
                response.sendRedirect("quiz-review?record_id=" + record_id + "&order=" + order);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private HashMap<Integer, Answer> ReProcessAnswer(HashMap<Integer, Answer> answers, int recordId, int questionId) {
        QuizDAO dao = new QuizDAO();
        HashMap<Integer, RecordAnswer> records = dao.GetRecordAnswersByQues(questionId, recordId);
        for (var key : records.keySet()) {
            Answer val = answers.get(key);
            System.out.println(key + "|" + questionId);
            if (val != null) {
                val.setIs_record(true);
                answers.put(key, val);
            }
        }
        return answers;
    }

}
