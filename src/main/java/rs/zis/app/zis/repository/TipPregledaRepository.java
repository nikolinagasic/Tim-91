package rs.zis.app.zis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.zis.app.zis.domain.TipPregleda;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public interface TipPregledaRepository extends JpaRepository<TipPregleda, Long> {
    Page<TipPregleda> findAll(Pageable pageable);
    TipPregleda findOneById(long id);
    TipPregleda findOneByName(String name);
}
