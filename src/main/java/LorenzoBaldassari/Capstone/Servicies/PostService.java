package LorenzoBaldassari.Capstone.Servicies;

import LorenzoBaldassari.Capstone.Entities.Pagina;
import LorenzoBaldassari.Capstone.Entities.Post;
import LorenzoBaldassari.Capstone.Entities.Proprietario;
import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Exceptions.ItemNotFoundException;
import LorenzoBaldassari.Capstone.Exceptions.NotYourPostException;
import LorenzoBaldassari.Capstone.Exceptions.OwnerNotFoundException;
import LorenzoBaldassari.Capstone.Payloads.PostPayloads.PostRequestDto;
import LorenzoBaldassari.Capstone.Payloads.PostPayloads.PostRespondDto;
import LorenzoBaldassari.Capstone.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;


    public List<Post> findAllUtenti(UUID uuid){
        return postRepository.getPostCustom(uuid);
    }
    public List<Post> findAllPagine(UUID id){
        return postRepository.getPostCustomPagine(id);
    }
    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public PostRespondDto create(PostRequestDto body, Proprietario proprietario){

        Post post= new Post();
        post.setTitolo(body.titolo());
        post.setContenuto(body.contenuto());
        post.setData(LocalDateTime.now());
        post.setImmagine(body.immagine());
        if(proprietario instanceof Utente){
            post.setUtentePost((Utente) proprietario);
        }else{
            post.setPaginaPost((Pagina) proprietario);
        }
        postRepository.save(post);
        return new PostRespondDto(post.getUuid(),post.getTitolo_post());
    }

    public Post findByUUID(UUID uuid){
        return postRepository.findById(uuid).orElseThrow(()->new ItemNotFoundException(uuid));
    }

    public PostRespondDto modifyByMe(PostRequestDto body, UUID uuid, Proprietario currentUser){
        Post post= this.findByUUID(uuid);
        if(currentUser instanceof Utente){
            if (post.getUtentePost()!=null){
                if (((Utente) currentUser).getUtente_uuid().equals(post.getUtentePost().getUtente_uuid())){
                    post.setTitolo(body.titolo());
                    post.setContenuto(body.contenuto());
                    post.setData(LocalDateTime.now());
                    post.setImmagine(body.immagine());
                    postRepository.save(post);
                    return new PostRespondDto(post.getUuid(),post.getTitolo_post());
                }else{
                    throw new NotYourPostException();
                }
            }else{throw new NotYourPostException();}
        }
        else if(currentUser instanceof Pagina){
                if(post.getPaginaPost()!=null){
                    if (((Pagina) currentUser).getId().equals(post.getPaginaPost().getId())){
                        post.setTitolo(body.titolo());
                        post.setContenuto(body.contenuto());
                        post.setData(LocalDateTime.now());
                        post.setImmagine(body.immagine());
                        postRepository.save(post);
                        return new PostRespondDto(post.getUuid(),post.getTitolo_post());
                    }else{
                        throw new NotYourPostException();
                    }
                }else{ throw new NotYourPostException();}
        }
        else{
            throw new OwnerNotFoundException();
        }
    }

    public void deleteByAdmin(UUID uuid){
        postRepository.delete(this.findByUUID(uuid));
    }

    public void deleteByMe(UUID uuid,Proprietario currentUser){
        Post post= this.findByUUID(uuid);
        if(currentUser instanceof Utente){
            if (post.getUtentePost()!=null){
                if (((Utente) currentUser).getUtente_uuid().equals(post.getUtentePost().getUtente_uuid())) {
                    postRepository.delete(post);
                }else{
                    throw new NotYourPostException();
                }
            }else{throw new NotYourPostException();}
        }
        else if(( currentUser instanceof Pagina)){
            if(post.getPaginaPost()!=null){
                if (((Pagina) currentUser).getId().equals(post.getPaginaPost().getId())) {
                    postRepository.delete(post);
                }else{
                    throw new NotYourPostException();
                }
            }else{throw new NotYourPostException();}
    }
        else {
        throw new NotYourPostException();
        }
    }

}
