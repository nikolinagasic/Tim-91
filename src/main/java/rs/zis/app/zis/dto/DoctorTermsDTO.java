package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.DoctorTerms;
import rs.zis.app.zis.domain.TermDefinition;

public class DoctorTermsDTO {
    private long date;
    private String start_term;
    private String end_term;

    public DoctorTermsDTO(long date, String start_term, String end_term) {
        this.date = date;
        this.start_term = start_term;
        this.end_term = end_term;
    }

    public DoctorTermsDTO(Long date, TermDefinition termDefinition) {
        this.date = date;
        this.start_term = termDefinition.getStartTerm();
        this.end_term = termDefinition.getEndTerm();
    }

    public DoctorTermsDTO(DoctorTerms doctorTerms) {
        this.date = doctorTerms.getDate();
        this.start_term = doctorTerms.getTerm().getStartTerm();
        this.end_term = doctorTerms.getTerm().getEndTerm();
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getStart_term() {
        return start_term;
    }

    public void setStart_term(String start_term) {
        this.start_term = start_term;
    }

    public String getEnd_term() {
        return end_term;
    }

    public void setEnd_term(String end_term) {
        this.end_term = end_term;
    }
}
