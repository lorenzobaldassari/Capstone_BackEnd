package LorenzoBaldassari.Capstone.Payloads.TokenPayloads;

import java.util.UUID;

public record TokenRespondPaylaod(
        String token,
        UUID uuid,
        String tipo,
        String nome,
        String cognome,
        String immagine
) {
}
