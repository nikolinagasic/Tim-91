package rs.zis.app.zis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.zis.app.zis.domain.DoctorTerms;
import rs.zis.app.zis.domain.TermDefinition;
import rs.zis.app.zis.repository.TermDefinitionRepository;

import java.util.List;

@Service
public class TermDefinitionService {

    @Autowired
    private TermDefinitionRepository termDefinitionRepository;

    public List<TermDefinition> findAll() {
        return termDefinitionRepository.findAll();
    }

    public Page<TermDefinition> findAll(Pageable page) {
        return termDefinitionRepository.findAll(page);
    }

    public TermDefinition findOneById(Long id) {
        return termDefinitionRepository.findOneById(id);
    }

    public TermDefinition findOneByStart_term(String start) {
        return termDefinitionRepository.findOneByStartTerm(start);
    }

    public TermDefinition findOneByEnd_term(String end_term) {
        return termDefinitionRepository.findOneByEndTerm(end_term);
    }
}
