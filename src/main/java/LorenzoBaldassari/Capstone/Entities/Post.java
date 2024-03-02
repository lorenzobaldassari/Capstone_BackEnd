package LorenzoBaldassari.Capstone.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "post")
@Getter
public class Post {

    @Id
    @GeneratedValue
    private UUID uuid;
    private String titolo_post;
    private String contenuto;
    private String immagine_post;
    private LocalDateTime data;


    @ManyToOne
    @JoinColumn(name = "id_utente_pubblicazione")
    private Utente utentePost;
    @ManyToOne
    @JoinColumn(name = "id_pagina_pubblicazione")
    private Pagina paginaPost;
    @JsonIgnore
    @OneToMany(mappedBy = "post")
    List<Commento> listaDiCommenti;



    public void setTitolo(String titolo) {
        this.titolo_post = titolo;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public void setImmagine(String immagine) {
        this.immagine_post = immagine;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setUtentePost(Utente utentePost) {
        this.utentePost = utentePost;
    }

    public void setPaginaPost(Pagina paginaPost) {
        this.paginaPost = paginaPost;
    }

    @Override
    public String toString() {
        return "Post{" +
                "uuid=" + uuid +
                ", titolo='" + titolo_post + '\'' +
                ", contenuto='" + contenuto + '\'' +
                ", immagine='" + immagine_post + '\'' +
                ", data=" + data +
                '}';
    }
}


