package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.Authority;
import rs.zis.app.zis.domain.Doctor;
import rs.zis.app.zis.domain.TipPregleda;
import rs.zis.app.zis.dto.TipPregledaDTO;
import rs.zis.app.zis.repository.TipPregledaRepository;

import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@Service
public class TipPregledaService {

    @Autowired
    private TipPregledaRepository tipPregledaRepository;

    public Page<TipPregleda> findAll(Pageable page) {
        return tipPregledaRepository.findAll(page);
    }

    public List<TipPregleda> findAll() {
        return tipPregledaRepository.findAll();
    }

    public void remove(Long id) {
        tipPregledaRepository.deleteById(id);
    }

    public TipPregleda findOneByName(String name) {
        return tipPregledaRepository.findOneByName(name);
    }

    public TipPregleda update(TipPregleda tipPregleda){
        return tipPregledaRepository.save(tipPregleda);
    }

    public TipPregleda save(TipPregledaDTO tipPregledaDTO) {
        TipPregleda t = new TipPregleda();
        TipPregleda tip = findOneByName(tipPregledaDTO.getName());
        if(tip != null){
            return null;
        }

        t.setName(tipPregledaDTO.getName());
        t = this.tipPregledaRepository.save(t);
        return t;
    }
}
