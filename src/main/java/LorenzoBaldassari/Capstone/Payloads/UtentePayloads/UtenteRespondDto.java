package LorenzoBaldassari.Capstone.Payloads.UtentePayloads;

import java.util.UUID;

public record UtenteRespondDto(
        UUID uuid,
        String nome
) {
}
