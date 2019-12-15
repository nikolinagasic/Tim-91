package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.TipPregleda;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class TipPregledaDTO {
    private Long id;
    private String name;

    public TipPregledaDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TipPregledaDTO(TipPregleda tip) {
        this.id = tip.getId();
        this.name = tip.getName();
    }

    public TipPregledaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
