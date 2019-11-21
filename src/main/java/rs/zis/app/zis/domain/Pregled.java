package rs.zis.app.zis.domain;

public class Pregled {
    private long datum_vreme;
    private long trajanje;
    private String tip_pregleda;
    private Sala sala;
    private Doctor lekar;
    private float cena;

    public Pregled() {
    }

    public Pregled(long datum_vreme, long trajanje, String tip_pregleda, Sala sala, Doctor lekar, float cena) {
        this.datum_vreme = datum_vreme;
        this.trajanje = trajanje;
        this.tip_pregleda = tip_pregleda;
        this.sala = sala;
        this.lekar = lekar;
        this.cena = cena;
    }

    public long getDatum_vreme() {
        return datum_vreme;
    }

    public void setDatum_vreme(long datum_vreme) {
        this.datum_vreme = datum_vreme;
    }

    public long getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(long trajanje) {
        this.trajanje = trajanje;
    }

    public String getTip_pregleda() {
        return tip_pregleda;
    }

    public void setTip_pregleda(String tip_pregleda) {
        this.tip_pregleda = tip_pregleda;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Doctor getLekar() {
        return lekar;
    }

    public void setLekar(Doctor lekar) {
        this.lekar = lekar;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }
}
