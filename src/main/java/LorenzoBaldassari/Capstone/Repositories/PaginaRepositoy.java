package LorenzoBaldassari.Capstone.Repositories;

import LorenzoBaldassari.Capstone.Entities.Docente;
import LorenzoBaldassari.Capstone.Entities.Pagina;
import LorenzoBaldassari.Capstone.Entities.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.Optional;
import java.util.UUID;

public interface PaginaRepositoy extends JpaRepository<Pagina,UUID> {
    Optional<Pagina> findByEmail(String email);

    Page<Pagina> findByTitoloContainingIgnoreCase(String titolo, Pageable pageable);
}
