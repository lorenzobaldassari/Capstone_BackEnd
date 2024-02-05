package LorenzoBaldassari.Capstone.Servicies;

import LorenzoBaldassari.Capstone.Entities.Enum.Ruolo;
import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Exceptions.ItemNotFoundException;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteRequestDto;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteRespondDto;
import LorenzoBaldassari.Capstone.Repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;
import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private UtenteRepository utenteRepository;

    public List<Utente> getAll(){
        return utenteRepository.findAll();
    }

    public UtenteRespondDto create(UtenteRequestDto body){
        Utente utente= new Utente();
        utente.setNome(body.nome());
        utente.setCognome(body.cognome());
        utente.setEmail(body.email());
        utente.setPassword(bcrypt.encode(body.password()));
        utente.setImmagine_di_profilo("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        utente.setRuolo(Ruolo.USER);
        utenteRepository.save(utente);
        return  new UtenteRespondDto(utente.getUtente_uuid(),utente.getNome());
    }

    public Utente findByUUID(String uuid){
        return utenteRepository.findById(UUID.fromString(uuid)).orElseThrow(()->new ItemNotFoundException(uuid));
    }

    public UtenteRespondDto modify(UtenteRequestDto body, String uuid){
        Utente utente= this.findByUUID(uuid);
        utente.setNome(body.nome());
        utente.setCognome(body.cognome());
        utente.setEmail(body.email());
        utente.setPassword(bcrypt.encode(body.password()));
        utente.setImmagine_di_profilo("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        utente.setRuolo(Ruolo.USER);
        utenteRepository.save(utente);
        return  new UtenteRespondDto(utente.getUtente_uuid(),utente.getNome());
    }
}
