package in.book.vnv.entity;

public class DailyScore {
    String id;
    String date;
    String solved;
    String correct;
    String total;

    public DailyScore(String id, String date, String solved, String correct, String total) {
        this.id = id;
        this.date = date;
        this.solved = solved;
        this.correct = correct;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSolved() {
        return solved;
    }

    public void setSolved(String solved) {
        this.solved = solved;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
