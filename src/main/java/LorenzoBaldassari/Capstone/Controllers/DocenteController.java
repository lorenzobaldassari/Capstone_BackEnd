package LorenzoBaldassari.Capstone.Controllers;

import LorenzoBaldassari.Capstone.Entities.Docente;
import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Exceptions.BadRequestException;
import LorenzoBaldassari.Capstone.Exceptions.ItemNotFoundException;
import LorenzoBaldassari.Capstone.Payloads.DocentePayloads.DocenteRequestDto;
import LorenzoBaldassari.Capstone.Payloads.DocentePayloads.DocenteRequestModifyByAdminDto;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteRespondDto;
import LorenzoBaldassari.Capstone.Repositories.DocenteRepository;
import LorenzoBaldassari.Capstone.Servicies.AuthService;
import LorenzoBaldassari.Capstone.Servicies.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController@RequestMapping("/docenti")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @Autowired
    private AuthService authService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Docente> docentiList(){
        return docenteService.findAll();
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Docente findByUUID( @PathVariable UUID uuid){
        return docenteService.findByUUID(uuid);
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public UtenteRespondDto modifyByAdmin(@RequestBody @Validated DocenteRequestModifyByAdminDto body,
                                          @PathVariable UUID uuid,
                                          BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
            throw new BadRequestException("errore nel invio del payload per il metodo PUT"+bindingResult.getAllErrors());
        }else {
        return docenteService.modifyByAdmin(body,uuid);
        }
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable UUID uuid){
        docenteService.delete(uuid);
    }


    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UtenteRespondDto modifyByMe(@RequestBody @Validated DocenteRequestDto body,
                                       @AuthenticationPrincipal Docente currentuser,
                                       BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
            throw new BadRequestException("errore nel invio del payload per il metodo PUT"+bindingResult.getAllErrors());
        }else {

            return authService.modifyDocenteByMe(body,currentuser.getUtente_uuid());
        }
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyself(@AuthenticationPrincipal Docente currentUser){
        docenteService.delete(currentUser.getUtente_uuid());
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public Docente findMyself(@AuthenticationPrincipal Docente currentUser){
        return currentUser;
    }
}
