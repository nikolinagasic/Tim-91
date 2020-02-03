package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.TipPregleda;
import rs.zis.app.zis.dto.TipPregledaDTO;
import rs.zis.app.zis.repository.TipPregledaRepository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@Service
public class TipPregledaService {

    @Autowired
    private TipPregledaRepository tipPregledaRepository;

   /* public Page<TipPregleda> findAll(Pageable page) {
        return tipPregledaRepository.findAll(page);
    }
*/
    public List<TipPregleda> findAll() {
        List<TipPregleda> retVal = new ArrayList<>();
        List<TipPregleda> svi = tipPregledaRepository.findAll();
        for (TipPregleda tip : svi) {
            if (tip.isActive()) {
                retVal.add(tip);
            }
        }
        System.out.println("find all:"+retVal);
        return retVal;
    }

    public void remove(Long id) {
        tipPregledaRepository.deleteById(id);
    }

    public TipPregleda findOneByName(String name) {
        return tipPregledaRepository.findOneByName(name);
    }

    public TipPregleda findOneById(Long id) {
        return tipPregledaRepository.findOneById(id);
    }

    public TipPregleda update(TipPregleda tipPregleda){
        return tipPregledaRepository.save(tipPregleda);
    }

    public TipPregleda save(TipPregledaDTO tipPregledaDTO) {
        TipPregleda t = new TipPregleda();
        TipPregleda tip = findOneByName(tipPregledaDTO.getName());
        if(tip != null){
            if (tip.isEnabled())
                 return null;
            tip.setEnabled(true);
            update(tip);
            System.out.println("saving"+tip.isEnabled());
            return tip;
        }
        t.setActive(true);
        t.setName(tipPregledaDTO.getName());
        t = this.tipPregledaRepository.save(t);
        return t;
    }
    public List<TipPregledaDTO> search(String naziv) {
        List<TipPregledaDTO> retList = new ArrayList<>();
        List<TipPregleda> svi = findAll();
        for (TipPregleda tip: svi) {
            System.out.println("search:"+tip.getName());
            if(tip.getName().toLowerCase().contains(naziv.toLowerCase())){

                    retList.add(new TipPregledaDTO(tip));

            }
        }

        return retList;
    }
}
