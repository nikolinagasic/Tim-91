package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Authority;
import rs.zis.app.zis.domain.ClinicAdministrator;
import rs.zis.app.zis.domain.ClinicCentreAdmin;
import rs.zis.app.zis.dto.ClinicAdministratorDTO;
import rs.zis.app.zis.dto.ClinicCentreAdminDTO;
import rs.zis.app.zis.repository.ClinicCentreAdminRepository;

import java.util.List;


@Service
public class ClinicCentreAdminService {
    @Autowired
    private ClinicCentreAdminRepository cc_adminRepository;
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AuthorityService authService;

    public ClinicCentreAdmin findOne(Long id) {
        return cc_adminRepository.findById(id).orElseGet(null);
    }

    public List<ClinicCentreAdmin> findAll() {
        return cc_adminRepository.findAll();
    }

    public Page<ClinicCentreAdmin> findAll(Pageable page) {
        return cc_adminRepository.findAll(page);
    }

    public ClinicCentreAdmin findOneByMail(String mail) {
        return cc_adminRepository.findOneByMail(mail);
    }

    public List<ClinicCentreAdmin> findAllByLastName(String lastName) {
        return cc_adminRepository.findAllByLastName(lastName);
    }

    public ClinicCentreAdmin save(ClinicCentreAdmin admin) {
        return cc_adminRepository.save(admin);
    }
    public ClinicCentreAdmin save(ClinicCentreAdminDTO clinicCentreAdminDTO) {
        ClinicCentreAdmin a = new ClinicCentreAdmin();
        a.setMail(clinicCentreAdminDTO.getMail());
        a.setPassword(applicationContext.getBean(PasswordEncoder.class).encode(clinicCentreAdminDTO.getPassword()));
        a.setPredefined(false);
        a.setEnabled(true);
        List<Authority> auth = authService.findByname("ROLE_CLINIC_CENTRE_ADMINISTRATOR");
        a.setAuthorities(auth);

        a = this.cc_adminRepository.save(a);
        return a;
    }
    public void remove(Long mail) {
        cc_adminRepository.deleteById(mail);
    }

    public ClinicCentreAdmin update(ClinicCentreAdmin clinicCentreAdmin){
        return cc_adminRepository.save(clinicCentreAdmin);
    }

    public boolean checkFirstLastName(String mail, String firstName, String lastName){
        ClinicCentreAdmin ccadmin = cc_adminRepository.findOneByMail(mail);
        if(ccadmin != null){
            if(ccadmin.getFirstName().equals(firstName) && ccadmin.getLastName().equals(lastName)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

}
