package rs.zis.app.zis.domain;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class Sala {
    private String tip_termina;
    private int ID;

    public Sala() {
    }

    public Sala(String tip, int ID) {
        this.tip_termina = tip;
        this.ID = ID;
    }

    public String getTip_termina() {
        return tip_termina;
    }

    public void setTip_termina(String tip_termina) {
        this.tip_termina = tip_termina;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
