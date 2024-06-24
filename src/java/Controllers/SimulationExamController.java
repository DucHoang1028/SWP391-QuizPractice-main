
    package Controllers;

    import DAOs.QuizDAO;
    import DAOs.SubjectDAO;
    import Models.Quiz.Quiz;
    import Models.Subject;
    import Models.User;
    import Utils.ValidationUtils;
    import java.io.IOException;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import jakarta.servlet.http.HttpSession;
    import java.util.ArrayList;
    import java.util.List;

    public class SimulationExamController extends HttpServlet {

        public static final int PAGE_LENGTH = 5;

        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("currentUser");

            if (user == null) {
                response.sendError(403);
            } else {
                int sort = 4;
                int subject_id = -1;
                int page = 1;
                String search_value = "";

                String raw_sort = request.getParameter("sort");
                String raw_subject_id = request.getParameter("subject_id");
                String raw_page = request.getParameter("page");
                String raw_search_value = request.getParameter("search_value");

                if (ValidationUtils.isValidInteger(raw_sort, 0, Integer.MAX_VALUE)) {
                    sort = Integer.parseInt(raw_sort);
                }
                if (ValidationUtils.isValidInteger(raw_subject_id, 0, Integer.MAX_VALUE)) {
                    subject_id = Integer.parseInt(raw_subject_id);
                }
                if (ValidationUtils.isValidInteger(raw_page, 0, Integer.MAX_VALUE)) {
                    page = Integer.parseInt(raw_page);
                }
                if (ValidationUtils.isNotNull(raw_search_value)) {
                    search_value = raw_search_value;
                }

                // get list of subjects that the user registered
                List<Subject> subjects = new SubjectDAO().GetRegisteredSubjects(user.getUser_id());

                // get list of exam paginated
                List<Quiz> exams = new ArrayList();
                List<Quiz> total_exams = new ArrayList();

                if (subject_id == -1){
                    // get array of subject_id
                    int[] subject_ids = new int[subjects.size()];
                    for (int i = 0; i < subjects.size(); i++) {
                        subject_ids[i] = subjects.get(i).getSubject_id();
                    }
                    exams = new QuizDAO().GetSimulationExams(search_value, subject_ids, sort, page, PAGE_LENGTH);
                    total_exams = new QuizDAO().GetSimulationExams(search_value, subject_ids, sort, 1, Integer.MAX_VALUE);
                }
                else {
                    exams = new QuizDAO().GetSimulationExams(search_value, new int[]{subject_id}, sort, page, PAGE_LENGTH);
                    total_exams = new QuizDAO().GetSimulationExams(search_value, new int[]{subject_id}, sort, 1, Integer.MAX_VALUE);
                }

                System.out.println("total exams: " + total_exams);

                // get total page
                int total_page = total_exams.size() / PAGE_LENGTH;
                if (total_exams.size() % PAGE_LENGTH != 0) {
                    total_page++;
                }

                for (Quiz exam : total_exams) {
                    System.out.println(exam.GetQuizRecordsByUserId(user.getUser_id()));
                }

                request.setAttribute("exams", exams);
                request.setAttribute("subjects", subjects);
                request.setAttribute("pages_number", total_page);
                request.setAttribute("page", page);
                request.setAttribute("sort", sort);
                request.setAttribute("subject_id", subject_id);
                request.setAttribute("search", search_value);

                request.getRequestDispatcher("./Views/Customer/SimulationExamView.jsp").forward(request, response);
            }
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            processRequest(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            processRequest(request, response);
        }

        @Override
        public String getServletInfo() {
            return "Short description";
        }// </editor-fold>

    }
