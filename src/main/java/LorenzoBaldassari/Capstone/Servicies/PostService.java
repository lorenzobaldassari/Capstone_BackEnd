package LorenzoBaldassari.Capstone.Servicies;

import LorenzoBaldassari.Capstone.Entities.Post;
import LorenzoBaldassari.Capstone.Exceptions.ItemNotFoundException;
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


    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public PostRespondDto create(PostRequestDto body){
        Post post= new Post();
        post.setTitolo(body.titolo());
        post.setContenuto(body.contnuto());
        post.setData(LocalDateTime.now());
        post.setImmagine(body.immagine());
        postRepository.save(post);
        return new PostRespondDto(post.getUuid(),post.getTitolo());
    }

    public Post findByUUID(UUID uuid){
        return postRepository.findById(uuid).orElseThrow(()->new ItemNotFoundException(uuid));
    }

    public PostRespondDto modify(PostRequestDto body, UUID uuid){
        Post post= this.findByUUID(uuid);
        post.setTitolo(body.titolo());
        post.setContenuto(body.contnuto());
        post.setData(LocalDateTime.now());
        post.setImmagine(body.immagine());
        postRepository.save(post);
        return new PostRespondDto(post.getUuid(),post.getTitolo());
    }

    public void delete(UUID uuid){
        postRepository.delete(this.findByUUID(uuid));
    }
}
