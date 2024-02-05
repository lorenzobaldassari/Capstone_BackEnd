package LorenzoBaldassari.Capstone.Entities;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "post")
@Getter
public class Post {

    private UUID uuid;
    private String titolo;
    private String contenuto;
    private String immagine;
    private LocalDateTime data;


    @ManyToOne
    @JoinColumn(name = "id_utente_pubblicazione")
    private Utente utentePost;
    @ManyToOne
    @JoinColumn(name = "id_pagina_pubblicazione")
    private Pagina paginaPost;



    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Post{" +
                "uuid=" + uuid +
                ", titolo='" + titolo + '\'' +
                ", contenuto='" + contenuto + '\'' +
                ", immagine='" + immagine + '\'' +
                ", data=" + data +
                '}';
    }
}


