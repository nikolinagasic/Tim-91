package rs.zis.app.zis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name= "MedicalRecord")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "height")
    private int height;

    @Column(name= "weight")
    private int weight;

    @Column(name= "dioptreRightEye")
    private float dioptreRightEye;

    @Column(name="dioptreLeftEye")
    private float dioptreLeftEye;

    @Column(name= "bloodGroup")
    private String bloodGroup;

    @Column(name= "allergy")
    private String allergy;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Patient patient;

    //formiram medjutabelu kartoni-izvestaji
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MedicalReview> medicalReviews = new HashSet<MedicalReview>();

    public MedicalRecord(){

    }

    public MedicalRecord(Long id, int height, int weight, float dioptreRightEye, float dioptreLeftEye, String bloodGroup, String allergy, Patient patient,
                         Set<MedicalReview>medicalReviews) {
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.dioptreRightEye = dioptreRightEye;
        this.dioptreLeftEye = dioptreLeftEye;
        this.bloodGroup = bloodGroup;
        this.allergy = allergy;
        this.patient = patient;
        this.medicalReviews = medicalReviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public float getDioptreRightEye() {
        return dioptreRightEye;
    }

    public void setDioptreRightEye(float dioptreRightEye) {
        this.dioptreRightEye = dioptreRightEye;
    }

    public float getDioptreLeftEye() {
        return dioptreLeftEye;
    }

    public void setDioptreLeftEye(float dioptreLeftEye) {
        this.dioptreLeftEye = dioptreLeftEye;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getPatintMail(){return patient.getMail();}

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Set<MedicalReview> getMedicalReviews() {
        return medicalReviews;
    }

    public void addMedicalReviews(MedicalReview medicalReview) {
        this.medicalReviews.add(medicalReview);
    }
}
