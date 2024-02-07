package LorenzoBaldassari.Capstone.Entities;

import LorenzoBaldassari.Capstone.Entities.Enum.Grado;
import LorenzoBaldassari.Capstone.Entities.Enum.Materia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "docenti")
@Getter
public class Docente extends Utente{

    @Enumerated(EnumType.STRING)
    private Grado grado;
    @Enumerated(EnumType.STRING)
    private Materia materia;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "docente_pagina",
            joinColumns = @JoinColumn(name = "docente_id"),
            inverseJoinColumns = @JoinColumn(name = "pagina_id"))
    List<Pagina> listaDiPagine;

    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "grado=" + grado +
                ", materia=" + materia +
                ", utente_uuid=" + utente_uuid +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", immagine_di_profilo='" + immagine_di_profilo + '\'' +
                ", ruolo=" + ruolo +
                '}';
    }
}
