package LorenzoBaldassari.Capstone.Servicies;

import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Exceptions.EmailAlreadyInDbException;
import LorenzoBaldassari.Capstone.Exceptions.ItemNotFoundException;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteModifyByAdminRequestDto;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteRespondDto;
import LorenzoBaldassari.Capstone.Repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

//    public Page<Utente> getAll(int page, int size, String orderBy){
//        Pageable pageable= PageRequest.of(page,size, Sort.by(orderBy));
//        return utenteRepository.findAll(pageable);
//    }
    public Page<Utente> getAll(int page, int size, String orderBy,String nome,String cognome){
        Pageable pageable= PageRequest.of(page,size, Sort.by(orderBy));
        return utenteRepository.findByNomeContainingAndCognomeContainingIgnoreCase(nome,cognome,pageable);
    }


    public Utente findByEmail(String email){
        return utenteRepository.findByEmail(email).orElseThrow(()->new ItemNotFoundException(email));
    }

    public Utente findByUUID(UUID uuid){
        return utenteRepository.findById(uuid).orElseThrow(()->new ItemNotFoundException(uuid));
    }

    public UtenteRespondDto modifyByAdmin(UtenteModifyByAdminRequestDto body, UUID uuid){
        Optional<Utente> email= utenteRepository.findByEmail(body.email());
        Utente utente= this.findByUUID(uuid);
        if(email.isEmpty() || utente.getEmail().equals(body.email()))
        {
        utente.setNome(body.nome());
        utente.setCognome(body.cognome());
        utente.setEmail(body.email());
        utente.setPassword((utente.getPassword()));
        utente.setRuolo(body.ruolo());
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
