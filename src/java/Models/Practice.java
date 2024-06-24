package Models;

import java.time.Duration;
import java.time.LocalDateTime;

public class Practice {
    private int quizId;
    private String subjectName;
    private String examName;
    private String keyword;
    private LocalDateTime examDate;
    private LocalDateTime finishedTime;
    private int numQuestions;
    private float score; // The score itself will be the percentage
    private int quizType;
    private String type; // New field to store whether it's "Topic" or "Dimension"

    // Getters and setters...

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public LocalDateTime getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDateTime examDate) {
        this.examDate = examDate;
    }

    public LocalDateTime getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(LocalDateTime finishedTime) {
        this.finishedTime = finishedTime;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getQuizType() {
        return quizType;
    }

    public void setQuizType(int quizType) {
        this.quizType = quizType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Method to calculate the number of correct questions
    public int getNumberOfCorrectQuestions() {
        return Math.round((this.score / 100) * this.numQuestions);
    }

    // Method to return the correct percentage directly from the score
    public float getCorrectPercentage() {
        return this.score;
    }

    // Method to calculate the duration
    public String getDuration() {
        if (examDate != null && finishedTime != null) {
            Duration duration = Duration.between(examDate, finishedTime);
            long minutes = duration.toMinutes();
            long seconds = duration.getSeconds() % 60;
            return String.format("%d mins %d secs", minutes, seconds);
        } else {
            return "N/A";
        }
    }
}
