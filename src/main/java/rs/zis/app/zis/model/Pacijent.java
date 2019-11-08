package rs.zis.app.zis.model;

public class Pacijent extends Korisnik{
    private String prebivaliste;
    private String grad;
    private String drzava;
    private long telefon;
    private long lbo;       // jedinstveni(licni) broj osiguranika

    public Pacijent() {
        this.setUloga(Uloga.pacijent);
    }

    public Pacijent(String mail, String lozinka, String ime, String prezime, Uloga uloga, String prebivaliste, String grad, String drzava, long telefon, long lbo) {
        super(mail, lozinka, ime, prezime, uloga);
        this.prebivaliste = prebivaliste;
        this.grad = grad;
        this.drzava = drzava;
        this.telefon = telefon;
        this.lbo = lbo;
    }

    public Pacijent(String mail, String lozinka, String ime, String prezime, Uloga uloga, String prebivaliste) {
        super(mail, lozinka, ime, prezime, uloga);
        this.prebivaliste = prebivaliste;
    }

    public String getPrebivaliste() {
        return prebivaliste;
    }

    public void setPrebivaliste(String prebivaliste) {
        this.prebivaliste = prebivaliste;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public long getTelefon() {
        return telefon;
    }

    public void setTelefon(long telefon) {
        this.telefon = telefon;
    }

    public long getLbo() {
        return lbo;
    }

    public void setLbo(long lbo) {
        this.lbo = lbo;
    }
}
