package DAOs;

import Contexts.DBContext;
import Models.Quiz.Answer;
import Models.Quiz.Question;
import Models.Quiz.QuizRecord;
import Models.Quiz.Quiz;
import Models.Quiz.RecordAnswer;
import Models.Quiz.RecordResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuizDAO extends DBContext {

    public QuizRecord GetQuizRecordByRecordId(int recordId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        QuizRecord record = null;
        String sqlCommand = "SELECT [record_id]\n"
                + "      ,[user_id]\n"
                + "      ,[quiz_id]\n"
                + "      ,[created_at]\n"
                + "      ,[score]\n"
                + "      ,[finished_at]\n"
                + "  FROM [dbo].[QuizRecord] WHERE record_id = ?";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, recordId);
            rs = ps.executeQuery();

            while (rs.next()) {
                LocalDateTime create = null;
                if (rs.getDate("created_at") != null) {
                    create = rs.getTimestamp("created_at").toLocalDateTime();
                }
                LocalDateTime finish = null;
                if (rs.getDate("finished_at") != null) {
                    finish = rs.getTimestamp("finished_at").toLocalDateTime();
                }
                record = new QuizRecord(rs.getInt("record_id"),
                        rs.getInt("user_id"), rs.getInt("quiz_id"),
                        create,
                        rs.getFloat("score"),
                        finish);
            }

        } catch (SQLException ex) {
            System.out.println("Errors occur in get user DAO: " + ex.getMessage());
        } finally {
        }
        return record;
    }

    public Quiz GetQuizById(int quizId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        Quiz quiz = null;
        String sqlCommand = "SELECT [quiz_id]\n"
                + "      ,[name]\n"
                + "      ,[duration]\n"
                + "      ,[quiz_type]\n"
                + "      ,[num_questions]\n"
                + "      ,[level]\n"
                + "      ,[status]\n"
                + "      ,[pass_rate]\n"
                + "      ,[description]\n"
                + "      ,[created_at]\n"
                + "      ,[updated_at]\n"
                + "      ,[subject_id]\n"
                + "      ,[pass_condition]\n"
                + "  FROM [dbo].[Quiz] WHERE quiz_id = ?";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, quizId);
            rs = ps.executeQuery();

            while (rs.next()) {
                LocalDateTime create = null;
                if (rs.getDate("created_at") != null) {
                    create = rs.getTimestamp("created_at").toLocalDateTime();
                }
                LocalDateTime update = null;
                if (rs.getDate("updated_at") != null) {
                    update = rs.getTimestamp("updated_at").toLocalDateTime();
                }
                quiz = new Quiz(quizId,
                        rs.getString("name"),
                        rs.getInt("duration"),
                        rs.getInt("quiz_type"),
                        rs.getInt("num_questions"),
                        rs.getInt("level"),
                        rs.getInt("status"),
                        rs.getInt("pass_rate"),
                        rs.getString("description"),
                        create,
                        update,
                        rs.getInt("subject_id"),
                        rs.getInt("pass_condition"));
            }

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
        return quiz;
    }
    
    // get all quiz-record of an user for a quiz
    public List<QuizRecord> GetQuizRecordsByUserId(int user_id, int quiz_id, int offset, int length) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        List<QuizRecord> records = new ArrayList<>();
        String sqlCommand = "SELECT [record_id]\n"
                + "      ,[user_id]\n"
                + "      ,[quiz_id]\n"
                + "      ,[created_at]\n"
                + "      ,[score]\n"
                + "      ,[status]\n"
                + "      ,[finished_at]\n"
                + "  FROM [dbo].[QuizRecord] WHERE user_id = ? AND quiz_id = ?"
                + " ORDER BY created_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, user_id);
            ps.setInt(2, quiz_id);
            ps.setInt(3, offset);
            ps.setInt(4, length);
            rs = ps.executeQuery();

            while (rs.next()) {
                LocalDateTime create = null;
                if (rs.getDate("created_at") != null) {
                    create = rs.getTimestamp("created_at").toLocalDateTime();
                }
                LocalDateTime finish = null;
                if (rs.getDate("finished_at") != null) {
                    finish = rs.getTimestamp("finished_at").toLocalDateTime();
                }            
                QuizRecord record = new QuizRecord();
                record.setRecord_id(rs.getInt("record_id"));
                record.setUser_id(rs.getInt("user_id"));
                record.setQuiz_id(rs.getInt("quiz_id"));
                record.setCreated_at(create);
                record.setScore(rs.getFloat("score"));
                record.setStatus(rs.getInt("status"));
                record.setFinished_at(finish);
                records.add(record);
            }

        } catch (SQLException ex) {
            System.out.println("Errors occur in get user DAO: " + ex.getMessage());
        } finally {
        }
        return records;
    }

    public Question GetQuestion(int quizId, int order) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        Question result = null;
        String sqlCommand = "SELECT dbo.Question.*, dbo.Quiz_Question.*\n"
                + "FROM     dbo.Question INNER JOIN\n"
                + "                  dbo.Quiz_Question ON dbo.Question.question_id = dbo.Quiz_Question.question_id\n"
                + "WHERE quiz_id = ? AND [order] =?";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, quizId);
            ps.setInt(2, order);
            rs = ps.executeQuery();

            while (rs.next()) {

                result = new Question(rs.getInt("question_id"),
                        rs.getInt("type_id"),
                        rs.getInt("topic_id"),
                        rs.getInt("dimension_id"),
                        rs.getInt("status"),
                        rs.getInt("level"),
                        rs.getString("explaination"),
                        rs.getString("content"),
                        rs.getInt("value"));
            }

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
        return result;
    }

    public HashMap<Integer, Answer> GetAnswersByQues(int questionId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        HashMap<Integer, Answer> result = new HashMap<Integer, Answer>();
        String sqlCommand = "SELECT [answer_id]\n"
                + "      ,[question_id]\n"
                + "      ,[content]\n"
                + "      ,[is_correct]\n"
                + "  FROM [dbo].[Answer] WHERE question_id = ?";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, questionId);
            rs = ps.executeQuery();

            while (rs.next()) {

                Answer answer = new Answer(rs.getInt("answer_id"),
                        questionId,
                        rs.getString("content"),
                        rs.getBoolean("is_correct"));
                result.put(answer.getAnswerId(), answer);
            }

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
        return result;
    }

    public HashMap<Integer, RecordAnswer> GetRecordAnswersByQues(int questionId, int recordId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        HashMap<Integer, RecordAnswer> result = new HashMap<>();
        String sqlCommand = "SELECT [id]\n"
                + "      ,[record_id]\n"
                + "      ,[question_id]\n"
                + "      ,[is_flagged]\n"
                + "      ,[content]\n"
                + "      ,[answer_id]\n"
                + "  FROM [dbo].[RecordAnswer]\n"
                + "WHERE record_id = ? AND question_id = ?";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, recordId);
            ps.setInt(2, questionId);

            rs = ps.executeQuery();

            while (rs.next()) {

                RecordAnswer answer = new RecordAnswer(rs.getInt("id"),
                        recordId, questionId,
                        rs.getBoolean("is_flagged"),
                        sqlCommand, rs.getInt("answer_id"));
                result.put(answer.getAnswerId(), answer);
                System.out.println(rs.getInt("id"));
            }

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
        return result;
    }
    
    public RecordAnswer getRecordAnswerByQues(int recordId, int questionId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        RecordAnswer result = null;
        String sqlCommand = "SELECT [id]\n"
                + "      ,[record_id]\n"
                + "      ,[question_id]\n"
                + "      ,[is_flagged]\n"
                + "      ,[content]\n"
                + "      ,[answer_id]\n"
                + "  FROM [dbo].[RecordAnswer]\n"
                + "WHERE record_id = ? AND question_id = ?";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, recordId);
            ps.setInt(2, questionId);

            rs = ps.executeQuery();

            while (rs.next()) {

                result = new RecordAnswer(rs.getInt("id"),
                        recordId, questionId,
                        rs.getBoolean("is_flagged"),
                        rs.getString("content"), rs.getInt("answer_id"));
            }

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
        return result;
    }

    public HashMap<Integer, RecordResult> GetRecordResult(int recordId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        HashMap<Integer, RecordResult> result = new HashMap<>();
        String sqlCommand = "SELECT [record_id]\n"
                + "      ,[quiz_id]\n"
                + "      ,[question_id]\n"
                + "      ,[order]\n"
                + "      ,[answer_id]\n"
                + "      ,[NumberOrCorrect]\n"
                + "      ,[NumberOfAnswer],Mark\n"
                + "  FROM [dbo].[RecordResult] WHERE record_id = ?";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, recordId);

            rs = ps.executeQuery();

            while (rs.next()) {

                RecordResult item = new RecordResult(rs.getInt("record_id"),
                        rs.getInt("quiz_id"),
                        rs.getInt("question_id"),
                        rs.getInt("order"), rs.getInt("answer_id"),
                        rs.getInt("NumberOrCorrect"),
                        rs.getInt("NumberOfAnswer")
                ,rs.getInt("Mark"));
                result.put(item.getOrder(), item);
            }

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
        return result;
    }

    public void AddMarkQuestion(int recordId, int questionId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        String sqlCommand = "INSERT INTO [dbo].[RecordMark]\n"
                + "           ([record_id]\n"
                + "           ,[question_id]\n"
                + "           )\n"
                + "     VALUES\n"
                + "           (?,?)";
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, recordId);
            ps.setInt(2, questionId);
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Errors occur in get user DAO: " + ex.getMessage());
        } finally {

        }
    }

    public boolean GetMarkQuestion(int recordId, int questionId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        boolean result = false;
        String sqlCommand = "SELECT [record_id]\n"
                + "      ,[question_id]\n"
                + "      ,[note]\n"
                + "  FROM [dbo].[RecordMark] WHERE record_id = ? AND question_id = ?";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, recordId);
            ps.setInt(2, questionId);

            rs = ps.executeQuery();

            while (rs.next()) {
                result = true;
            }

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
        return result;
    }

    public void DeleteRecordMark(int record_id, int questionId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        String sqlCommand = "DELETE FROM [dbo].[RecordMark]\n"
                + "      WHERE  record_id = ? AND question_id = ?";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, record_id);
            ps.setInt(2, questionId);

            rs = ps.executeQuery();

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }

    }

    public QuizRecord CreateNewRecord(int user_id, int quiz_id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        QuizRecord record = null;
        String sqlCommand = "INSERT INTO [dbo].[QuizRecord]\n"
                + "           ([user_id]\n"
                + "           ,[quiz_id]\n"
                + "           ,[created_at])\n"
                + "     VALUES\n"
                + "           (?,?,?)";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, user_id);
            ps.setInt(2, quiz_id);
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();

            record = GetQuizRecordByRecordId(GetLastRecordId(user_id, quiz_id));
        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
        return record;
    }

    private int GetLastRecordId(int user_id, int quiz_id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        int result = 0;
        String sqlCommand = "SELECT MAX(record_id) as record_id FROM [dbo].[QuizRecord] WHERE user_id = ? AND quiz_id = ?";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, user_id);
            ps.setInt(2, quiz_id);
            rs = ps.executeQuery();

            while (rs.next()) {
                result = rs.getInt("record_id");
            }

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
        return result;
    }

    public boolean CheckUserAccess(int user_id, int quiz_id) {

        // user -> registration -> pricepackage -> subject_id
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;

        boolean result = false;
        String sqlCommand = "SELECT [dbo].[User].user_id\n"
                + "FROM     [dbo].[User] INNER JOIN\n"
                + "  [dbo].[Registration] ON [dbo].[User].user_id = [dbo].[Registration].user_id INNER JOIN\n"
                + "  [dbo].[PricePackage] ON [dbo].[Registration].package_id = [dbo].[PricePackage].package_id INNER JOIN\n"
                + "  [dbo].[Subject] ON [dbo].[PricePackage].subject_id = [dbo].[Subject].subject_id INNER JOIN\n"
                + "  [dbo].[Quiz] ON [dbo].[Subject].subject_id = [dbo].[Quiz].subject_id\n"
                + "WHERE [dbo].[User].user_id = ? AND [dbo].[Quiz].quiz_id = ? "
                + "AND [dbo].[PricePackage].status = 1 "
                + "AND [dbo].[Registration].status = 1 ";
        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, user_id);
            ps.setInt(2, quiz_id);
            rs = ps.executeQuery();

            while (rs.next()) {
                result = true;
            }

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
        return result;

    }

    public void UpdateAnswer(int record_id, int questionId, int answerId) {
        DeleteAnswer(record_id, questionId);
        PreparedStatement ps = null;
        Connection connection = null;

        String sqlCommand = "INSERT INTO [dbo].[RecordAnswer]\n"
                + "           ([record_id]\n"
                + "           ,[question_id]\n"
                + "           ,[answer_id])\n"
                + "     VALUES\n"
                + "           (?,?,?)";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, record_id);
            ps.setInt(2, questionId);
            ps.setInt(3, answerId);
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }

    }

    public void UpdateAnswer(int record_id, int questionId, String answer) {
        DeleteAnswer(record_id, questionId);
        if (answer.length() == 0) return;
        PreparedStatement ps = null;
        Connection connection = null;

        String sqlCommand = "INSERT INTO [dbo].[RecordAnswer]\n"
                + "           ([record_id]\n"
                + "           ,[question_id]\n"
                + "           ,[content])\n"
                + "     VALUES\n"
                + "           (?,?,?)";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, record_id);
            ps.setInt(2, questionId);
            ps.setString(3, answer);
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
    }

    public void UpdateAnswer(int record_id, int questionId, int[] answer_ids) {
        DeleteAnswer(record_id, questionId);
        PreparedStatement ps = null;
        Connection connection = null;

        String sqlCommand = "INSERT INTO [dbo].[RecordAnswer]\n"
                + "           ([record_id]\n"
                + "           ,[question_id]\n"
                + "           ,[answer_id])\n"
                + "     VALUES\n"
                + "           (?,?,?)";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            for (int i = 0; i < answer_ids.length; i++) {
                ps = connection.prepareStatement(sqlCommand);
                ps.setInt(1, record_id);
                ps.setInt(2, questionId);
                ps.setInt(3, answer_ids[i]);
                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
    }

    public void DeleteAnswer(int record_id, int question_id) {
        PreparedStatement ps = null;
        Connection connection = null;

        String sqlCommand = "DELETE FROM [dbo].[RecordAnswer] WHERE record_id = ? AND question_id = ?";

        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setInt(1, record_id);
            ps.setInt(2, question_id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }
    }

    public void SubmitRecord(int record_id, int quiz_id) {

        Quiz quiz = GetQuizById(quiz_id);
        QuizRecord record = GetQuizRecordByRecordId(record_id);

        // set finished_at = min of the current time and the quiz duration + created_at
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime finishedAt = record.getCreated_at().plusMinutes(quiz.getDuration());
        finishedAt = finishedAt.isBefore(currentTime) ? finishedAt : currentTime;
        
        // if the quiz is not simulation exam, then finishedAt is not having the above constraint
        if (quiz.getQuizType() != 2){
            finishedAt = currentTime;
        }

        PreparedStatement ps = null;
        Connection connection = null;

        String sqlCommand = "UPDATE [dbo].[QuizRecord]\n"
                + "   SET [score] = ?,\n"
                + "       [finished_at] = ?,\n"
                + "       [status] = ?\n"
                + " WHERE record_id = ?";
        // Try-with-resources to ensure the resources are closed after usage
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sqlCommand);
            ps.setFloat(1, GetRecordScore(record_id, quiz_id));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(finishedAt));
            ps.setInt(3, GetRecordScore(record_id, quiz_id) >= quiz.getPassCondition() ? 1 : 0 );
            ps.setInt(4, record_id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        } finally {
        }

    }
    
    public int GetRecordScore(int record_id, int quiz_id){
        HashMap<Integer, RecordResult> recordres = GetRecordResult(record_id);
        int corrects = recordres.values().stream().filter(x -> x.getNumberOrCorrect() > 0).toList().size();
        int total = recordres.size();
        return corrects * 100 / total;
    }

    public List<Quiz> GetSimulationExams(String search_value, int subject_id[], int sort, int page, int page_length) {
        // make a condition for query (where clause)
        String condition = "WHERE status = 1 AND quiz_type = 2";
        // if search value is not empty, add search condition
        if (!search_value.isEmpty()) {
            condition += " AND [name] LIKE '%" + search_value + "%'";
        }
        // if subject_id is not empty, add subject_id condition
        if (subject_id.length > 0) {
            // subject_id is an array
            String subject_condition = "AND (";
            for (int i = 0; i < subject_id.length; i++) {
                subject_condition += "subject_id = " + subject_id[i];
                if (i != subject_id.length - 1) {
                    subject_condition += " OR ";
                }
            }
            subject_condition += ")";
            condition += " " + subject_condition;
        }
        // make a sort condition
        String sort_condition = "";
        switch (sort) {
            case 1:
                sort_condition = "ORDER BY created_at DESC, quiz_id ASC";
                break;
            case 2:
                sort_condition = "ORDER BY name ASC, quiz_id ASC";
                break;
            case 3:
                sort_condition = "ORDER BY pass_rate DESC, quiz_id ASC";
                break;
            case 4:
                sort_condition = "ORDER BY level ASC, quiz_id ASC";
                break;
            default:
                sort_condition = "ORDER BY created_at DESC, quiz_id ASC";
                break;
        }
        // make a limit condition
        String limit_condition = "OFFSET " + (page - 1) * page_length + " ROWS FETCH NEXT " + page_length + " ROWS ONLY";
        // make a query
        String sqlCommand = "SELECT * FROM Quiz " + condition + " " + sort_condition + " " + limit_condition;
        System.out.println(sqlCommand);
        // make a list to store result
        List<Quiz> result = new ArrayList<>();
        // Try-with-resources to ensure the resources are closed after usage
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sqlCommand);
                ResultSet rs = ps.executeQuery();) {
            // get data from result set
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizId(rs.getInt("quiz_id"));
                quiz.setName(rs.getString("name"));
                quiz.setDuration(rs.getInt("duration"));
                quiz.setQuizType(rs.getInt("quiz_type"));
                quiz.setNumQuestions(rs.getInt("num_questions"));
                quiz.setLevel(rs.getInt("level"));
                quiz.setStatus(rs.getInt("status"));
                quiz.setPassRate(rs.getInt("pass_rate"));
                quiz.setDescription(rs.getString("description"));
                quiz.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                quiz.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
                quiz.setSubjectId(rs.getInt("subject_id"));
                quiz.setPassCondition(rs.getInt("pass_condition"));
                result.add(quiz);
            }
        } catch (SQLException ex) {
            System.out.println("Errors occur in get quiz DAO: " + ex.getMessage());
        }
        return result;

    }

    // main to test
    public static void main(String[] args) {
        QuizDAO dao = new QuizDAO();
       
        System.out.println(dao.GetQuizRecordsByUserId(1, 6, 0, 10));
    }

    
}
