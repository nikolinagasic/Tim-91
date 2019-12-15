package rs.zis.app.zis.domain;

@SuppressWarnings("SpellCheckingInspection")
public class Termin {
    private long pocetak;
    private long kraj;
    private String sala;
    private String tip;

    public Termin() {
    }

    public Termin(long pocetak, long kraj, String sala, String tip) {
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.sala = sala;
        this.tip = tip;
    }

    public long getPocetak() {
        return pocetak;
    }

    public void setPocetak(long pocetak) {
        this.pocetak = pocetak;
    }

    public long getKraj() {
        return kraj;
    }

    public void setKraj(long kraj) {
        this.kraj = kraj;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
