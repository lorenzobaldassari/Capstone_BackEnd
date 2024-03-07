package LorenzoBaldassari.Capstone.Runners;

import LorenzoBaldassari.Capstone.Entities.Enum.Ruolo;
import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Repositories.UtenteRepository;
import LorenzoBaldassari.Capstone.Security.JWTTtools;
import LorenzoBaldassari.Capstone.Servicies.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateAdminRunner implements CommandLineRunner {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PasswordEncoder bcrypt;
    @Override
    public void run(String... args) throws Exception {
        Optional<Utente> checkMail= utenteRepository.findByEmail("admin1@gmail.com");
        if(checkMail.isEmpty()){
        Utente utente = new Utente();
        utente.setNome("Administrator1");
        utente.setCognome("");
        utente.setRuolo(Ruolo.ADMIN);
        utente.setPassword(bcrypt.encode("admin1"));
        utente.setEmail("admin1@gmail.com");
        utente.setImmagine_di_copertina("https://img.freepik.com/premium-vector/book-templates-books-blank-cover-open-closed-covers-empty-textbook-magazine-white-sheets-hardcover-catalog-mockup-perspective-view-library-bookstore-vector-isolated-illustration_176411-4177.jpg");
        utente.setImmagine_di_profilo("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
        utenteRepository.save(utente);
            System.out.println("Admin creato!");
        }else{
            System.out.println("Admin gia esistente!");
        }

    }
}
