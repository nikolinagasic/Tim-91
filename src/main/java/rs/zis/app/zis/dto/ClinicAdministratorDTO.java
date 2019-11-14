package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.Admin_klinike;
import rs.zis.app.zis.domain.ClinicAdministrator;
import rs.zis.app.zis.domain.Klinika;

public class ClinicAdministratorDTO {
    private Long id;
    private String mail;
    private String password;
    private String lastName;
    private String firstName;
    private String address;
    private String city;
    private String country;
    private long telephone;
    private Klinika clinic;
    public ClinicAdministratorDTO() {
    }

    public ClinicAdministratorDTO(Long id, String mail, String password, String firstName, String lastName, String address, String city, String country, long telephone, Klinika clinic) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.telephone = telephone;
        this.clinic = clinic;
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
        this.clinic = administrator.getClinic();
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

    public long getTelephone() {
        return telephone;
    }

    public Klinika getClinic() {
        return clinic;
    }
}
