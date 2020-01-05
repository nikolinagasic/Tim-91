package rs.zis.app.zis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.zis.app.zis.domain.TermDefinition;
import java.util.List;

@SuppressWarnings("unused")
public interface TermDefinitionRepository extends JpaRepository<TermDefinition, Long> {
    Page<TermDefinition> findAll(Pageable pageable);
    TermDefinition findOneById(Long id);
    TermDefinition findOneByStartTerm(String start);
    TermDefinition findOneByEndTerm(String end);
    List<TermDefinition> findAllByWorkShift(int workShift);
}
