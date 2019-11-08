package rs.zis.app.zis.model;

import java.util.ArrayList;

public class Klinika {
    private String naziv;
    private String adresa;
    private String opis;
    private ArrayList<Lekar> lista_lekara;
    private ArrayList<Sala> lista_sala;
    private ArrayList<Termin> slobodni_termini;
    private ArrayList<Float> cenovnik;

    public Klinika() {
        lista_lekara = new ArrayList<Lekar>();
        lista_sala = new ArrayList<Sala>();
        slobodni_termini = new ArrayList<Termin>();
        cenovnik = new ArrayList<Float>();
    }

    public Klinika(String naziv, String adresa, String opis, ArrayList<Lekar> lista_lekara, ArrayList<Sala> lista_sala, ArrayList<Termin> slobodni_termini, ArrayList<Float> cenovnik) {
        this.naziv = naziv;
        this.adresa = adresa;
        this.opis = opis;
        this.lista_lekara = lista_lekara;
        this.lista_sala = lista_sala;
        this.slobodni_termini = slobodni_termini;
        this.cenovnik = cenovnik;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public ArrayList<Lekar> getLista_lekara() {
        return lista_lekara;
    }

    public void setLista_lekara(ArrayList<Lekar> lista_lekara) {
        this.lista_lekara = lista_lekara;
    }

    public ArrayList<Sala> getLista_sala() {
        return lista_sala;
    }

    public void setLista_sala(ArrayList<Sala> lista_sala) {
        this.lista_sala = lista_sala;
    }

    public ArrayList<Termin> getSlobodni_termini() {
        return slobodni_termini;
    }

    public void setSlobodni_termini(ArrayList<Termin> slobodni_termini) {
        this.slobodni_termini = slobodni_termini;
    }

    public ArrayList<Float> getCenovnik() {
        return cenovnik;
    }

    public void setCenovnik(ArrayList<Float> cenovnik) {
        this.cenovnik = cenovnik;
    }
}
