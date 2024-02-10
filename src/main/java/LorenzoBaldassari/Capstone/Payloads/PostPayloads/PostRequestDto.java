package LorenzoBaldassari.Capstone.Payloads.PostPayloads;

import LorenzoBaldassari.Capstone.Entities.Pagina;
import LorenzoBaldassari.Capstone.Entities.Proprietario;
import LorenzoBaldassari.Capstone.Entities.Utente;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PostRequestDto(
        @NotNull(message="il campo non deve essere null")
        String titolo,
        @NotNull(message="il campo non deve essere null")
        String contenuto,
        @Nullable
        String immagine
) {
}
