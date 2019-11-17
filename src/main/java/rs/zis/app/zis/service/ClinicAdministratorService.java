package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.ClinicAdministrator;
import rs.zis.app.zis.domain.Klinika;
import rs.zis.app.zis.repository.ClinicAdministratorRepository;

@Service
public class ClinicAdministratorService {

    @Autowired
    private ClinicAdministratorRepository clinicAdministratorRepository;

    /*  public List<ClinicAdministrator> findAll() {
          return clinicAdministratorRepository.findAll();
      }
    */
    public Page<ClinicAdministrator> findAll(Pageable page) {
        return clinicAdministratorRepository.findAll(page);
    }

    public ClinicAdministrator save(ClinicAdministrator clinicAdministrator) {
        return clinicAdministratorRepository.save(clinicAdministrator);
    }

//    public ClinicAdministrator findOneByClinic(Klinika clinic) {
//        return clinicAdministratorRepository.findOneByClinic(clinic);
//    }
}