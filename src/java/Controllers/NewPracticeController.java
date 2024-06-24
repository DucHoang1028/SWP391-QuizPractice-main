package Controllers;

import DAOs.PracticeDAO;
import DAOs.SubjectDAO;
import Models.Subject;
import Models.Group;
import Models.User;
import Models.Quiz.Question;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NewPracticeController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(NewPracticeController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect("login"); // Redirect to login page if currentUser is not found
            return;
        }

        int userId = currentUser.getUser_id(); // Fetch the user_id from the currentUser object

        SubjectDAO subjectDAO = new SubjectDAO();
        List<Subject> subjects = subjectDAO.GetRegisteredSubjects(userId); // Get registered subjects for the user

        String subjectIdParam = request.getParameter("subject");
        String selectionTypeParam = request.getParameter("selectionType");

        int subjectId = subjectIdParam != null ? Integer.parseInt(subjectIdParam) : subjects.get(0).getSubject_id();
        String selectionType = selectionTypeParam != null ? selectionTypeParam : "topic";

        PracticeDAO practiceDAO = new PracticeDAO();
        List<Group> groups;
        if ("dimension".equals(selectionType)) {
            groups = practiceDAO.getDimensionsBySubjectId(subjectId);
        } else {
            groups = practiceDAO.getTopicsBySubjectId(subjectId);
        }

        request.setAttribute("registeredSubjects", subjects);
        request.setAttribute("groups", groups);
        request.setAttribute("selectedSubjectId", subjectId);
        request.setAttribute("selectedSelectionType", selectionType);

        request.getRequestDispatcher("Views/Practice/NewPracticeView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect("login"); // Redirect to login page if currentUser is not found
            return;
        }

        String action = request.getParameter("action");
        if ("create".equals(action)) {
            int userId = currentUser.getUser_id(); // Fetch the user_id from the currentUser object

            int subjectId = Integer.parseInt(request.getParameter("subject"));
            String examName = request.getParameter("examName");
            String numQuestionsStr = request.getParameter("numQuestions");
            int numQuestions;
            try {
                numQuestions = Integer.parseInt(numQuestionsStr);
                if (numQuestions <= 0) {
                    throw new NumberFormatException("Number of questions must be greater than zero.");
                }
            } catch (NumberFormatException e) {
                String errorMessage = "Invalid number of practicing questions. It must be a positive integer greater than zero.";
                request.setAttribute("error", errorMessage);
                doGet(request, response);
                return;
            }

            String selectionType = request.getParameter("selectionType");
            String questionGroup = request.getParameter("questionGroup");

            if (examName == null || examName.trim().isEmpty()) {
                String errorMessage = "Exam name cannot be empty or just whitespace.";
                request.setAttribute("error", errorMessage);
                doGet(request, response);
                return;
            }

            SubjectDAO subjectDAO = new SubjectDAO();
            if (!subjectDAO.CheckUserAccess(userId, subjectId)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have access to this subject.");
                return;
            }

            PracticeDAO practiceDAO = new PracticeDAO();
            List<Question> questions = new ArrayList<>();

            if ("all".equals(questionGroup)) {
                if ("topic".equals(selectionType)) {
                    questions = practiceDAO.getQuestionsBySubject(subjectId);
                } else if ("dimension".equals(selectionType)) {
                    questions = practiceDAO.getQuestionsBySubjectAndDimension(subjectId, -1); // Pass a dummy value as -1 for all dimensions
                }
            } else {
                int groupId = Integer.parseInt(questionGroup);
                if ("topic".equals(selectionType)) {
                    questions = practiceDAO.getQuestionsBySubjectAndTopic(subjectId, groupId);
                } else if ("dimension".equals(selectionType)) {
                    questions = practiceDAO.getQuestionsBySubjectAndDimension(subjectId, groupId);
                }
            }

            if (questions.size() < numQuestions) {
                String errorMessage = "Failed to create practice. Not enough questions available. Available questions: " + questions.size();
                request.setAttribute("error", errorMessage);
                doGet(request, response);
            } else {
                int quizId = practiceDAO.createPracticeAndGetQuizId(examName, subjectId, numQuestions, selectionType, questionGroup);

                if (quizId > 0) {
                    response.sendRedirect("quiz-handle?action=start&quiz_id=" + quizId);
                } else {
                    String errorMessage = "Failed to create practice. Unknown error occurred.";
                    request.setAttribute("error", errorMessage);
                    doGet(request, response);
                }
            }
        } else {
            // Reload the page to reflect the changed dropdown
            doGet(request, response);
        }
    }

}
