package LorenzoBaldassari.Capstone.Payloads.PaginaPayloads;

import LorenzoBaldassari.Capstone.Entities.Utente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

public record PaginaRequestDto(
        @NotNull(message="il campo non deve essere null")
        String titolo,
        @NotNull(message="il campo non deve essere null")
        String descrizione,
        @NotNull(message="il campo non deve essere null")
        String provincia,
        @NotNull(message="il campo non deve essere null")
        String indirizzo,
        @NotNull(message="il campo non deve essere null")
        String citta,
        @Nullable
        String link_sito,
        @Email
        String email,
        @NotNull
        String password

) {
}
