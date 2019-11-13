package rs.zis.app.zis.domain;

enum Uloga{
    lekar,
    pacijent,
    medicinska_sestra,
    admin_klinike,
    admin_KC
}
public class Korisnik {
    private String mail;
    private String lozinka;
    private String ime;
    private String prezime;
    private Uloga uloga;   // lekar, pacijent, medicinska_sestra, admin_klinike, admin_KC

    public Korisnik() {
    }

    public Korisnik(String mail, String lozinka, String ime, String prezime, Uloga uloga) {
        this.mail = mail;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.uloga = uloga;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }
}
