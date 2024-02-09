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

    public PaginaRespondDto create(PaginaRequestDto body,Utente currentUser){
        Pagina pagina= new Pagina();
        pagina.setTitolo(body.titolo());
        pagina.setDescrizione(body.descrizione());
        pagina.setImmagine("https://res.cloudinary.com/dxmrdw4i7/image/upload/v1707323085/blank-profile-picture-973460_640_1_dqhavj.webp");
        pagina.setLink_sito(body.link_sito());
        pagina.setUtentePagina(currentUser);
        paginaRepositoy.save(pagina);
        return new PaginaRespondDto(pagina.getTitolo(),pagina.getId());
    }

    public Pagina findByUUID(UUID uuid){
        return paginaRepositoy.findById(uuid).orElseThrow(()->new ItemNotFoundException(uuid));
    }

    public PaginaRespondDto modify(PaginaRequestDto body, UUID uuid, Utente currentUser){
        Pagina pagina= this.findByUUID(uuid);
        if(currentUser.getUtente_uuid().equals(pagina.getUtentePagina().getUtente_uuid())){
        pagina.setTitolo(body.titolo());
        pagina.setDescrizione(body.descrizione());
        pagina.setImmagine("https://res.cloudinary.com/dxmrdw4i7/image/upload/v1707323085/blank-profile-picture-973460_640_1_dqhavj.webp");
        pagina.setLink_sito(body.link_sito());
        paginaRepositoy.save(pagina);
        return new PaginaRespondDto(pagina.getTitolo(),pagina.getId());
        }else{
            throw new NotYourPageException();
        }
    }

    public void delete(UUID uuid,Utente currentUser){
        Pagina pagina=this.findByUUID(uuid);
        if(currentUser.getUtente_uuid().equals(pagina.getUtentePagina().getUtente_uuid())){
        paginaRepositoy.delete(pagina);
    }else{
        throw new NotYourPageException();
        }
    }
    public void deleteByAdmin(UUID uuid){
        Pagina pagina=this.findByUUID(uuid);
        paginaRepositoy.delete(pagina);

    }

}
