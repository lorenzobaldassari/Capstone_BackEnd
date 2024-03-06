package LorenzoBaldassari.Capstone.Controllers;

import LorenzoBaldassari.Capstone.Entities.Docente;
import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Exceptions.BadRequestException;
import LorenzoBaldassari.Capstone.Payloads.DocentePayloads.DocenteRequestDto;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteModifyByAdminRequestDto;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteRequestDto;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteRespondDto;
import LorenzoBaldassari.Capstone.Repositories.UtenteRepository;
import LorenzoBaldassari.Capstone.Servicies.AuthService;
import LorenzoBaldassari.Capstone.Servicies.CloudinaryService;
import LorenzoBaldassari.Capstone.Servicies.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtenteController {


    @Autowired
    private UtenteService utenteService;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private CloudinaryService cloudinaryService;

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public Page<Utente> getAll(@RequestParam(defaultValue = "0")int page,
//                               @RequestParam(defaultValue = "15") int size,
//                               @RequestParam(defaultValue = "nome") String orderBy){
//        return utenteService.getAll(page,size,orderBy);
//    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Utente> findBynomeAndCognomeLike( @RequestParam(defaultValue = "0")int page,
                                                  @RequestParam(defaultValue = "15") int size,
                                                  @RequestParam(defaultValue = "nome") String orderBy,
                                                  @RequestParam(defaultValue = "") String nome,
                                                  @RequestParam(defaultValue = "") String cognome){
        return utenteService.getAll(page,size,orderBy,nome,cognome);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Utente findById(@PathVariable UUID uuid){
        return utenteService.findByUUID(uuid);
    }
    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public  UtenteRespondDto modify(@RequestBody @Validated UtenteModifyByAdminRequestDto body,
                                    @PathVariable UUID uuid,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
            throw new BadRequestException("errore nel invio del payload per il metodo PUT"+bindingResult.getAllErrors());
        }else {
            return utenteService.modifyByAdmin(body, uuid);
        }
    }


    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable UUID uuid){
        utenteService.delete(uuid);
    }


    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UtenteRespondDto modifyByMe(@RequestBody @Validated UtenteRequestDto body,
                                       @AuthenticationPrincipal Utente currentuser,
                                       BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
            throw new BadRequestException("errore nel invio del payload per il metodo PUT"+bindingResult.getAllErrors());
        }else {
            return authService.modifyByMe(body,currentuser.getUtente_uuid());
        }
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public Utente findMyself(@AuthenticationPrincipal Utente currentUser){
        return currentUser;
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyself(@AuthenticationPrincipal Utente currentUser){
        utenteService.delete(currentUser.getUtente_uuid());
    }



    @PutMapping("/me/upload")
    public String uploadImage(@RequestParam("UtenteImage") MultipartFile file,
                                   @AuthenticationPrincipal Utente currentUser
                                   ) throws IOException
                                    {
        String url = cloudinaryService.uploadPicture(file);
        Utente utente = utenteService.findByUUID(currentUser.getUtente_uuid());
        utente.setImmagine_di_profilo(url);
        utenteRepository.save(utente);
        return "ok immagine salvata " + url;
    }
    @PutMapping("/me/cover/upload")
    public String uploadcoverImage(@RequestParam("UtenteCover") MultipartFile file,
                                   @AuthenticationPrincipal Utente currentUser
                                   ) throws IOException
                                    {
        String url = cloudinaryService.uploadPicture(file);
        Utente utente = utenteService.findByUUID(currentUser.getUtente_uuid());
        utente.setImmagine_di_copertina(url);
        utenteRepository.save(utente);
        return "ok immagine salvata " + url;
    }

}
