package LorenzoBaldassari.Capstone.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pagine")
@Getter
public class Pagina {

    private UUID id;
    private String titolo;
    private String descrizione;
    private String immagine;
    private String link_sito;

    @ManyToOne
    @JoinColumn(name = "id_utente_proprietario")
    private Utente utentePagina;

    @JsonIgnore
    @OneToMany(mappedBy = "paginaPost")
    List<Post> listaDiPostDellaPagina;

    @JsonIgnore
    @ManyToMany(mappedBy = "listaDiPagine")

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public void setLink_sito(String link_sito) {
        this.link_sito = link_sito;
    }

    @Override
    public String toString() {
        return "Pagina{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", immagine='" + immagine + '\'' +
                ", link_sito='" + link_sito + '\'' +
                '}';
    }
}