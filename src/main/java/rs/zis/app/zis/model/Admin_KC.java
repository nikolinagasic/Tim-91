package rs.zis.app.zis.model;

public class Admin_KC extends Korisnik{


    public Admin_KC() {
        this.setUloga(Uloga.admin_KC);
    }

    public Admin_KC(String mail, String lozinka, String ime, String prezime, Uloga uloga) {
        super(mail, lozinka, ime, prezime, uloga);
    }
}
