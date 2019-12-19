package rs.zis.app.zis.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@Entity
public class DoctorTerms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "start_term", nullable = false)
    private String start_term;

    @Column(name = "end_term", nullable = false)
    private String end_term;

    // formira se medjutabela koja mi odslikava koji je doktor zauzet u kom terminu
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Doctor doctor;

    public DoctorTerms() {
    }

    public DoctorTerms(Long id, String date, String start_term, String end_term) {
        this.id = id;
        this.date = date;
        this.start_term = start_term;
        this.end_term = end_term;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
