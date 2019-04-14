package in.book.vnv.entity;

public class ChaptersDataModel {
    private String title;
    private String queCompleted;
    private String queTotal;
    private String questions;

    public ChaptersDataModel(String title, String queCompleted, String queTotal, String questions) {
        this.title = title;
        this.queCompleted = queCompleted;
        this.queTotal = queTotal;
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQueCompleted() {
        return queCompleted;
    }

    public void setQueCompleted(String queCompleted) {
        this.queCompleted = queCompleted;
    }

    public String getQueTotal() {
        return queTotal;
    }

    public void setQueTotal(String queTotal) {
        this.queTotal = queTotal;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }
}
