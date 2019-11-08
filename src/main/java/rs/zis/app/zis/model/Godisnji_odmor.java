package rs.zis.app.zis.model;

public class Godisnji_odmor {
    private long pocetak;
    private long kraj;

    public Godisnji_odmor() {
    }

    public Godisnji_odmor(long pocetak, long kraj) {
        this.pocetak = pocetak;
        this.kraj = kraj;
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
}
