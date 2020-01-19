package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.TermDefinition;

public class TermDefinitionDTO {

    private Long id;
    private int work_shift;
    private String start_term;
    private String end_term;

    public TermDefinitionDTO() {
    }

    public TermDefinitionDTO(Long id, int work_shift, String start_term, String end_term) {
        this.id = id;
        this.work_shift = work_shift;
        this.start_term = start_term;
        this.end_term = end_term;
    }

    public TermDefinitionDTO(TermDefinition termDefinition) {
        this.id = termDefinition.getId();
        this.work_shift = termDefinition.getWorkShift();
        this.start_term = termDefinition.getStartTerm();
        this.end_term = termDefinition.getEndTerm();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWork_shift() {
        return work_shift;
    }

    public void setWork_shift(int work_shift) {
        this.work_shift = work_shift;
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
