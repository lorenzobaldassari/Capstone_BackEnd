package LorenzoBaldassari.Capstone.Repositories;

import LorenzoBaldassari.Capstone.Entities.Commento;
import LorenzoBaldassari.Capstone.Entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CommentoRepository extends JpaRepository<Commento, UUID> {
    @Query(
            value = "SELECT * FROM commenti c INNER JOIN posts p on c.id_post=p.uuid WHERE c.id_post=?1",
            nativeQuery = true)
    List<Commento> getCommentiCustom(UUID id);
}
