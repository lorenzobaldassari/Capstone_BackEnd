package LorenzoBaldassari.Capstone.Payloads.CommentiPayloads;

import java.util.UUID;

public record CommentiRespondDto(
        UUID uuid_commento,
        String contenuto
) {
}
