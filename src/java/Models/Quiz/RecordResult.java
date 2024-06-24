
package Models.Quiz;

public class RecordResult {
    private int recordId;
    private int quizId;
    private int questionId;
    private int order;
    private int answerId;
    private int NumberOrCorrect;
    private int NumberOfAnswer;
    private int mark;

    public RecordResult(int recordId, int quizId, int questionId, int order, int answerId, int NumberOrCorrect, int NumberOfAnswer, int mark) {
        this.recordId = recordId;
        this.quizId = quizId;
        this.questionId = questionId;
        this.order = order;
        this.answerId = answerId;
        this.NumberOrCorrect = NumberOrCorrect;
        this.NumberOfAnswer = NumberOfAnswer;
        this.mark = mark;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getNumberOrCorrect() {
        return NumberOrCorrect;
    }

    public void setNumberOrCorrect(int NumberOrCorrect) {
        this.NumberOrCorrect = NumberOrCorrect;
    }

    public int getNumberOfAnswer() {
        return NumberOfAnswer;
    }

    public void setNumberOfAnswer(int NumberOfAnswer) {
        this.NumberOfAnswer = NumberOfAnswer;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

   
}
