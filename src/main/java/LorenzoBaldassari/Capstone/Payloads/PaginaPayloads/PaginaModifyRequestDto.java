package LorenzoBaldassari.Capstone.Payloads.PaginaPayloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

public record PaginaModifyRequestDto(
        @NotNull(message="il campo non deve essere null")
        String titolo,
        @NotNull(message="il campo non deve essere null")
        String descrizione,
        @Nullable
        String link_sito,
        @Email
        String email
) {
}
