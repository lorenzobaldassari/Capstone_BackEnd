package LorenzoBaldassari.Capstone.Controllers;

import LorenzoBaldassari.Capstone.Entities.Commento;
import LorenzoBaldassari.Capstone.Entities.Proprietario;
import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Exceptions.BadRequestException;
import LorenzoBaldassari.Capstone.Payloads.CommentiPayloads.CommentiRequestDto;
import LorenzoBaldassari.Capstone.Payloads.CommentiPayloads.CommentiRespondDto;
import LorenzoBaldassari.Capstone.Servicies.CommentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/commenti")
public class CommentiController {

    @Autowired
    private CommentiService commentiService;


    @GetMapping("/posts/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Page<Commento> findByPost(@PathVariable UUID uuid,
                                     @RequestParam(defaultValue = "0")int page,
                                     @RequestParam(defaultValue = "3") int size,
                                     @RequestParam(defaultValue = "data_commento") String orderBy){

        return commentiService.findByPost(uuid,page,size,orderBy);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Commento> findAll(@RequestParam(defaultValue = "0")int page,
                                  @RequestParam(defaultValue = "3") int size,
                                  @RequestParam(defaultValue = "data") String orderBy){
        return commentiService.findAll(page,size,orderBy);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public CommentiRespondDto post(@RequestBody @Validated CommentiRequestDto body,
                                   @AuthenticationPrincipal Proprietario currentUser,
                                   @RequestParam UUID uuid_post,
                                   BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.err.println(bindingResult.getAllErrors());
            throw new BadRequestException("errore nel invio del payload per il metodo PUT"+bindingResult.getAllErrors());
        }else {
            return commentiService.create(body,currentUser,uuid_post);
        }

    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Commento findByUuid(@PathVariable UUID uuid){
        return commentiService.findById(uuid);
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public CommentiRespondDto modify(@PathVariable UUID uuid,
                                     @RequestBody @Validated CommentiRequestDto body,
                                     BindingResult bindingResult,
                                     @AuthenticationPrincipal Utente utente
                                     ){
        if(bindingResult.hasErrors()){
            System.err.println(bindingResult.getAllErrors());
            throw new BadRequestException("errore nel invio del payload per il metodo PUT"+bindingResult.getAllErrors());
        }else {
            return commentiService.modify(body,uuid,utente);        }

    }

    @DeleteMapping("/me/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable UUID uuid,
                        @AuthenticationPrincipal Utente utente){
        commentiService.delete(uuid,utente);
    }
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")

    public void delete (@PathVariable UUID uuid){
        commentiService.deleteByAmdin(uuid);
    }
//    @PutMapping("/like/{uuid}")
//    @ResponseStatus(HttpStatus.OK)
//    public long like(@PathVariable UUID uuid){
//
//        return commentiService.like(uuid);
//    }
//    @PutMapping("/dislike/{uuid}")
//    @ResponseStatus(HttpStatus.OK)
//    public long dislike(@PathVariable UUID uuid){
//
//        return commentiService.dislike(uuid);
//    }



}
