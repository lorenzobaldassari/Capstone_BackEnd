package LorenzoBaldassari.Capstone.Entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@Table(name = "commenti")
public class Commento {

    @GeneratedValue
    @Id
    @Column(name = "uuid_commento")
    private UUID uuid;
    @Column(name = "uuid_commento_commento")
    private String contenuto;
    @Column(name = "data_commento")
    private LocalDate data;
//    private long likess;


    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;
    @ManyToOne
    @JoinColumn(name = "id_pagina")
    private Pagina pagina;
    @ManyToOne
    @JoinColumn(name = "id_post")
    private Post post;
//    public void setLikes(long likes) {
//        this.likess = likes;
//    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setPagina(Pagina pagina) {
        this.pagina = pagina;
    }
}
