package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.Patient;

public class PatientDTO {
    private Long id;
    private String mail;
    private String password;
    private String lastName;
    private String firstName;
    private String address;
    private String city;
    private String country;
    private String telephone;
    private long lbo;

    public PatientDTO() {
    }

    public PatientDTO(Long id, String mail, String password, String firstName, String lastName, String address, String city, String country, String telephone, long lbo) {
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

    public PatientDTO(Patient patient) {
        this.id = patient.getId();
        this.mail = patient.getMail();
        this.password = patient.getPassword();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.address = patient.getAddress();
        this.city = patient.getCity();
        this.country = patient.getCountry();
        this.telephone = patient.getTelephone();
        this.lbo = patient.getLbo();
    }

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getTelephone() {
        return telephone;
    }

    public long getLbo() {
        return lbo;
    }
}
