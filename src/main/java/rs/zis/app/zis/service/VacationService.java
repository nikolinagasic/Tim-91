package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.*;
import rs.zis.app.zis.dto.RoomDTO;
import rs.zis.app.zis.dto.VacationDTO;
import rs.zis.app.zis.repository.VacationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class VacationService {

    @Autowired
    private VacationRepository vacationRepository;
    @Autowired
    private DoctorService doctorService;

    public List<Vacation> findAll() {
        List<Vacation> vacationList = new ArrayList<>();
        for (Vacation vacation : vacationRepository.findAll()) {
            if(vacation.isActive()){
                vacationList.add(vacation);
            }
        }

        return vacationList;
    }

    public Page<Vacation> findAll(Pageable page) {
        return vacationRepository.findAll(page);
    }

    public void remove(Long id) {
        vacationRepository.deleteById(id);
    }

    public Vacation findOneById(Long id) {
        return vacationRepository.findOneById(id);
    }

    public List<Vacation> findAllByDoctor(Doctor doctor) {
        return vacationRepository.findAllByDoctor(doctor);
    }

    public List<Vacation> findAllByNurse(Nurse nurse) {return vacationRepository.findAllByNurse(nurse);}

    public Vacation update(Vacation u) {return vacationRepository.save(u);}
    public Vacation saveDoctorVocation(VacationDTO vacationDTO,Doctor doctor) {
        Vacation c = new Vacation();
        c.setPocetak(vacationDTO.getPocetak());
        c.setKraj(vacationDTO.getKraj());
        c.setDoctor(doctor);
        c.setActive(true);
        c.setEnabled(false);
        c.setDoctor_nurse("doctor");
        c = this.vacationRepository.save(c);
        return c;
    }

    public Vacation saveNurseVocation(VacationDTO vacationDTO, Nurse nurse) {
        Vacation c = new Vacation();
        c.setPocetak(vacationDTO.getPocetak());
        c.setKraj(vacationDTO.getKraj());
        c.setNurse(nurse);
        c.setActive(true);
        c.setEnabled(false);
        c.setDoctor_nurse("nurse");
        c = this.vacationRepository.save(c);
        return c;
    }

}
