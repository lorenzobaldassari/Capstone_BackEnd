package LorenzoBaldassari.Capstone.Servicies;

import LorenzoBaldassari.Capstone.Entities.*;
import LorenzoBaldassari.Capstone.Exceptions.ItemNotFoundException;
import LorenzoBaldassari.Capstone.Exceptions.NotYourPostException;
import LorenzoBaldassari.Capstone.Exceptions.OwnerNotFoundException;
import LorenzoBaldassari.Capstone.Payloads.CommentiPayloads.CommentiRequestDto;
import LorenzoBaldassari.Capstone.Payloads.CommentiPayloads.CommentiRespondDto;
import LorenzoBaldassari.Capstone.Repositories.CommentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CommentiService {

    @Autowired
    private CommentoRepository commentoRepository;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private PostService postService;
    public Page<Commento> findAll(int page, int size, String orderBy){
        Pageable pageable= PageRequest.of(page,size, Sort.by(orderBy).descending());
        return commentoRepository.findAll(pageable);
    }


    public Page<Commento> findByPost(UUID id,int page, int size, String orderBy){
        Pageable pageable= PageRequest.of(page,size, Sort.by(orderBy).descending());
        return commentoRepository.getCommentiCustom(id,pageable);
    }
    public CommentiRespondDto create(CommentiRequestDto body, Proprietario proprietario, UUID uuid_post){
        Post post= postService.findByUUID(uuid_post);
        Commento commento= new Commento();
        commento.setContenuto(body.contenuto());
        commento.setPost(post);
        commento.setData(LocalDate.now());
        if(proprietario instanceof Utente){
            commento.setUtente((Utente) proprietario);
        }else if(proprietario instanceof Pagina){
            commento.setPagina((Pagina) proprietario);
        }
        commentoRepository.save(commento);
        return new CommentiRespondDto(commento.getUuid(),commento.getContenuto());
    }

    public Commento findById(UUID uuid){
        return commentoRepository.findById(uuid).orElseThrow(()->new ItemNotFoundException(uuid));
    }
    public CommentiRespondDto modify(CommentiRequestDto body,UUID uuid_commento,Proprietario proprietario){
        Commento commento= this.findById(uuid_commento);
        if(proprietario instanceof Utente){
            if (commento.getUtente()!=null){
                if(((Utente)proprietario).getUtente_uuid().equals(commento.getUtente().getUtente_uuid()))
                {
                commento.setContenuto(body.contenuto());
                commentoRepository.save(commento);
                commento.setData(LocalDate.now());
                return new CommentiRespondDto(commento.getUuid(),commento.getContenuto());
                }else{
                    throw new NotYourPostException();
                }
        }else{
            throw new NotYourPostException();
        }
    }else if(proprietario instanceof Pagina){
            if (commento.getPagina()!=null) {
                if (((Pagina) proprietario).getId().equals(commento.getPagina().getId())) {
                    commento.setContenuto(body.contenuto());
                    commentoRepository.save(commento);
                    commento.setData(LocalDate.now());
                    return new CommentiRespondDto(commento.getUuid(), commento.getContenuto());
                } else {
                    throw new NotYourPostException();
                }
            }else{
                throw new NotYourPostException();
            }
        }else{
            throw new OwnerNotFoundException();
        }
    }


    public void delete(UUID uuid_commento, Proprietario proprietario){
        Commento commento= this.findById(uuid_commento);

        if(proprietario instanceof Utente){
            if( ((Utente) proprietario).getUtente_uuid().equals(commento.getUtente().getUtente_uuid()))
            {
                commentoRepository.delete(commento);
            }else{
                throw new NotYourPostException();
            }
        }
        if(proprietario instanceof Pagina){
            if( ((Pagina) proprietario).getId().equals(commento.getPagina().getId()))
            {
                commentoRepository.delete(commento);
            }else{
                throw new NotYourPostException();
            }
        }


    }
    public void deleteByAmdin(UUID uuid_commento){

            commentoRepository.delete(this.findById(uuid_commento));
    }

//    public long like(UUID uuid){
//        Commento commento = this.findById(uuid);
//        commento.setContenuto(commento.getContenuto());
//        commento.setData(commento.getData());
//        commento.setLikes(commento.getLikess()+1);
//        commentoRepository.save(commento);
//        return commento.getLikess();
//    }
//    public long dislike(UUID uuid){
//        Commento commento = this.findById(uuid);
//        commento.setContenuto(commento.getContenuto());
//        commento.setData(commento.getData());
//        commento.setLikes(commento.getLikess()-1);
//        commentoRepository.save(commento);
//        return commento.getLikess();
//    }

}
