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
    private String role;
    private boolean firstLogin;

    public PatientDTO() {
    }

    public PatientDTO(Long id, String mail, String password, String firstName, String lastName, String address, String city,
                      String country, String telephone, long lbo, String role, boolean firstLogin) {
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
        this.role = role;
        this.firstLogin = firstLogin;
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
        this.role = patient.getRole();
        this.firstLogin = patient.isFirstLogin();
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

    public String getRole() {
        return role;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setLbo(long lbo) {
        this.lbo = lbo;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
}
