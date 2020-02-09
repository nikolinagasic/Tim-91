package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.ClinicAdministratorDTO;
import rs.zis.app.zis.repository.ClinicAdministratorRepository;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
@Service
public class ClinicAdministratorService implements UserDetailsService {

    @Autowired
    private ClinicAdministratorRepository clinicAdministartorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;
    @Autowired
    private ClinicService clinicService;
    @Autowired
    private DoctorTermsService doctorTermsService;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private VacationService vacationService;


    public List<ClinicAdministrator> findAll() {
        return clinicAdministartorRepository.findAll();
    }

    public Page<ClinicAdministrator> findAll(Pageable page) {
        return clinicAdministartorRepository.findAll(page);
    }

    public List<ClinicAdministrator> findAllByClinic(Clinic clinic) {
        return clinicAdministartorRepository.findAllByClinic(clinic);
    }

    public ClinicAdministrator save(ClinicAdministratorDTO clinicAdministartorDTO) {
        ClinicAdministrator a = new ClinicAdministrator();
        a.setMail(clinicAdministartorDTO.getMail());
        a.setPassword(passwordEncoder.encode(clinicAdministartorDTO.getPassword()));
        a.setFirstName(clinicAdministartorDTO.getFirstName());
        a.setLastName(clinicAdministartorDTO.getLastName());
        a.setAddress(clinicAdministartorDTO.getAddress());
        a.setCity(clinicAdministartorDTO.getCity());
        a.setCountry(clinicAdministartorDTO.getCountry());
        a.setTelephone(clinicAdministartorDTO.getTelephone());
        a.setClinic(clinicService.findOneByName(clinicAdministartorDTO.getClinic()));
        a.setEnabled(true);
        a.setFirstLogin(true);
        List<Authority> auth = authService.findByname("ROLE_CADMIN");
        a.setAuthorities(auth);

        a = this.clinicAdministartorRepository.save(a);
        return a;
    }
    public ClinicAdministrator update(ClinicAdministrator clinicAdministrator){
        return clinicAdministartorRepository.save(clinicAdministrator);
    }

    public void remove(Long id) {
        clinicAdministartorRepository.deleteById(id);
    }

    public ClinicAdministrator findOneByMail(String mail) {
        return clinicAdministartorRepository.findOneByMail(mail);
    }


    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        ClinicAdministrator d = clinicAdministartorRepository.findOneByMail(mail);
        if (d == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%mail'.", mail));
        } else {
            return (UserDetails) d;
        }
    }

    public boolean checkFirstLastName(String mail, String firstName, String lastName){
        ClinicAdministrator cadmin = clinicAdministartorRepository.findOneByMail(mail);
        if(cadmin != null){
            if(cadmin.getFirstName().equals(firstName) && cadmin.getLastName().equals(lastName)){
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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void obradiZahtev(Long id, boolean odobren, String razlog) {
        String body;
        Vacation vacation = vacationService.findOneById(id);
        if (odobren) {
            vacation.setEnabled(true);
            body = "Postovani,\nVas zahtev za godisnji odmor je odobren.";
        } else {
            vacation.setActive(false);
            body = "Postovani,\nVas zahtev za godisnji odmor je odbijen.\nRazlog: "+razlog;
        }

        vacationService.update(vacation);
        if(vacation.getDoctor_nurse().equals("doctor")) {
            notificationService.SendNotification(vacation.getDoctor().getMail(), "billypiton43@gmail.com", "OBAVESTENJE", body);
        }else{
            notificationService.SendNotification(vacation.getNurse().getMail(), "billypiton43@gmail.com", "OBAVESTENJE", body);
        }
    }
}