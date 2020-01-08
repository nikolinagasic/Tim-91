package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.DoctorTerms;
import rs.zis.app.zis.domain.TermDefinition;

@SuppressWarnings("unused")
public class DoctorTermsDTO {
    private long date;
    private String start_term;
    private String end_term;
    private String firstNameDoctor;
    private String lastNameDoctor;
    private double price;
    private int discount;
    private String type;        // tip pregleda

    public DoctorTermsDTO() {
    }

    public DoctorTermsDTO(long date, String start_term, String end_term, Doctor doctor) {
        this.date = date;
        this.start_term = start_term;
        this.end_term = end_term;
        this.firstNameDoctor = doctor.getFirstName();
        this.lastNameDoctor = doctor.getLastName();
        this.price = doctor.getPrice();
        this.discount = doctor.getDiscount();
        this.type = doctor.getTip().getName();
    }

    public DoctorTermsDTO(Long date, TermDefinition termDefinition, Doctor doctor) {
        this.date = date;
        this.start_term = termDefinition.getStartTerm();
        this.end_term = termDefinition.getEndTerm();
        this.firstNameDoctor = doctor.getFirstName();
        this.lastNameDoctor = doctor.getLastName();
        this.price = doctor.getPrice();
        this.discount = doctor.getDiscount();
        this.type = doctor.getTip().getName();
    }

    public DoctorTermsDTO(DoctorTerms doctorTerms) {
        this.date = doctorTerms.getDate();
        this.start_term = doctorTerms.getTerm().getStartTerm();
        this.end_term = doctorTerms.getTerm().getEndTerm();
        this.firstNameDoctor = doctorTerms.getDoctor().getFirstName();
        this.lastNameDoctor = doctorTerms.getDoctor().getLastName();
        this.price = doctorTerms.getDoctor().getPrice();
        this.discount = doctorTerms.getDoctor().getDiscount();
        this.type = doctorTerms.getDoctor().getTip().getName();
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

    public String getFirstNameDoctor() {
        return firstNameDoctor;
    }

    public void setFirstNameDoctor(String firstNameDoctor) {
        this.firstNameDoctor = firstNameDoctor;
    }

    public String getLastNameDoctor() {
        return lastNameDoctor;
    }

    public void setLastNameDoctor(String lastNameDoctor) {
        this.lastNameDoctor = lastNameDoctor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
