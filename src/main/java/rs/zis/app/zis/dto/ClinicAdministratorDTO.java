package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.ClinicAdministrator;

public class ClinicAdministratorDTO {
    private Long id;
    private String mail;
    private String password;
    private String role;
    private boolean firstLogin;

    public ClinicAdministratorDTO() {
    }

    public ClinicAdministratorDTO(Long id, String mail, String password, String role, boolean firstLogin) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.role = role;
        this.firstLogin = firstLogin;
    }

    public ClinicAdministratorDTO(ClinicAdministrator administrator) {
        this.id = administrator.getId();
        this.mail = administrator.getMail();
        this.password = administrator.getPassword();
        this.role = administrator.getRole();
        this.firstLogin = administrator.isFirstLogin();
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

    public String getRole() {
        return role;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }
}
