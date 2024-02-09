package LorenzoBaldassari.Capstone.Servicies;

import LorenzoBaldassari.Capstone.Entities.Docente;
import LorenzoBaldassari.Capstone.Entities.Enum.Ruolo;
import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Exceptions.EmailAlreadyInDbException;
import LorenzoBaldassari.Capstone.Exceptions.UnauthorizedException;
import LorenzoBaldassari.Capstone.Payloads.AuthPayloads.AuthRequestDTO;
import LorenzoBaldassari.Capstone.Payloads.DocentePayloads.DocenteRequestDto;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteRequestDto;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteRespondDto;
import LorenzoBaldassari.Capstone.Repositories.DocenteRepository;
import LorenzoBaldassari.Capstone.Repositories.UtenteRepository;
import LorenzoBaldassari.Capstone.Security.JWTTtools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private DocenteService docenteService;
    @Autowired
    private JWTTtools jwtTtools;
    @Autowired
    private UtenteRepository utenteRepository;

    public String authenticateUser(AuthRequestDTO body){
        Utente utente=  utenteService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), utente.getPassword())) {
            return jwtTtools.createToken(utente);
        } else {
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }

    public UtenteRespondDto create(UtenteRequestDto body){
        Utente utente= new Utente();
        Optional<Utente> email= utenteRepository.findByEmail(body.email());
        if(email.isEmpty())
        {
        utente.setNome(body.nome());
        utente.setCognome(body.cognome());
        utente.setEmail(body.email());
        utente.setPassword(bcrypt.encode(body.password()));
        utente.setImmagine_di_profilo("https://res.cloudinary.com/dxmrdw4i7/image/upload/v1707323085/blank-profile-picture-973460_640_1_dqhavj.webp");
        utente.setRuolo(Ruolo.USER);
        utenteRepository.save(utente);
        return  new UtenteRespondDto(utente.getUtente_uuid(),utente.getNome());
        }else{
            throw new EmailAlreadyInDbException(body.email());
        }
    }

    public UtenteRespondDto createDocente(DocenteRequestDto body){
        Docente docente= new Docente();

        Optional<Utente> email= utenteRepository.findByEmail(body.email());
        if(email.isEmpty()){

        docente.setNome(body.nome());
        docente.setCognome(body.cognome());
        docente.setEmail(body.email());
        docente.setPassword(bcrypt.encode(body.password()));
        docente.setImmagine_di_profilo("https://res.cloudinary.com/dxmrdw4i7/image/upload/v1707323085/blank-profile-picture-973460_640_1_dqhavj.webp");
        docente.setRuolo(Ruolo.USER);
        docente.setGrado(body.grado());
        docente.setMateria(body.materia());
        docenteRepository.save(docente);
        return new UtenteRespondDto(docente.getUtente_uuid(),docente.getNome());
        }else{
            throw new EmailAlreadyInDbException(body.email());
        }
    }

    public UtenteRespondDto modifyByMe(UtenteRequestDto body, UUID uuid){
        Optional<Utente> email= utenteRepository.findByEmail(body.email());
        Utente utente= utenteService.findByUUID(uuid);
        if(email.isEmpty() || utente.getEmail().equals(body.email()))
        {
            utente.setNome(body.nome());
            utente.setCognome(body.cognome());
            utente.setEmail(body.email());
            utente.setPassword(bcrypt.encode(body.password()));
            utenteRepository.save(utente);
            return  new UtenteRespondDto(utente.getUtente_uuid(),utente.getNome());
        }else{
            throw new EmailAlreadyInDbException(body.email());
        }
    }

    public UtenteRespondDto modifyDocenteByMe(DocenteRequestDto body, UUID uuid){
        Docente docente= docenteService.findByUUID(uuid);
        Optional<Docente> email= docenteRepository.findByEmail(body.email());
        if(email.isEmpty() || body.email().equals(docente.getEmail())) {
            docente.setNome(body.nome());
            docente.setCognome(body.cognome());
            docente.setEmail(body.email());
            docente.setPassword(bcrypt.encode(body.password()));
            docente.setGrado(body.grado());
            docente.setMateria(body.materia());
            docenteRepository.save(docente);
            return new UtenteRespondDto(docente.getUtente_uuid(), docente.getNome());
        }else {
            throw  new EmailAlreadyInDbException(body.email());
        }
    }
}
