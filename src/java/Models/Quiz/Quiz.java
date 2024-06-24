package Models.Quiz;

import DAOs.QuizDAO;
import DAOs.SubjectDAO;
import java.time.LocalDateTime;
import java.util.List;

public class Quiz {

    private int quizId;
    private String name;
    private int duration;
    private int quizType;
    private int numQuestions;
    private int level;
    private int status;
    private int passRate;
    private String description;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private int subjectId;
    private int passCondition;

    public Quiz() {

    }

    public Quiz(int quizId, String name, int duration, int quizType,
            int numQuestions, int level, int status, int passRate,
            String description, LocalDateTime created_at, LocalDateTime updated_at,
            int subjectId, int passCondition) {
        this.quizId = quizId;
        this.name = name;
        this.duration = duration;
        this.quizType = quizType;
        this.numQuestions = numQuestions;
        this.level = level;
        this.status = status;
        this.passRate = passRate;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.subjectId = subjectId;
        this.passCondition = passCondition;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getQuizType() {
        return quizType;
    }

    public void setQuizType(int quizType) {
        this.quizType = quizType;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPassRate() {
        return passRate;
    }

    public void setPassRate(int passRate) {
        this.passRate = passRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public int getSubjectId() {
        return subjectId;
    }
    
    public String getSubjectName(){
        return new SubjectDAO().GetPublishedSubjectById(subjectId).getTitle();
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getPassCondition() {
        return passCondition;
    }

    public void setPassCondition(int passCondition) {
        this.passCondition = passCondition;
    }
    public List<QuizRecord> GetQuizRecordsByUserId(int user_id){
        return new QuizDAO().GetQuizRecordsByUserId(user_id, quizId, 0, 5);
    }

}
