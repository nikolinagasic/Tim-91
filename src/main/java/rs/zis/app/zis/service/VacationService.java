package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Vacation;
import rs.zis.app.zis.repository.VacationRepository;

import java.util.List;

@Service
public class VacationService {

    @Autowired
    private VacationRepository vacationRepository;

    public List<Vacation> findAll() {
        return vacationRepository.findAll();
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

    public Vacation findOneByDoctor(Long id) {
        return vacationRepository.findOneByDoctor(id);
    }

    public Vacation save(Vacation u) {return vacationRepository.save(u);}
}
