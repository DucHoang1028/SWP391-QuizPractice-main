
package Models.Quiz;


public class Answer {
    private int answerId;
    private int questionId;
    private String content;
    private boolean is_correct;
    private boolean is_record;

    public Answer(int answerId, int questionId, String content, boolean is_correct) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.content = content;
        this.is_correct = is_correct;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIs_correct() {
        return is_correct;
    }

    public void setIs_correct(boolean is_correct) {
        this.is_correct = is_correct;
    }

    public boolean isIs_record() {
        return is_record;
    }

    public void setIs_record(boolean is_record) {
        this.is_record = is_record;
    }
    
}
