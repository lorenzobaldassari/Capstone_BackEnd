package LorenzoBaldassari.Capstone.Payloads.ErrorPayloads;

import java.time.LocalDateTime;

public record ErrorDto(
        String message,
        LocalDateTime data
) {
}
