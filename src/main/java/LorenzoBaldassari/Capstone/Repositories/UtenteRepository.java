package LorenzoBaldassari.Capstone.Repositories;

import LorenzoBaldassari.Capstone.Entities.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UtenteRepository extends JpaRepository<Utente, UUID> {
    Optional<Utente> findByEmail(String email);
    Page<Utente> findByNomeContainingAndCognomeContainingIgnoreCase(String nome, String Congnome, Pageable pageable);

}
