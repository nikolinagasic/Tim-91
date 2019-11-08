package rs.zis.app.zis.model;

import java.util.ArrayList;

public class Lekar extends Korisnik{
    private String oblast;
    private ArrayList<Pacijent> lista_pacijenata;
    private ArrayList<Termin> lista_termina;        // zakazani termini pregleda/operacija
    private Klinika klinika;                     // klinika u kojoj je zaposlen
    private ArrayList<Godisnji_odmor> godisnji_odmor;

    public Lekar() {
        this.setUloga(Uloga.lekar);
        lista_pacijenata = new ArrayList<Pacijent>();
        lista_termina = new ArrayList<Termin>();
        godisnji_odmor = new ArrayList<Godisnji_odmor>();
    }

    public Lekar(String mail, String lozinka, String ime, String prezime, Uloga uloga, String oblast, ArrayList<Pacijent> lista_pacijenata, ArrayList<Termin> lista_termina, Klinika klinika, ArrayList<Godisnji_odmor> godisnji) {
        super(mail, lozinka, ime, prezime, uloga);
        this.oblast = oblast;
        this.lista_pacijenata = lista_pacijenata;
        this.lista_termina = lista_termina;
        this.klinika = klinika;
        this.godisnji_odmor = godisnji;
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

    public String getOblast() {
        return oblast;
    }

    public void setOblast(String oblast) {
        this.oblast = oblast;
    }

    public ArrayList<Pacijent> getLista_pacijenata() {
        return lista_pacijenata;
    }

    public void setLista_pacijenata(ArrayList<Pacijent> lista_pacijenata) {
        this.lista_pacijenata = lista_pacijenata;
    }

    public ArrayList<Termin> getLista_termina() {
        return lista_termina;
    }

    public void setLista_termina(ArrayList<Termin> lista_termina) {
        this.lista_termina = lista_termina;
    }
}
