package LorenzoBaldassari.Capstone.Payloads.DocentePayloads;

import LorenzoBaldassari.Capstone.Entities.Enum.Grado;
import LorenzoBaldassari.Capstone.Entities.Enum.Materia;
import LorenzoBaldassari.Capstone.Entities.Enum.Ruolo;
import jakarta.validation.constraints.NotNull;

public record DocenteRequestModifyByAdminDto(
        @NotNull(message="il campo non deve essere null")
        String nome,
        @NotNull(message="il campo non deve essere null")
        String cognome,
        @NotNull(message="il campo non deve essere null")
        String email,

        @NotNull(message="il campo non deve essere null")
        Ruolo ruolo,
        @NotNull(message="il campo non deve essere null")
        Grado grado,
        @NotNull(message="il campo non deve essere null")
        Materia materia
) {
}
