package rs.zis.app.zis.domain;

import javax.persistence.*;

@Entity
public class TermDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_shift", nullable = false)      // smena
    private int workShift;

    @Column(name = "start_term", nullable = false)
    private String startTerm;

    @Column(name = "end_term", nullable = false)
    private String endTerm;

    public TermDefinition() {
    }

    public TermDefinition(Long id, int workShift, String startTerm, String endTerm) {
        this.id = id;
        this.workShift = workShift;
        this.startTerm = startTerm;
        this.endTerm = endTerm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWorkShift() {
        return workShift;
    }

    public void setWorkShift(int workShift) {
        this.workShift = workShift;
    }

    public String getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(String startTerm) {
        this.startTerm = startTerm;
    }

    public String getEndTerm() {
        return endTerm;
    }

    public void setEndTerm(String endTerm) {
        this.endTerm = endTerm;
    }
}
