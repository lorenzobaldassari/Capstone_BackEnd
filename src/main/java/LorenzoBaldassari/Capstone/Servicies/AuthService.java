package LorenzoBaldassari.Capstone.Servicies;

import LorenzoBaldassari.Capstone.Entities.Docente;
import LorenzoBaldassari.Capstone.Entities.Enum.Ruolo;
import LorenzoBaldassari.Capstone.Entities.Pagina;
import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Exceptions.EmailAlreadyInDbException;
import LorenzoBaldassari.Capstone.Exceptions.UnauthorizedException;
import LorenzoBaldassari.Capstone.Payloads.AuthPayloads.AuthRequestDTO;
import LorenzoBaldassari.Capstone.Payloads.DocentePayloads.DocenteRequestDto;
import LorenzoBaldassari.Capstone.Payloads.PaginaPayloads.PaginaRequestDto;
import LorenzoBaldassari.Capstone.Payloads.PaginaPayloads.PaginaRespondDto;
import LorenzoBaldassari.Capstone.Payloads.TokenPayloads.TokenRespondPaylaod;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteRequestDto;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteRespondDto;
import LorenzoBaldassari.Capstone.Repositories.DocenteRepository;
import LorenzoBaldassari.Capstone.Repositories.PaginaRepositoy;
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
    @Autowired
    private PaginaRepositoy paginaRepositoy;
    @Autowired
    private PaginaService paginaService;

    public TokenRespondPaylaod authenticateUser(AuthRequestDTO body){
        Utente utente=  utenteService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), utente.getPassword())) {
           String token= jwtTtools.createToken(utente);
           return   new TokenRespondPaylaod(token,utente.getUtente_uuid(),"utente",
                   utente.getNome(), utente.getCognome(), utente.getImmagine_di_profilo() );
        }
        else {
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }
    public TokenRespondPaylaod authenticatePagina(AuthRequestDTO body){
        Pagina pagina= paginaService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), pagina.getPassword())) {
            String token= jwtTtools.createTokenPagina(pagina);
            return   new TokenRespondPaylaod(token,pagina.getId(),"pagina",pagina.getTitolo(),"",pagina.getImmagine());
        }
        else {
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
        utente.setImmagine_di_profilo("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
        utente.setImmagine_di_copertina("https://img.freepik.com/premium-vector/book-templates-books-blank-cover-open-closed-covers-empty-textbook-magazine-white-sheets-hardcover-catalog-mockup-perspective-view-library-bookstore-vector-isolated-illustration_176411-4177.jpg");
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
        docente.setImmagine_di_profilo("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
        docente.setImmagine_di_copertina("https://img.freepik.com/premium-vector/book-templates-books-blank-cover-open-closed-covers-empty-textbook-magazine-white-sheets-hardcover-catalog-mockup-perspective-view-library-bookstore-vector-isolated-illustration_176411-4177.jpg");
        docente.setRuolo(Ruolo.USER);
        docente.setGrado(body.grado());
        docente.setMateria(body.materia());
        docenteRepository.save(docente);
        return new UtenteRespondDto(docente.getUtente_uuid(),docente.getNome());
        }else{
            throw new EmailAlreadyInDbException(body.email());
        }
    }
    public PaginaRespondDto createPagina(PaginaRequestDto body, Utente currentUser){
        Pagina pagina= new Pagina();
        Optional<Pagina> email= paginaRepositoy.findByEmail(body.email());
        if(email.isEmpty()){
        pagina.setTitolo(body.titolo());
        pagina.setDescrizione(body.descrizione());
        pagina.setImmagine("https://cdn5.acolore.com/disegni/colori/201940/scuola-edifici-altri-edifici-1157248.jpg" );
        pagina.setImmagine_di_copertina("https://img.freepik.com/premium-vector/book-templates-books-blank-cover-open-closed-covers-empty-textbook-magazine-white-sheets-hardcover-catalog-mockup-perspective-view-library-bookstore-vector-isolated-illustration_176411-4177.jpg");
        pagina.setLink_sito(body.link_sito());
        pagina.setCitta(body.citta());
        pagina.setProvincia(body.provincia());
        pagina.setIndirizzo(body.indirizzo());
        pagina.setUtentePagina(currentUser);
        pagina.setEmail(body.email());
        pagina.setPassword(bcrypt.encode(body.password()));
        paginaRepositoy.save(pagina);
        return new PaginaRespondDto(pagina.getDescrizione(),pagina.getId());
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
            utente.setImmagine_di_copertina(utente.getImmagine_di_copertina());
            utente.setImmagine_di_profilo(utente.getImmagine_di_profilo());
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

    public void test(){
        System.err.println("errore qui");
    }
}
