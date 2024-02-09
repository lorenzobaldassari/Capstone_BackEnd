package LorenzoBaldassari.Capstone.Entities;

import LorenzoBaldassari.Capstone.Entities.Enum.Ruolo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "utenti")
@Getter
@JsonIgnoreProperties({"password", "authorities", "accountNonExpired",
        "enabled", "accountNonLocked", "credentialsNonExpired"})
public class Utente implements UserDetails {

    @Id
    @GeneratedValue
    protected UUID utente_uuid;
    protected String nome;
    protected String cognome;
    protected String email;
    protected String password;
    protected String immagine_di_profilo;
    @Enumerated(EnumType.STRING)
    protected Ruolo ruolo;

    @JsonIgnore
    @OneToMany(mappedBy = "utentePagina")
    List<Pagina> listaDiPagine;
    @JsonIgnore
    @OneToMany(mappedBy = "utentePost")
    List<Post> listaDiPost;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImmagine_di_profilo(String immagine_di_profilo) {
        this.immagine_di_profilo = immagine_di_profilo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "utente_uuid=" + utente_uuid +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", immagine_di_profilo='" + immagine_di_profilo + '\'' +
                ", ruolo=" + ruolo +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(String.valueOf(this.ruolo)));
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.nome;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
