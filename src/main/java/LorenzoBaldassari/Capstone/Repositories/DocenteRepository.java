package LorenzoBaldassari.Capstone.Repositories;

import LorenzoBaldassari.Capstone.Entities.Docente;
import LorenzoBaldassari.Capstone.Entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DocenteRepository extends JpaRepository<Docente,UUID> {
    Optional<Docente> findByEmail(String email);
}
