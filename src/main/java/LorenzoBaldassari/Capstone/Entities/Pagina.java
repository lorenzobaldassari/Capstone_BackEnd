package LorenzoBaldassari.Capstone.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pagine")
@Getter
public class Pagina extends Proprietario{

    @Id
    @GeneratedValue
    private UUID id;
    private String titolo;
    private String descrizione;
    private String immagine;
    private String link_sito;
    private String email;
    @JsonIgnore
    private String password;


    @ManyToOne
    @JoinColumn(name = "id_utente_proprietario")
    private Utente utentePagina;

    @JsonIgnore
    @OneToMany(mappedBy = "paginaPost")
    List<Post> listaDiPostDellaPagina;
    @JsonIgnore
    @OneToMany(mappedBy = "pagina")
    List<Commento> listaDiCommenti;


    public void setUtentePagina(Utente utentePagina) {
        this.utentePagina = utentePagina;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }



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
