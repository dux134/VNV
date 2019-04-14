package in.book.vnv.entity;

public class ContentDataModel {
    private String title;
    private String questionFinished;
    private String questionTotal;
    private String completePercentage;

    public ContentDataModel(String title, String questionFinished, String questionTotal, String completePercentage) {
        this.title = title;
        this.questionFinished = questionFinished;
        this.questionTotal = " /"+questionTotal;
        this.completePercentage = completePercentage +"%";
    }

    public ContentDataModel() {
    }

    public String getTitle() {
        return title;
    }

    public String getQuestionFinished() {
        return questionFinished;
    }

    public String getQuestionTotal() {
        return questionTotal;
    }

    public String getCompletePercentage() {
        return completePercentage;
    }
}
