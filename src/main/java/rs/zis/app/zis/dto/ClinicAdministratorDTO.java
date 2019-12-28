package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.ClinicAdministrator;

public class ClinicAdministratorDTO {
    private Long id;
    private String mail;
    private String password;
    private String lastName;
    private String firstName;
    private String address;
    private String city;
    private String country;
    private String telephone;
    private String role;
    private String clinic;
    private boolean firstLogin;

    public ClinicAdministratorDTO() {
    }

    public ClinicAdministratorDTO(Long id, String mail, String password,String firstName, String lastName, String address, String city,
                                  String country, String telephone, String clinic, String role, boolean firstLogin) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.telephone = telephone;
        this.role = role;
        this.clinic = clinic;
        this.firstLogin = firstLogin;
    }

    public ClinicAdministratorDTO(ClinicAdministrator administrator) {
        this.id = administrator.getId();
        this.mail = administrator.getMail();
        this.password = administrator.getPassword();
        this.firstName = administrator.getFirstName();
        this.lastName = administrator.getLastName();
        this.address = administrator.getAddress();
        this.city = administrator.getCity();
        this.country = administrator.getCountry();
        this.telephone = administrator.getTelephone();
        this.role = administrator.getRole();
        this.clinic = administrator.getClinic().getName(); //privremeno
        this.firstLogin = administrator.isFirstLogin();
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getClinic() {
        return clinic;
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

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }
}
