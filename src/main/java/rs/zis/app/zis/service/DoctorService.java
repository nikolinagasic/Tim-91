package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.DoctorDTO;
import rs.zis.app.zis.repository.DoctorRepository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SpellCheckingInspection", "unused", "UnusedReturnValue", "RedundantIfStatement", "RedundantSuppression"})
@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorTermsService doctorTermsService;

    @Autowired
    private VacationService vacationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Page<Doctor> findAll(Pageable page) {
        return doctorRepository.findAll(page);
    }

    public Doctor save(DoctorDTO doctorDTO) {
        Doctor d = new Doctor();
        d.setMail(doctorDTO.getMail());
        d.setPassword(passwordEncoder.encode(doctorDTO.getPassword()));
        d.setEnabled(true);
        List<Authority> auth = authService.findByname("ROLE_DOCTOR");
        d.setAuthorities(auth);

        d = this.doctorRepository.save(d);
        return d;
    }

    public void remove(Long id) {
        doctorRepository.deleteById(id);
    }

    public Doctor findOneByMail(String mail) {
        return doctorRepository.findOneByMail(mail);
    }

    public List<Doctor> findDoctorByLastName(String lastName) {
        return doctorRepository.findDoctorByLastName(lastName);
    }
    public Doctor update(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    public boolean checkFirstLastName(String mail, String firstName, String lastName){
        Doctor doctor = doctorRepository.findOneByMail(mail);
        if(doctor != null){
            if(doctor.getFirstName().equals(firstName) && doctor.getLastName().equals(lastName)){
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
    public Doctor findOneById(Long id){return doctorRepository.findOneById(id); }

    public List<Doctor> findDoctorByType(TipPregleda tp){
        return doctorRepository.findAllByTip(tp);
    }

    public List<Doctor> findAllByClinic(Clinic c) { return doctorRepository.findAllByClinic(c); }

    public List<DoctorDTO> searchDoctors(List<DoctorDTO> lista_lekara, String ime, String prezime, double ocena) {
        List<DoctorDTO> retList = new ArrayList<>();
        for (DoctorDTO doctorDTO: lista_lekara) {
            if(doctorDTO.getFirstName().toLowerCase().contains(ime.toLowerCase())){
                if(doctorDTO.getLastName().toLowerCase().contains(prezime.toLowerCase())){
                    if(ocena != -1) {
                        if (doctorDTO.getRating() == ocena) {
                            retList.add(doctorDTO);
                        }
                    }
                    else{
                        retList.add(doctorDTO);
                    }
                }
            }
        }

        return retList;
    }

    public Doctor findDoctorByFirstNameAndLastName(String ime, String prezime) {
        return doctorRepository.findDoctorByFirstNameAndLastName(ime, prezime);
    }

    public List<DoctorDTO> filterDoctor(List<DoctorDTO> lista_lekara, String ocOd, String ocDo){
        List<DoctorDTO> retList = new ArrayList<>();
        double ocenaOd, ocenaDo;
        if(ocOd.equals("min")){
            ocenaOd = 0;
        }else{
            ocenaOd = Double.parseDouble(ocOd);
        }
        if(ocDo.equals("max")){
            ocenaDo = Double.MAX_VALUE;
        }else{
            ocenaDo = Double.parseDouble(ocDo);
        }

        for (DoctorDTO doctorDTO: lista_lekara) {
            if(doctorDTO.getRating() >= ocenaOd && doctorDTO.getRating() <= ocenaDo){
                retList.add(doctorDTO);
            }
        }

        return retList;
    }


    public List<DoctorDTO> expandedSearchDoctor(String ime, String prezime, double ocena, long datum, String tip,
                                                List<DoctorDTO> lista_lekara){
        List<DoctorDTO> retList = new ArrayList<>();
        if(ime.equals("~")){
            ime = "";
        }if(prezime.equals("~")){
            prezime = "";
        }if(tip.equals("Сви типови")){
            tip = "";
        }

        for (DoctorDTO doctorDTO : lista_lekara) {
            Doctor doctor = findOneById(doctorDTO.getId());
            if(doctor.getTip().getName().toLowerCase().contains(tip.toLowerCase())){
                if(doctor_free_at_date(doctor, datum) || datum == -1){
                    if(doctor.getRating() == ocena || ocena == -1){
                        if(doctor.getLastName().toLowerCase().contains(prezime.toLowerCase())){
                            if(doctor.getFirstName().toLowerCase().contains(ime.toLowerCase())){
                                retList.add(doctorDTO);
                            }
                        }
                    }
                }
            }
        }

        return retList;
    }

    private boolean doctor_free_at_date(Doctor doctor, long datum) {
        List<DoctorTerms> doctorTermsList = doctorTermsService.findAllByDoctor(doctor);

        boolean godisnji = false;
        for (Vacation vacation : vacationService.findAllByDoctor(doctor)) {
            if(vacation.isActive() && datum >= vacation.getPocetak() && datum <= vacation.getKraj()){
                godisnji = true;
            }
        }

        if(!godisnji) {
            int term_counter = 0;
            for (DoctorTerms doctorTerms : doctorTermsList) {
                if (doctorTerms.getDate() == datum) {
                    term_counter++;
                }
            }

            if (term_counter == 10) {
                return false;
            }
        }else{
            return false;
        }

        return true;
    }

}

