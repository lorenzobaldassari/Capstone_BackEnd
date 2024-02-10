package LorenzoBaldassari.Capstone.Servicies;
import LorenzoBaldassari.Capstone.Entities.Pagina;
import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Exceptions.ItemNotFoundException;
import LorenzoBaldassari.Capstone.Exceptions.NotYourPageException;
import LorenzoBaldassari.Capstone.Payloads.PaginaPayloads.PaginaRequestDto;
import LorenzoBaldassari.Capstone.Payloads.PaginaPayloads.PaginaRespondDto;
import LorenzoBaldassari.Capstone.Repositories.PaginaRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaginaService {
    @Autowired
    private PaginaRepositoy paginaRepositoy;


    public List<Pagina> findAll(){
        return paginaRepositoy.findAll();
    }



    public Pagina findByUUID(UUID uuid){
        return paginaRepositoy.findById(uuid).orElseThrow(()->new ItemNotFoundException(uuid));
    }

    public Pagina findByEmail(String email){
        return paginaRepositoy.findByEmail(email).orElseThrow(()->new ItemNotFoundException(email));
    }
    public PaginaRespondDto modify(PaginaRequestDto body, UUID uuid){
        Pagina pagina= this.findByUUID(uuid);
        pagina.setTitolo(body.titolo());
        pagina.setDescrizione(body.descrizione());
        pagina.setImmagine("https://res.cloudinary.com/dxmrdw4i7/image/upload/v1707323085/blank-profile-picture-973460_640_1_dqhavj.webp");
        pagina.setLink_sito(body.link_sito());
        pagina.setEmail(body.email());
        pagina.setPassword(pagina.getPassword());
        paginaRepositoy.save(pagina);
        return new PaginaRespondDto(pagina.getTitolo(),pagina.getId());
        }


    public void delete(UUID uuid){
        Pagina pagina=this.findByUUID(uuid);
        paginaRepositoy.delete(pagina);
    }
    public void deleteByAdmin(UUID uuid){
        Pagina pagina=this.findByUUID(uuid);
        paginaRepositoy.delete(pagina);

    }

}
