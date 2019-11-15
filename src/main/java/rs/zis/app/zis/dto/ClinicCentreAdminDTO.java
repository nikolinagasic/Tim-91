package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.ClinicCentreAdmin;

public class ClinicCentreAdminDTO {

    private Long id;
    private String mail;
    private String password;
    private boolean predefined;
    private String firstName;
    private String lastName;

    public ClinicCentreAdminDTO() {

    }

    public ClinicCentreAdminDTO(Long id, String mail, String password, boolean predefined, String firstName, String lastName) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.predefined = predefined;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ClinicCentreAdminDTO(ClinicCentreAdmin cadmin) {
        this.id = cadmin.getId();
        this.mail = cadmin.getMail();
        this.password = cadmin.getPassword();
        this.predefined = cadmin.isPredefined();
        this.firstName = cadmin.getFirstName();
        this.lastName = cadmin.getLastName();
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

    public boolean isPredefined() {
        return predefined;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
