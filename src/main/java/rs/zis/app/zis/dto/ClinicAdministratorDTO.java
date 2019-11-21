package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.ClinicAdministrator;

public class ClinicAdministratorDTO {
    private Long id;
    private String mail;
    private String password;

    public ClinicAdministratorDTO() {
    }

    public ClinicAdministratorDTO(Long id, String mail, String password) {
        this.id = id;
        this.mail = mail;
        this.password = password;
    }

    public ClinicAdministratorDTO(ClinicAdministrator administrator) {
        this.id = administrator.getId();
        this.mail = administrator.getMail();
        this.password = administrator.getPassword();
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

}
