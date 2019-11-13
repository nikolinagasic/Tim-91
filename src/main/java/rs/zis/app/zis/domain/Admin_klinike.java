package rs.zis.app.zis.domain;

public class Admin_klinike extends Korisnik{
    private Klinika klinika;        // klinika kojom upravlja
    private Izvestaj izvestaj;      // izvestaj o klinici koju vodi

    public Admin_klinike(Klinika klinika, Izvestaj izvestaj) {
        this.setUloga(Uloga.admin_klinike);
        this.klinika = klinika;
        this.izvestaj = izvestaj;
    }

    public Admin_klinike(String mail, String lozinka, String ime, String prezime, Uloga uloga, Klinika klinika, Izvestaj izvestaj) {
        super(mail, lozinka, ime, prezime, uloga);
        this.klinika = klinika;
        this.izvestaj = izvestaj;
    }

    public Klinika getKlinika() {
        return klinika;
    }

    public void setKlinika(Klinika klinika) {
        this.klinika = klinika;
    }

    public Izvestaj getIzvestaj() {
        return izvestaj;
    }

    public void setIzvestaj(Izvestaj izvestaj) {
        this.izvestaj = izvestaj;
    }
}
