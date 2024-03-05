package LorenzoBaldassari.Capstone.Repositories;

import LorenzoBaldassari.Capstone.Entities.Post;
import org.hibernate.type.ListType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    @Query(
            value = "SELECT * FROM posts p INNER JOIN utenti u on p.id_utente_pubblicazione=u.utente_uuid "+
            "WHERE u.utente_uuid=?1",
            nativeQuery = true)
    List<Post> getPostCustom(UUID uuid);

    @Query(
            value = "SELECT * FROM posts p INNER JOIN pagine a on p.id_pagina_pubblicazione=a.id WHERE a.id=?1",
            nativeQuery = true)
    List<Post> getPostCustomPagine(UUID id);
}
