package LorenzoBaldassari.Capstone.Payloads.CommentiPayloads;

import LorenzoBaldassari.Capstone.Entities.Post;
import LorenzoBaldassari.Capstone.Entities.Utente;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CommentiRequestDto(
        @NotNull(message="il campo non deve essere null")
        String contenuto

) {
}
