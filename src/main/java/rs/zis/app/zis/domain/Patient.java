package rs.zis.app.zis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@Entity
public class Patient extends Users {

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "lbo", nullable = false)
    private long lbo;       // jedinstveni(licni) broj osiguranika

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "patient")
    private MedicalRecord medicalRecord;

    @Column(name= "role")
    private String role;

    public Patient() {
        this.role = "patient";
        this.setActive(true);
    }

    public Patient(String mail, String password, String firstName, String lastName, String address,
                   String city, String country, String telephone, long lbo, boolean enabled,
                   Timestamp lastPasswordResetDate, List<Authority> authorities, boolean firstLogin,
                   MedicalRecord medicalRecord) {
        super(mail, password, enabled, lastPasswordResetDate, authorities, firstLogin);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.telephone = telephone;
        this.lbo = lbo;
        this.role = "patient";
        this.medicalRecord = medicalRecord;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public long getLbo() {
        return lbo;
    }

    public void setLbo(long lbo) {
        this.lbo = lbo;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}
