package rs.zis.app.zis.domain;

import javax.persistence.*;

@Entity
public class TermDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_term", nullable = false)
    private String startTerm;

    @Column(name = "end_term", nullable = false)
    private String endTerm;

    public TermDefinition() {
    }

    public TermDefinition(Long id, String startTerm, String endTerm) {
        this.id = id;
        this.startTerm = startTerm;
        this.endTerm = endTerm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
