package LorenzoBaldassari.Capstone.Payloads.UtentePayloads;

import LorenzoBaldassari.Capstone.Entities.Enum.Ruolo;

public record UtenteRequestDto(
        String nome,
        String cognome,
        String email,
        String password,
        String immagine_di_profilo,
        Ruolo ruolo
) {
}
