package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.ClinicCentreAdmin;
import rs.zis.app.zis.repository.ClinicCentreAdminRepository;

import java.util.List;

@Service
public class ClinicCentreAdminService {
    @Autowired
    private ClinicCentreAdminRepository cc_adminRepository;

    public ClinicCentreAdmin findOne(Long id) {
        return cc_adminRepository.findById(id).orElseGet(null);
    }

    public List<ClinicCentreAdmin> findAll() {
        return cc_adminRepository.findAll();
    }

    public Page<ClinicCentreAdmin> findAll(Pageable page) {
        return cc_adminRepository.findAll(page);
    }

  /*  public static ClinicCentreAdmin findByMail(String mail) {
        return cc_adminRepository.findOneByMail(mail);
    }*/

    public List<ClinicCentreAdmin> findByLastName(String lastName) {
        return cc_adminRepository.findAllByLastName(lastName);
    }

    public ClinicCentreAdmin save(ClinicCentreAdmin admin) {
        return cc_adminRepository.save(admin);
    }

    public void remove(Long mail) {
        cc_adminRepository.deleteById(mail);
    }





}
