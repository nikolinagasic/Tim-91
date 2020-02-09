package rs.zis.app.zis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name= "MedicalRecipe")
public class MedicalRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="sifraDijagnoze")
    private String sifraDijagnoza;

    @Column(name="sifraLeka")
    private String sifraLeka;

    @Column(name="nazivLeka")
    private String nazivLeka;

    @Column(name="nacinKoriscenja")
    private String nacinKoriscenja;

    @Column(name="overen")
    private boolean overen;    //da li je recept overen

    @Column(name="doctor_name")
    private String doctor_name; //info koji je doktor propisao recept

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Nurse nurse;  //koja je sestra overila recept

    @Version
    private Long version;

    public MedicalRecipe() {
    }

    public MedicalRecipe(Long id, String sifraDijagnoza, String sifraLeka, String nazivLeka, String nacinKoriscenja, boolean overen, String doctor_name, Nurse nurse) {
        this.id = id;
        this.sifraDijagnoza = sifraDijagnoza;
        this.sifraLeka = sifraLeka;
        this.nazivLeka = nazivLeka;
        this.nacinKoriscenja = nacinKoriscenja;
        this.overen = overen;
        this.doctor_name = doctor_name;
        this.nurse = nurse;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSifraDijagnoza() {
        return sifraDijagnoza;
    }

    public void setSifraDijagnoza(String sifraDijagnoza) {
        this.sifraDijagnoza = sifraDijagnoza;
    }

    public String getSifraLeka() {
        return sifraLeka;
    }

    public void setSifraLeka(String sifraLeka) {
        this.sifraLeka = sifraLeka;
    }

    public String getNazivLeka() {
        return nazivLeka;
    }

    public void setNazivLeka(String nazivLeka) {
        this.nazivLeka = nazivLeka;
    }

    public String getNacinKoriscenja() {
        return nacinKoriscenja;
    }

    public void setNacinKoriscenja(String nacinKoriscenja) {
        this.nacinKoriscenja = nacinKoriscenja;
    }

    public boolean isOveren() {
        return overen;
    }

    public void setOveren(boolean overen) {
        this.overen = overen;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }
}
