package rs.zis.app.zis.domain;

import org.springframework.data.annotation.Id;
import javax.persistence.*;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "address", unique = true, nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "telephone", nullable = false)
    private long telephone;

    @Column(name = "lbo", unique = true, nullable = false)
    private long lbo;       // jedinstveni(licni) broj osiguranika

    public Patient() {
    }

    public Patient(Long id, String mail, String password, String firstName, String lastName, String address, String city, String country, long telephone, long lbo) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.telephone = telephone;
        this.lbo = lbo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public long getTelephone() {
        return telephone;
    }

    public void setTelephone(long telephone) {
        this.telephone = telephone;
    }

    public long getLbo() {
        return lbo;
    }

    public void setLbo(long lbo) {
        this.lbo = lbo;
    }
}
