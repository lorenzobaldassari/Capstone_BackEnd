package LorenzoBaldassari.Capstone.Payloads.UtentePayloads;

import LorenzoBaldassari.Capstone.Entities.Enum.Ruolo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UtenteModifyByAdminRequestDto(

        @NotNull(message="il campo non deve essere null")
        String nome,
        @NotNull(message="il campo non deve essere null")
        String cognome,
        @Email
        String email,
        Ruolo ruolo

) {
}
