package rs.zis.app.zis.dto;

public class ReviewsRecordDTO {

    private long date;  //datum unosa medicinskog izvestaja
    private String firstName; //ime lekara koji je uneo izvestaj
    private String lastName; //prezime lekara koji je uneo izvestaj
    private boolean couldChange; //dozvoljena izmena izvestaja lekaru koji trazi istoriju bolesti
    private Long reviewId; // id izvestaja

    public ReviewsRecordDTO() {
    }

    public ReviewsRecordDTO(long date, String firstName, String lastName, boolean couldChange, Long reviewId) {
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.couldChange = couldChange;
        this.reviewId = reviewId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isCouldChange() {
        return couldChange;
    }

    public void setCouldChange(boolean couldChange) {
        this.couldChange = couldChange;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }
}
