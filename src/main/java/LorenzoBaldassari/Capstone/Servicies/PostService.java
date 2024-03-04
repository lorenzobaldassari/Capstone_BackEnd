package LorenzoBaldassari.Capstone.Servicies;

import LorenzoBaldassari.Capstone.Entities.Pagina;
import LorenzoBaldassari.Capstone.Entities.Post;
import LorenzoBaldassari.Capstone.Entities.Proprietario;
import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Exceptions.*;
import LorenzoBaldassari.Capstone.Payloads.PostPayloads.PostRequestDto;
import LorenzoBaldassari.Capstone.Payloads.PostPayloads.PostRespondDto;
import LorenzoBaldassari.Capstone.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        post.setTitolo_post(body.titolo());
        post.setContenuto(body.contenuto());
        post.setData(LocalDateTime.now());
        post.setImmagine_post(body.immagine());
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
                    post.setTitolo_post(body.titolo());
                    post.setContenuto(body.contenuto());
                    post.setData(LocalDateTime.now());
                    post.setImmagine_post(body.immagine());
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
                        post.setTitolo_post(body.titolo());
                        post.setContenuto(body.contenuto());
                        post.setData(LocalDateTime.now());
                        post.setImmagine_post(body.immagine());
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

    public long like(UUID uuid, Proprietario proprietario){
        Post post= this.findByUUID(uuid);
        if (proprietario instanceof Utente){
            List<Utente> listaUtenti=post.getLikes_utente();
            List<Utente>utenteLike= listaUtenti.stream().filter(elem->elem.getUtente_uuid().equals(((Utente) proprietario).getUtente_uuid())).toList();
            if(utenteLike.isEmpty()){
            post.setTitolo_post(post.getTitolo_post());
            post.setContenuto(post.getContenuto());
            post.setData(post.getData());
            post.setImmagine_post(post.getImmagine_post());
            post.setLikes_utente((Utente) proprietario);
            postRepository.save(post);
            return post.getLikes_utente().size()+ post.getLikes_pagina().size();
            }else{
                throw new AlreadyLikedExceptions(((Utente) proprietario).getUtente_uuid());
            }
        }else if(proprietario instanceof Pagina){
            List<Pagina> listaPaigne=post.getLikes_pagina();
            List<Pagina>pagineLikes= listaPaigne.stream().filter(elem->elem.getId().equals(((Pagina) proprietario).getId())).toList();
            if(pagineLikes.isEmpty()){
                post.setTitolo_post(post.getTitolo_post());
                post.setContenuto(post.getContenuto());
                post.setData(post.getData());
                post.setImmagine_post(post.getImmagine_post());
                post.setLikes_Pagina((Pagina) proprietario);
                postRepository.save(post);
                return post.getLikes_utente().size()+ post.getLikes_pagina().size();
            }else{
                throw new AlreadyLikedExceptions(((Pagina) proprietario).getId());
            }

        }else{
            throw new OwnerNotFoundException();
        }
    }
    public long dislike(UUID uuid, Proprietario proprietario){
        Post post= this.findByUUID(uuid);
        if (proprietario instanceof Utente){
            List<Utente> listaUtenti=post.getLikes_utente();
            List<Utente>utenteLike= listaUtenti.stream().filter(elem->elem.getUtente_uuid().equals(((Utente) proprietario).getUtente_uuid())).toList();
            if(!utenteLike.isEmpty()){
                post.setTitolo_post(post.getTitolo_post());
                post.setContenuto(post.getContenuto());
                post.setData(post.getData());
                post.setImmagine_post(post.getImmagine_post());
                post.setLikes_utente_list(new ArrayList<>());
                List<Utente> utenteList=post.getLikes_utente().stream().filter(elem->!elem.getUtente_uuid().equals(((Utente) proprietario).getUtente_uuid())).toList();
                utenteList.forEach(elem->post.setLikes_utente(elem));

                postRepository.save(post);
                return post.getLikes_utente().size()+ post.getLikes_pagina().size();
            }else{
                throw new ThereIsNotYourLikeExceptions();
            }
        }else if(proprietario instanceof Pagina){
            List<Pagina> listaPaigne=post.getLikes_pagina();
            List<Pagina>pagineLikes= listaPaigne.stream().filter(elem->elem.getId().equals(((Pagina) proprietario).getId())).toList();
            if(!pagineLikes.isEmpty()){
                post.setTitolo_post(post.getTitolo_post());
                post.setContenuto(post.getContenuto());
                post.setData(post.getData());
                post.setImmagine_post(post.getImmagine_post());
                post.setLikes_pagina_list(new ArrayList<>());
                List<Pagina> paginaList= post.getLikes_pagina().stream().filter(elem->!(elem.getId().equals(((Pagina) proprietario).getId()))).toList();
                paginaList.forEach(elem->post.setLikes_Pagina(elem));
                postRepository.save(post);
                return post.getLikes_utente().size()+ post.getLikes_pagina().size();
            }else{
                throw new AlreadyLikedExceptions(((Pagina) proprietario).getId());
            }

        }else{
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
