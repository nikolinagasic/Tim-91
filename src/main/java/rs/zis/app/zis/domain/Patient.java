package rs.zis.app.zis.domain;

import java.sql.Timestamp;

import javax.persistence.*;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
@Entity
public class Patient extends User {

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

    @Column(name = "lbo", unique = true, nullable = false)
    private long lbo;       // jedinstveni(licni) broj osiguranika

    public Patient() {
    }

    public Patient(String mail, String password, String firstName, String lastName, String address,
                   String city, String country, String telephone, long lbo, boolean enabled,
                   Timestamp lastPasswordResetDate, List<Authority> authorities) {
        super(mail, password, enabled, lastPasswordResetDate, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.telephone = telephone;
        this.lbo = lbo;
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
}
