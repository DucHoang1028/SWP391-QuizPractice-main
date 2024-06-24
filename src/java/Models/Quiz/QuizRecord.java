
package Models.Quiz;

import java.time.LocalDateTime;


public class QuizRecord {
    private int record_id;
    private int user_id;
    private int quiz_id;
    private LocalDateTime created_at;
    private float score;
    private int status;
    private LocalDateTime finished_at;
    private int i;
    public QuizRecord(){
        
    }
    
    // constructor for quiz handle
    public QuizRecord(int record_id, int user_id, int quiz_id, LocalDateTime created_at) {
        this.record_id = record_id;
        this.user_id = user_id;
        this.quiz_id = quiz_id;
        this.created_at = created_at;
    }
    
    
    
    public QuizRecord(int record_id, int user_id, int quiz_id, LocalDateTime created_at, float score, LocalDateTime finished_at) {
        this.record_id = record_id;
        this.user_id = user_id;
        this.quiz_id = quiz_id;
        this.created_at = created_at;
        this.score = score;
        this.finished_at = finished_at;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public LocalDateTime getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(LocalDateTime finished_at) {
        this.finished_at = finished_at;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    

    @Override
    public String toString() {
        // return a json string representation of the object, change all the " to ' to reduce the chance of breaking the string
        return "{" +
                "'record_id':" + record_id +
                ",'user_id':" + user_id +
                ",'quiz_id':" + quiz_id +
                ",'created_at':'" + created_at + "'" +
                ",'score':" + score +
                ",'status':" + status +
                ",'finished_at':'" + finished_at + "'" +
                '}';
                
    }
    
    
    
}
