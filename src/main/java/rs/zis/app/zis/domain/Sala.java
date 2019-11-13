package rs.zis.app.zis.domain;

public class Sala {
    private Tip_termina tip;
    private int ID;

    public Sala() {
    }

    public Sala(Tip_termina tip, int ID) {
        this.tip = tip;
        this.ID = ID;
    }

    public Tip_termina getTip() {
        return tip;
    }

    public void setTip(Tip_termina tip) {
        this.tip = tip;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
