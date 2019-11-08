package rs.zis.app.zis.model;

import java.util.ArrayList;

public class Medicinska_sestra extends Korisnik{
    private ArrayList<Godisnji_odmor> godisnji_odmor;
    private Klinika klinika;

    public Medicinska_sestra() {
        this.setUloga(Uloga.medicinska_sestra);
        godisnji_odmor = new ArrayList<Godisnji_odmor>();
    }

    public Medicinska_sestra(String mail, String lozinka, String ime, String prezime, Uloga uloga, ArrayList<Godisnji_odmor> godisnji_odmor, Klinika klinika) {
        super(mail, lozinka, ime, prezime, uloga);
        this.godisnji_odmor = godisnji_odmor;
        this.klinika = klinika;
    }

    public ArrayList<Godisnji_odmor> getGodisnji_odmor() {
        return godisnji_odmor;
    }

    public void setGodisnji_odmor(ArrayList<Godisnji_odmor> godisnji_odmor) {
        this.godisnji_odmor = godisnji_odmor;
    }

    public Klinika getKlinika() {
        return klinika;
    }

    public void setKlinika(Klinika klinika) {
        this.klinika = klinika;
    }
}
