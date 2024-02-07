package LorenzoBaldassari.Capstone.Payloads.PostPayloads;

import java.util.UUID;

public record PostRespondDto(
        UUID uuid,
        String titolo
) {
}
