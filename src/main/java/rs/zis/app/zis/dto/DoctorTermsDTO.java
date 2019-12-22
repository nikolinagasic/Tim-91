package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.DoctorTerms;

public class DoctorTermsDTO {
    private long date;
    private int start_term;
    private int end_term;

    public DoctorTermsDTO() {
    }

    public DoctorTermsDTO(long date, int start_term, int end_term) {
        this.date = date;
        this.start_term = start_term;
        this.end_term = end_term;
    }

    public DoctorTermsDTO(DoctorTerms doctorTerms) {
        this.date = doctorTerms.getDate();
        this.start_term = doctorTerms.getStart_term();
        this.end_term = doctorTerms.getEnd_term();
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getStart_term() {
        return start_term;
    }

    public void setStart_term(int start_term) {
        this.start_term = start_term;
    }

    public int getEnd_term() {
        return end_term;
    }

    public void setEnd_term(int end_term) {
        this.end_term = end_term;
    }
}
