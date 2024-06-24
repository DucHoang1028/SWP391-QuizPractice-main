package DAOs;

import Contexts.DBContext;
import Models.Group;
import Models.Practice;
import Models.Quiz.Question;
import Utils.ValidationUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PracticeDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(PracticeDAO.class.getName());

    // Fetch practices with pagination, filter, sort, and search
    public List<Practice> GetPractices(int limit, int offset, String filter, String sort, String search, int user_id) {
    boolean filter_by_subject = ValidationUtils.isValidInteger(filter, 0, Integer.MAX_VALUE);

    List<Practice> practices = new ArrayList<>();
    StringBuilder sqlCommand = new StringBuilder(
            "SELECT r.record_id, "
            + "       s.title AS subject_name, "
            + "       q.name AS exam_name, "
            + "       CAST(q.description AS NVARCHAR(MAX)) AS keyword, "
            + "       r.created_at AS exam_date, "
            + "       r.finished_at AS finished_time, "
            + "       q.num_questions, "
            + "       COALESCE(r.score, 0) AS score, "
            + "       q.quiz_type, "
            + "       CASE "
            + "         WHEN q.quiz_type = 1 THEN st.name "
            + "         WHEN q.quiz_type = 3 THEN sd.name "
            + "         ELSE NULL "
            + "       END AS type_keyword_name "
            + "FROM Quiz q "
            + "JOIN Subject s ON q.subject_id = s.subject_id "
            + "LEFT JOIN SubjectTopic st ON q.quiz_type = 1 AND CAST(q.description AS NVARCHAR(MAX)) = CAST(st.id AS NVARCHAR(MAX)) "
            + "LEFT JOIN SubjectDimension sd ON q.quiz_type = 3 AND CAST(q.description AS NVARCHAR(MAX)) = CAST(sd.dimension_id AS NVARCHAR(MAX)) "
            + "INNER JOIN QuizRecord r ON q.quiz_id = r.quiz_id "
            + "WHERE (q.quiz_type = ? OR q.quiz_type = ?) "
            + "AND (q.name LIKE ? OR s.title LIKE ?) "
            + "AND r.finished_at IS NOT NULL "
            + "AND q.created_at IS NOT NULL " // Exclude records with null created_at
            + "AND r.user_id = ? "
            + (filter_by_subject ? "AND q.subject_id = " + filter : "")
            + "ORDER BY " + (sort.equals("title") ? "q.name ASC" : sort.equals("correct") ? "r.score DESC" : "q.created_at DESC")
            + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY"
    );

    try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sqlCommand.toString())) {
        ps.setInt(1, 1); // topic
        ps.setInt(2, 3); // dimension
        ps.setString(3, "%" + search + "%");
        ps.setString(4, "%" + search + "%");
        ps.setInt(5, user_id);
        ps.setInt(6, offset);
        ps.setInt(7, limit);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Practice practice = new Practice();
            practice.setQuizId(rs.getInt("record_id"));
            practice.setSubjectName(rs.getString("subject_name"));
            practice.setExamName(rs.getString("exam_name"));
            practice.setKeyword(rs.getString("keyword"));
            practice.setExamDate(rs.getTimestamp("exam_date").toLocalDateTime());
            practice.setFinishedTime(rs.getTimestamp("finished_time").toLocalDateTime());
            practice.setNumQuestions(rs.getInt("num_questions"));
            practice.setScore(rs.getFloat("score")); // Use the score directly as the percentage
            practice.setQuizType(rs.getInt("quiz_type"));
            practice.setType(rs.getString("type_keyword_name"));

            practices.add(practice);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return practices;
}

    public int GetResultLength(String filter, String search, int user_id) {
        boolean filter_by_subject = ValidationUtils.isValidInteger(filter, 0, Integer.MAX_VALUE);
        int resultLength = 0;

        StringBuilder sqlCommand = new StringBuilder(
                "SELECT COUNT(*) AS result_length "
                + "FROM Quiz q "
                + "JOIN Subject s ON q.subject_id = s.subject_id "
                + "INNER JOIN QuizRecord r ON q.quiz_id = r.quiz_id "
                + "WHERE (q.quiz_type = ? OR q.quiz_type = ?) "
                + "AND (q.name LIKE ? OR s.title LIKE ?) "
                + "AND r.finished_at IS NOT NULL "
                + "AND q.created_at IS NOT NULL "
                + "AND r.user_id = ? "
                + (filter_by_subject ? "AND q.subject_id = " + filter : "")
        );

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sqlCommand.toString())) {
            ps.setInt(1, 1); // topic
            ps.setInt(2, 3); // dimension
            ps.setString(3, "%" + search + "%");
            ps.setString(4, "%" + search + "%");
            ps.setInt(5, user_id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                resultLength = rs.getInt("result_length");
            }
        } catch (SQLException ex) {
            System.out.println("Errors occur in get result length DAO: " + ex.getMessage());
        }
        return resultLength;
    }

    public Practice GetPracticeById(int quizId) {
        Practice practice = null;
        String sqlCommand = "SELECT r.record_id, s.title AS subject_name, q.name AS exam_name, q.description AS keyword, "
                + "r.created_at AS exam_date, r.finished_at AS finished_time, q.num_questions, COALESCE(r.score, 0) AS score, q.quiz_type, "
                + "CASE "
                + "  WHEN q.quiz_type = 1 THEN st.name "
                + "  WHEN q.quiz_type = 3 THEN sd.name "
                + "  ELSE NULL "
                + "END AS type_keyword_name "
                + "FROM Quiz q "
                + "JOIN Subject s ON q.subject_id = s.subject_id "
                + "LEFT JOIN SubjectTopic st ON q.quiz_type = 1 AND CAST(q.description AS NVARCHAR(MAX)) = CAST(st.id AS NVARCHAR(MAX)) "
                + "LEFT JOIN SubjectDimension sd ON q.quiz_type = 3 AND CAST(q.description AS NVARCHAR(MAX)) = CAST(sd.dimension_id AS NVARCHAR(MAX)) "
                + "INNER JOIN QuizRecord r ON q.quiz_id = r.quiz_id "
                + "WHERE r.record_id = ? "
                + "AND r.finished_at IS NOT NULL";  // Add this condition

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sqlCommand)) {
            ps.setInt(1, quizId);

            System.out.println("Executing get practice by ID query: " + ps.toString()); // Debugging statement

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                practice = new Practice();
                practice.setQuizId(rs.getInt("record_id")); // Changed to record_id
                practice.setSubjectName(rs.getString("subject_name"));
                practice.setExamName(rs.getString("exam_name"));
                practice.setKeyword(rs.getString("keyword"));
                practice.setExamDate(rs.getTimestamp("exam_date").toLocalDateTime());
                practice.setFinishedTime(rs.getTimestamp("finished_time").toLocalDateTime());
                practice.setNumQuestions(rs.getInt("num_questions"));
                practice.setScore(rs.getFloat("score"));
                practice.setQuizType(rs.getInt("quiz_type"));
                practice.setType(rs.getString("type_keyword_name"));
            }

            System.out.println("Practice fetched by ID: " + (practice != null ? practice.getQuizId() : "none")); // Debugging statement

        } catch (SQLException ex) {
            System.out.println("Errors occur in get practice by id DAO: " + ex.getMessage());
        }
        return practice;
    }

    public List<Question> getQuestionsBySubject(int subjectId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT q.question_id, q.type_id, q.topic_id, q.dimension_id, q.status, q.level, q.explaination, q.content, q.value "
                + "FROM Question q "
                + "JOIN SubjectTopic st ON q.topic_id = st.id "
                + "WHERE st.subject_id = ?";

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Question question = new Question(
                        rs.getInt("question_id"),
                        rs.getInt("type_id"),
                        rs.getInt("topic_id"),
                        rs.getInt("dimension_id"),
                        rs.getInt("status"),
                        rs.getInt("level"),
                        rs.getString("explaination"),
                        rs.getString("content"),
                        rs.getInt("value")
                );
                questions.add(question);
            }
        } catch (SQLException ex) {
            System.out.println("Errors occur in getQuestionsBySubject DAO: " + ex.getMessage());
        }
        return questions;
    }

    public List<Question> getQuestionsBySubjectAndTopic(int subjectId, int topicId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT q.question_id, q.type_id, q.topic_id, q.dimension_id, q.status, q.level, q.explaination, q.content, q.value "
                + "FROM Question q "
                + "JOIN SubjectTopic st ON q.topic_id = st.id "
                + "WHERE st.subject_id = ? AND st.id = ?";

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            ps.setInt(2, topicId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Question question = new Question(
                        rs.getInt("question_id"),
                        rs.getInt("type_id"),
                        rs.getInt("topic_id"),
                        rs.getInt("dimension_id"),
                        rs.getInt("status"),
                        rs.getInt("level"),
                        rs.getString("explaination"),
                        rs.getString("content"),
                        rs.getInt("value")
                );
                questions.add(question);
            }
        } catch (SQLException ex) {
            System.out.println("Errors occur in getQuestionsBySubjectAndTopic DAO: " + ex.getMessage());
        }
        return questions;
    }

    public List<Question> getQuestionsBySubjectAndDimension(int subjectId, int dimensionId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT q.question_id, q.type_id, q.topic_id, q.dimension_id, q.status, q.level, q.explaination, q.content, q.value "
                + "FROM Question q "
                + "JOIN SubjectDimension sd ON q.dimension_id = sd.dimension_id "
                + "WHERE sd.subject_id = ?";

        if (dimensionId != -1) {
            sql += " AND sd.dimension_id = ?";
        }

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            if (dimensionId != -1) {
                ps.setInt(2, dimensionId);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Question question = new Question(
                        rs.getInt("question_id"),
                        rs.getInt("type_id"),
                        rs.getInt("topic_id"),
                        rs.getInt("dimension_id"),
                        rs.getInt("status"),
                        rs.getInt("level"),
                        rs.getString("explaination"),
                        rs.getString("content"),
                        rs.getInt("value")
                );
                questions.add(question);
            }
        } catch (SQLException ex) {
            System.out.println("Errors occur in getQuestionsBySubjectAndDimension DAO: " + ex.getMessage());
        }
        return questions;
    }

    // Additional methods for fetching groups and other functionalities...
    public List<Group> getTopicsBySubjectId(int subjectId) {
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT id, name FROM SubjectTopic WHERE subject_id = ?";

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getInt("id"));
                group.setName(rs.getString("name"));
                groups.add(group);
            }
        } catch (SQLException ex) {
            System.out.println("Errors occur in getTopicsBySubjectId DAO: " + ex.getMessage());
        }
        return groups;
    }

    public List<Group> getDimensionsBySubjectId(int subjectId) {
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT dimension_id AS id, name FROM SubjectDimension WHERE subject_id = ?";

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getInt("id"));
                group.setName(rs.getString("name"));
                groups.add(group);
            }
        } catch (SQLException ex) {
            System.out.println("Errors occur in getDimensionsBySubjectId DAO: " + ex.getMessage());
        }
        return groups;
    }

    public int getAvailableQuestions(int subjectId, String selectionType, String questionGroup) {
        int availableQuestions = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS availableQuestions FROM Question q ");

        if ("topic".equals(selectionType)) {
            sql.append("JOIN SubjectTopic st ON q.topic_id = st.id WHERE st.subject_id = ?");
            if (!"all".equals(questionGroup)) {
                sql.append(" AND q.topic_id = ?");
            }
        } else {
            sql.append("JOIN SubjectDimension sd ON q.dimension_id = sd.dimension_id WHERE sd.subject_id = ?");
            if (!"all".equals(questionGroup)) {
                sql.append(" AND q.dimension_id = ?");
            }
        }

        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            ps.setInt(1, subjectId);
            if (!"all".equals(questionGroup)) {
                ps.setInt(2, Integer.parseInt(questionGroup));
            }

            LOGGER.log(Level.INFO, "Executing SQL: {0} with subject ID: {1} and question group: {2}", new Object[]{sql.toString(), subjectId, questionGroup});

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                availableQuestions = rs.getInt("availableQuestions");
            }
        } catch (SQLException ex) {
            System.out.println("Errors occur in getAvailableQuestions DAO: " + ex.getMessage());
        }
        return availableQuestions;
    }

    public int createPracticeAndGetQuizId(String examName, int subjectId, int numQuestions, String selectionType, String questionGroup) {
        int availableQuestions = getAvailableQuestions(subjectId, selectionType, questionGroup);

        LOGGER.log(Level.INFO, "Available questions: {0}", availableQuestions);

        if (availableQuestions < numQuestions) {
            LOGGER.log(Level.SEVERE, "Not enough questions available. Available questions: {0}", availableQuestions);
            return 0;
        }

        String insertQuizSql = "INSERT INTO Quiz (name, subject_id, num_questions, quiz_type, duration, level, status, pass_rate, description, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        String selectQuestionsSql = buildSelectQuestionsSql(selectionType, questionGroup, numQuestions);

        int quizId = 0;

        try (Connection connection = getConnection(); PreparedStatement psQuiz = connection.prepareStatement(insertQuizSql, PreparedStatement.RETURN_GENERATED_KEYS); PreparedStatement psQuestions = connection.prepareStatement(selectQuestionsSql)) {

            // Insert new quiz
            int duration = 60; // Example duration value, replace with appropriate value
            int level = 1; // Example level value, replace with appropriate value
            int status = 1; // Example status value, replace with appropriate value
            int passRate = 50; // Example pass rate value, replace with appropriate value

            LOGGER.log(Level.INFO, "Inserting new quiz with name: {0}, subject ID: {1}, num questions: {2}, quiz type: {3}, description: {4}",
                    new Object[]{examName, subjectId, numQuestions, selectionType.equals("topic") ? 1 : 3, questionGroup});

            psQuiz.setString(1, examName);
            psQuiz.setInt(2, subjectId);
            psQuiz.setInt(3, numQuestions);
            psQuiz.setInt(4, selectionType.equals("topic") ? 1 : 3); // 1 for topic, 3 for dimension
            psQuiz.setInt(5, duration);
            psQuiz.setInt(6, level);
            psQuiz.setInt(7, status);
            psQuiz.setInt(8, passRate);
            psQuiz.setString(9, questionGroup.equals("all") ? "All" : questionGroup);
            psQuiz.executeUpdate();

            // Get generated quiz ID
            ResultSet rs = psQuiz.getGeneratedKeys();
            if (rs.next()) {
                quizId = rs.getInt(1);
                LOGGER.log(Level.INFO, "Generated quiz ID: {0}", quizId);
            } else {
                LOGGER.log(Level.SEVERE, "Failed to retrieve generated quiz ID.");
                return 0;
            }

            // Select questions
            psQuestions.setInt(1, subjectId);
            if (!questionGroup.equals("all")) {
                psQuestions.setInt(2, Integer.parseInt(questionGroup));
            }
            LOGGER.log(Level.INFO, "Executing question selection SQL: {0} with subject ID: {1} and question group: {2}",
                    new Object[]{selectQuestionsSql, subjectId, questionGroup});

            ResultSet rsQuestions = psQuestions.executeQuery();

            List<Integer> questionIds = new ArrayList<>();
            while (rsQuestions.next()) {
                questionIds.add(rsQuestions.getInt("question_id"));
            }

            if (questionIds.isEmpty()) {
                LOGGER.log(Level.SEVERE, "No questions found for the given criteria.");
                return 0;
            }

            // Select only the required number of questions
            List<Integer> selectedQuestions = questionIds.subList(0, Math.min(numQuestions, questionIds.size()));

            // Insert selected questions into Quiz_Question table with order
            String insertQuizQuestionSql = "INSERT INTO Quiz_Question (quiz_id, question_id, [order]) VALUES (?, ?, ?)";
            try (PreparedStatement psQuizQuestion = connection.prepareStatement(insertQuizQuestionSql)) {
                int order = 1;
                for (int questionId : selectedQuestions) {
                    psQuizQuestion.setInt(1, quizId);
                    psQuizQuestion.setInt(2, questionId);
                    psQuizQuestion.setInt(3, order++);
                    psQuizQuestion.addBatch();
                }
                psQuizQuestion.executeBatch();
                LOGGER.log(Level.INFO, "Inserted selected questions into Quiz_Question table.");
            }

            return quizId;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Errors occur in createPractice DAO: " + ex.getMessage(), ex);
            return 0;
        }
    }

    private String buildSelectQuestionsSql(String selectionType, String questionGroup, int numQuestions) {
        StringBuilder sql = new StringBuilder("SELECT TOP ").append(numQuestions).append(" q.question_id FROM Question q ");

        if (selectionType.equals("topic")) {
            sql.append("JOIN SubjectTopic st ON q.topic_id = st.id WHERE st.subject_id = ?");
            if (!questionGroup.equals("all")) {
                sql.append(" AND q.topic_id = ?");
            }
        } else {
            sql.append("JOIN SubjectDimension sd ON q.dimension_id = sd.dimension_id WHERE sd.subject_id = ?");
            if (!questionGroup.equals("all")) {
                sql.append(" AND q.dimension_id = ?");
            }
        }

        sql.append(" ORDER BY NEWID()"); // NEWID() is used to randomly select rows in SQL Server
        return sql.toString();
    }

    public static void main(String[] args) {
        PracticeDAO dao = new PracticeDAO();

        // Test case: specific dimension with "all" option for question group
        runTest(dao, "Test Exam - All Dimensions", 1, 20, "dimension", "all");
        runTest(dao, "Test Exam - Specific Dimension 1", 1, 20, "dimension", "1");
        runTest(dao, "Test Exam - Specific Dimension 2", 1, 20, "dimension", "2");
        runTest(dao, "Test Exam - Specific Dimension 3", 1, 20, "dimension", "3");
    }

    private static void runTest(PracticeDAO dao, String examName, int subjectId, int numQuestions, String selectionType, String questionGroup) {
        int quizId = dao.createPracticeAndGetQuizId(examName, subjectId, numQuestions, selectionType, questionGroup);
        if (quizId > 0) {
            System.out.println("Practice created successfully with quizId: " + quizId);
        } else {
            System.out.println("Failed to create practice for " + examName);
        }
    }
}
