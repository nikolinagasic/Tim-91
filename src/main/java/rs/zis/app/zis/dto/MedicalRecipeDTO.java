package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.MedicalRecipe;

public class MedicalRecipeDTO {

    private Long id;
    private String sifraDijagnoze;
    private String sifraLeka;
    private String nazivLeka;
    private String nacinKoriscenja;
    private boolean overen;
    private String doctor_name;
    private Long id_nurse;          //na osnovu njega vezujem sestru koja ga je overila

    public MedicalRecipeDTO() {
    }

    public MedicalRecipeDTO(Long id, String sifraDijagnoze, String sifraLeka, String nazivLeka, String nacinKoriscenja, boolean overen, String doctor_name, Long id_nurse) {
        this.id = id;
        this.sifraDijagnoze = sifraDijagnoze;
        this.sifraLeka = sifraLeka;
        this.nazivLeka = nazivLeka;
        this.nacinKoriscenja = nacinKoriscenja;
        this.overen = overen;
        this.doctor_name = doctor_name;
        this.id_nurse = id_nurse;
    }

    public MedicalRecipeDTO(MedicalRecipe medicalRecipe) {
        this.id = medicalRecipe.getId();
        this.sifraDijagnoze = medicalRecipe.getSifraDijagnoza();
        this.sifraLeka = medicalRecipe.getSifraLeka();
        this.nazivLeka = medicalRecipe.getNazivLeka();
        this.nacinKoriscenja = medicalRecipe.getNacinKoriscenja();
        this.overen = medicalRecipe.isOveren();
        this.doctor_name = medicalRecipe.getDoctor_name();
        this.id_nurse = medicalRecipe.getNurse().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSifraDijagnoze() {
        return sifraDijagnoze;
    }

    public void setSifraDijagnoze(String sifraDijagnoze) {
        this.sifraDijagnoze = sifraDijagnoze;
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

    public Long getId_nurse() {
        return id_nurse;
    }

    public void setId_nurse(Long id_nurse) {
        this.id_nurse = id_nurse;
    }
}
