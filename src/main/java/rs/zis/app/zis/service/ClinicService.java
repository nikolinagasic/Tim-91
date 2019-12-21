package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Clinic;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.DoctorTerms;
import rs.zis.app.zis.domain.TipPregleda;
import rs.zis.app.zis.dto.ClinicDTO;
import rs.zis.app.zis.repository.ClinicRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"SpellCheckingInspection", "unused", "MalformedFormatString", "CollectionAddAllCanBeReplacedWithConstructor", "UseBulkOperation"})
@Service
public class ClinicService implements UserDetailsService {

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private AuthorityService authService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorTermsService doctorTermsService;

    @Autowired
    private TipPregledaService tipPregledaService;

    public List<Clinic> findAll() {return clinicRepository.findAll(); }

    public Clinic save(ClinicDTO clinicDTO) {
        Clinic c = new Clinic();
        c.setName(clinicDTO.getName());
        c.setAddress(clinicDTO.getAddress());
        c.setDescription(clinicDTO.getDescription());
        c.setRating(0);

        c = this.clinicRepository.save(c);
        return c;
    }

    public Clinic findOneByName(String name) {
        return clinicRepository.findOneByName(name);
    }

    public Clinic findOneById(Long id) { return clinicRepository.findOneById(id); }

    public List<Clinic> searchClinic(long datum, String tip, int ocena){
        // 1) return: doktori koji su datog tipa
        List<Doctor> doctorsType;
        if(tip.equals("Сви типови")){
            doctorsType = doctorService.findAll();
        }
        else {
            TipPregleda tipPregleda = tipPregledaService.findOneByName(tip.toLowerCase());
            doctorsType = doctorService.findDoctorByType(tipPregleda);
        }
        // 2) return: doktori koji imaju slobodnih termina u tom danu
        List<Doctor> slobodni_doktori = new ArrayList<>();
        for (Doctor d : doctorsType) {          // za sve doktore koji su tog tipa (stomatolog, urolog, ...)
            List<DoctorTerms> doctorTerm = doctorTermsService.findAllByDoctor(d);
            if(!doctorTerm.isEmpty()){          // ima zauzetih termina taj doca
                System.out.println("doktor [z]: " + d.getFirstName() + " " + d.getLastName());
//                DoctorTerms dt = doctorTermsService.findAllByDate(datum);  // proveravam da li ima nesto tog dana
                // TODO proveriti da li su sve satnice zauzete


            }
            else{                               // nema zauzetih
                slobodni_doktori.add(d);        // odmah ga upisi u listu
            }
        }

        // 3)
        Set<Clinic> retSet = new HashSet<>();
        for (Doctor d : slobodni_doktori) {
            Long id = d.getClinic().getId();
            Clinic clinic = findOneById(id);
            if(clinic.getRating() == ocena){
                retSet.add(clinic);
            }
        }

        List<Clinic> retList = new ArrayList<>();
        retList.addAll(retSet);

        return  retList;
    }

    public List<Doctor> findDoctorsByClinic(String clinic_name){
        List<Doctor> retList = new ArrayList<>();
        Clinic clinic = findOneByName(clinic_name);
        if(clinic != null) {
            for (Doctor doctor: doctorService.findAllByClinic(clinic)) {
                retList.add(doctor);
            }
        }

        return retList;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Clinic c = clinicRepository.findOneByName(name);
        if (c == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%name'.", name));
        } else {
            return (UserDetails) c;
        }
    }

}
