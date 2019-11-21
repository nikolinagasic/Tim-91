package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.ClinicCentreAdmin;

public class ClinicCentreAdminDTO {

    private Long id;
    private String mail;
    private String password;


    public ClinicCentreAdminDTO() {

    }

    public ClinicCentreAdminDTO(Long id, String mail, String password) {
        this.id = id;
        this.mail = mail;
        this.password = password;

    }

    public ClinicCentreAdminDTO(ClinicCentreAdmin cadmin) {
        this.id = cadmin.getId();
        this.mail = cadmin.getMail();
        this.password = cadmin.getPassword();
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
