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
        pagina.setImmagine("data:image/png;base64,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");
        pagina.setImmagine_di_copertina("https://img.freepik.com/premium-vector/book-templates-books-blank-cover-open-closed-covers-empty-textbook-magazine-white-sheets-hardcover-catalog-mockup-perspective-view-library-bookstore-vector-isolated-illustration_176411-4177.jpg");
        pagina.setLink_sito(body.link_sito());
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
