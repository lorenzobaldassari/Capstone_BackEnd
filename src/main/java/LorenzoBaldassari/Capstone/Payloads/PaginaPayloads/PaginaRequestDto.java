package LorenzoBaldassari.Capstone.Payloads.PaginaPayloads;

import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

public record PaginaRequestDto(
        @NotNull(message="il campo non deve essere null")
        String titolo,
        @NotNull(message="il campo non deve essere null")
        String descrizione,
        @Nullable
        String link_sito
) {
}
