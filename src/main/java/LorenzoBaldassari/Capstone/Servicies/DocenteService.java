package LorenzoBaldassari.Capstone.Servicies;

import LorenzoBaldassari.Capstone.Entities.Docente;
import LorenzoBaldassari.Capstone.Entities.Enum.Ruolo;
import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Exceptions.EmailAlreadyInDbException;
import LorenzoBaldassari.Capstone.Exceptions.ItemNotFoundException;
import LorenzoBaldassari.Capstone.Payloads.DocentePayloads.DocenteRequestDto;
import LorenzoBaldassari.Capstone.Payloads.DocentePayloads.DocenteRequestModifyByAdminDto;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteRespondDto;
import LorenzoBaldassari.Capstone.Repositories.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocenteService {
    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public List<Docente> findAll(){
        return docenteRepository.findAll();
    }



    public Docente findByUUID(UUID uuid){
        return docenteRepository.findById(uuid).orElseThrow(()->new ItemNotFoundException(uuid));
    }

    public UtenteRespondDto modifyByAdmin(DocenteRequestModifyByAdminDto body, UUID uuid){
        Docente docente= this.findByUUID(uuid);
        Optional<Docente> email= docenteRepository.findByEmail(body.email());
        if(email.isEmpty() || body.email().equals(docente.getEmail())){
        docente.setNome(body.nome());
        docente.setCognome(body.cognome());
        docente.setEmail(body.email());
        docente.setRuolo(body.ruolo());
        docente.setGrado(body.grado());
        docente.setMateria(body.materia());
        docenteRepository.save(docente);
        return new UtenteRespondDto(docente.getUtente_uuid(),docente.getNome());
        }else {
            throw  new EmailAlreadyInDbException(body.email());
        }

    }






    public void delete(UUID uuid){
        docenteRepository.delete(this.findByUUID(uuid));
    }

}
