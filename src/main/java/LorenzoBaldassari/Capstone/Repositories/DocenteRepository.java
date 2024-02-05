package LorenzoBaldassari.Capstone.Repositories;

import LorenzoBaldassari.Capstone.Entities.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocenteRepository extends JpaRepository<Docente,UUID> {
}
