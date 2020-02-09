package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.ClinicCentreAdmin;

public class ClinicCentreAdminDTO {

    private Long id;
    private String mail;
    private String firstName;
    private String lastName;
    private String password;
    private String role;
    private boolean firstLogin;

    public ClinicCentreAdminDTO() {

    }

    public ClinicCentreAdminDTO(Long id, String mail, String firstName, String lastName, String password, String role, boolean firstLogin) {
        this.id = id;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.firstLogin = firstLogin;
    }

    public ClinicCentreAdminDTO(ClinicCentreAdmin cadmin) {
        this.id = cadmin.getId();
        this.mail = cadmin.getMail();
        this.firstName = cadmin.getFirstName();
        this.lastName = cadmin.getLastName();
        this.password = cadmin.getPassword();
        this.role = cadmin.getRole();
        this.firstLogin = cadmin.isFirstLogin();
    }

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getFirstName() {return firstName; }

    public String getLastName() { return lastName; }

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
