package LorenzoBaldassari.Capstone.Entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "commenti")
@Getter
public class Commento {

    @GeneratedValue
    @Id
    private UUID uuid;
    private String contenuto;
    private LocalDate data;


    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;
    @ManyToOne
    @JoinColumn(name = "id_pagina")
    private Pagina pagina;
    @ManyToOne
    @JoinColumn(name = "id_post")
    private Post post;


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
}
