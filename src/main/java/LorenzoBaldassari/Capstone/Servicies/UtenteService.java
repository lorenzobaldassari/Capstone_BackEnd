package LorenzoBaldassari.Capstone.Servicies;

import LorenzoBaldassari.Capstone.Entities.Enum.Ruolo;
import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Exceptions.EmailAlreadyInDbException;
import LorenzoBaldassari.Capstone.Exceptions.ItemNotFoundException;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteRequestDto;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteRespondDto;
import LorenzoBaldassari.Capstone.Repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public List<Utente> getAll(){
        return utenteRepository.findAll();
    }



    public Utente findByEmail(String email){
        return utenteRepository.findByEmail(email).orElseThrow(()->new EmailAlreadyInDbException(email));
    }

    public Utente findByUUID(UUID uuid){
        return utenteRepository.findById(uuid).orElseThrow(()->new ItemNotFoundException(uuid));
    }

    public UtenteRespondDto modify(UtenteRequestDto body, UUID uuid){
        Optional<Utente> email= utenteRepository.findByEmail(body.email());
        Utente utente= this.findByUUID(uuid);
        if(email.isEmpty() || utente.getEmail().equals(body.email()))
        {
        utente.setNome(body.nome());
        utente.setCognome(body.cognome());
        utente.setEmail(body.email());
        utente.setPassword((body.password()));
        utente.setImmagine_di_profilo("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        utente.setRuolo(Ruolo.USER);
        utenteRepository.save(utente);
        return  new UtenteRespondDto(utente.getUtente_uuid(),utente.getNome());
        }else{
            throw new EmailAlreadyInDbException(body.email());
        }
    }

    public void delete (UUID uuid){
       utenteRepository.delete(this.findByUUID(uuid));
    }
}
