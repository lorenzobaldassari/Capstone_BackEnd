package LorenzoBaldassari.Capstone.Repositories;

import LorenzoBaldassari.Capstone.Entities.Commento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentoRepository extends JpaRepository<Commento, UUID> {
}
