package LorenzoBaldassari.Capstone.Controllers;

import LorenzoBaldassari.Capstone.Entities.Post;
import LorenzoBaldassari.Capstone.Entities.Proprietario;
import LorenzoBaldassari.Capstone.Exceptions.BadRequestException;
import LorenzoBaldassari.Capstone.Payloads.PostPayloads.PostRequestDto;
import LorenzoBaldassari.Capstone.Payloads.PostPayloads.PostRespondDto;
import LorenzoBaldassari.Capstone.Servicies.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {


    @Autowired
    private PostService postService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Post> getAll(@RequestParam(defaultValue = "0")int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(defaultValue = "data") String orderBy){
        return postService.findAll(page,size,orderBy);
    }
    @GetMapping("/utenti")
    @ResponseStatus(HttpStatus.OK)
    public Page<Post> getAll(@RequestParam(defaultValue = "")UUID uuid,
                             @RequestParam(defaultValue = "0")int page,
                             @RequestParam(defaultValue = "4") int size,
                             @RequestParam(defaultValue = "data") String orderBy){
        return postService.findAllUtenti(uuid,page,size,orderBy);
    }
    @GetMapping("/pagine")
    @ResponseStatus(HttpStatus.OK)
    public Page<Post> getAllPagine(@RequestParam(defaultValue = "")UUID id,
                                   @RequestParam(defaultValue = "0")int page,
                                   @RequestParam(defaultValue = "4") int size,
                                   @RequestParam(defaultValue = "data") String orderBy){

        return postService.findAllPagine(id,page,size,orderBy);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostRespondDto post(@RequestBody @Validated PostRequestDto body,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal Proprietario currentUser){
        if(bindingResult.hasErrors()){
            System.err.println(bindingResult.getAllErrors());
            throw new BadRequestException("errore nel invio del payload per il metodo PUT"+bindingResult.getAllErrors());
        }else {
            return postService.create(body,currentUser);
        }
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Post findByUUID(@PathVariable UUID uuid){
    return postService.findByUUID(uuid);
    }

    @PutMapping("/{uuid}/me")
    @ResponseStatus(HttpStatus.OK)
    public PostRespondDto modify(@RequestBody @Validated PostRequestDto body,
                                 @PathVariable UUID uuid,
                                 BindingResult bindingResult,
                                 @AuthenticationPrincipal Proprietario currentUser){
        if(bindingResult.hasErrors()){
            System.err.println(bindingResult.getAllErrors());
            throw new BadRequestException("errore nel invio del payload per il metodo PUT"+bindingResult.getAllErrors());
        }else {
            return postService.modifyByMe(body,uuid,currentUser);
        }
    }

    @DeleteMapping("/{uuid}/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID uuid,
                       @AuthenticationPrincipal Proprietario currentUser){
        postService.deleteByMe(uuid,currentUser);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delteByMe(@PathVariable UUID uuid) {
        postService.deleteByAdmin(uuid);
    }

    @PutMapping("/like/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public long like(@PathVariable UUID uuid,@AuthenticationPrincipal Proprietario currentUser){

            return postService.like(uuid,currentUser);
    }
    @PutMapping("/dislike/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public long dislike(@PathVariable UUID uuid,@AuthenticationPrincipal Proprietario currentUser){

            return postService.dislike(uuid,currentUser);
    }

}

