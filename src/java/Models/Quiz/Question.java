package Models.Quiz;

import DAOs.QuizDAO;
import java.util.HashMap;

public class Question {

    private int questionId;
    private int typeId;
    private int topicId;
    private int dimensionId;
    private int status;
    private int level;
    private String explaination;
    private String content;
    private int value;

    public static final int TYPE_SINGLE_CHOICE = 1;
    public static final int TYPE_MULTIPLE_CHOICE = 2;
    public static final int TYPE_FILL_BLANK = 3;

    public Question(int questionId, int typeId, int topicId, int dimensionId, int status, int level, String explaination, String content, int value) {
        this.questionId = questionId;
        this.typeId = typeId;
        this.topicId = topicId;
        this.dimensionId = dimensionId;
        this.status = status;
        this.level = level;
        this.explaination = explaination;
        this.content = content;
        this.value = value;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(int dimensionId) {
        this.dimensionId = dimensionId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    // get the question type
    public String getType() {
        return switch (typeId) {
            case 1 ->
                "Single Choice";
            case 2 ->
                "Multiple Choice";
            case 3 ->
                "Fill Blank";
            default ->
                "Unknown";
        };
    }

    public int getNumOfAnswers() {
        HashMap<Integer, Answer> answers = new QuizDAO().GetAnswersByQues(questionId);
        int result = answers.values().stream().filter(x -> x.isIs_correct() == true).toList().size();
        return result;
    }

}
