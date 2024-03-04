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
@Table(name = "posts")
@Getter
public class Post {

    @Id
    @GeneratedValue
    private UUID uuid;
    private String titolo_post;
    private String contenuto;
    private String immagine_post;
    private LocalDateTime data;

    @ManyToMany
    @JoinTable(
            name = "utente_e_likes",
            joinColumns = @JoinColumn(name = "utente1"),
            inverseJoinColumns = @JoinColumn(name = "like1"))
    private List<Utente> likes_utente;

    @ManyToMany
    @JoinTable(
            name = "pagine_e_likes1",
            joinColumns = @JoinColumn(name = "pagina2"),
            inverseJoinColumns = @JoinColumn(name = "like2"))
    private List<Pagina> likes_pagina;


    @ManyToOne
    @JoinColumn(name = "id_utente_pubblicazione")
    private Utente utentePost;
    @ManyToOne
    @JoinColumn(name = "id_pagina_pubblicazione")
    private Pagina paginaPost;
    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<Commento> listaDiCommenti;

    public void setLikes_utente(Utente utente) {
        this.likes_utente.add(utente);
    }

    public void setLikes_utente_list(List<Utente> likes_utente) {
        this.likes_utente = likes_utente;
    }

    public void setLikes_pagina_list(List<Pagina> likes_pagina) {
        this.likes_pagina = likes_pagina;
    }

    public void setLikes_Pagina(Pagina pagina) {
        this.likes_pagina.add(pagina);
    }

    public void setTitolo_post(String titolo_post) {
        this.titolo_post = titolo_post;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public void setImmagine_post(String immagine_post) {
        this.immagine_post = immagine_post;
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


