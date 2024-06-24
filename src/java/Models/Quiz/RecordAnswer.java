
package Models.Quiz;


public class RecordAnswer {
    private int id;
    private int recordId;
    private int questionId;
    private boolean isFlagged;
    private String content;
    private int answerId;

    public RecordAnswer(int id, int recordId, int questionId, boolean isFlagged, String content, int answerId) {
        this.id = id;
        this.recordId = recordId;
        this.questionId = questionId;
        this.isFlagged = isFlagged;
        this.content = content;
        this.answerId = answerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isIsFlagged() {
        return isFlagged;
    }

    public void setIsFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    
    
}
