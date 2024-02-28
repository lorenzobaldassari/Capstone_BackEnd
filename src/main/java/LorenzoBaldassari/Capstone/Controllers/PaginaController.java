package LorenzoBaldassari.Capstone.Controllers;

import LorenzoBaldassari.Capstone.Entities.Pagina;
import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Exceptions.BadRequestException;
import LorenzoBaldassari.Capstone.Payloads.PaginaPayloads.PaginaModifyRequestDto;
import LorenzoBaldassari.Capstone.Payloads.PaginaPayloads.PaginaRequestDto;
import LorenzoBaldassari.Capstone.Payloads.PaginaPayloads.PaginaRespondDto;
import LorenzoBaldassari.Capstone.Servicies.AuthService;
import LorenzoBaldassari.Capstone.Servicies.PaginaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pagine")
public class PaginaController {

    @Autowired
    private PaginaService paginaService;
    @Autowired
    private AuthService authService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Pagina> findAll(){
        authService.test();
        return paginaService.findAll();

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaginaRespondDto createPagina(@RequestBody @Validated PaginaRequestDto body,
                                         @AuthenticationPrincipal Utente currentUser){
        return authService.createPagina(body, currentUser);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Pagina findByUUID(@PathVariable UUID uuid){
        return paginaService.findByUUID(uuid);
    }
    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public PaginaRespondDto findByUUID(@RequestBody @Validated PaginaModifyRequestDto body,
                                       BindingResult bindingResult,
                                       @AuthenticationPrincipal Pagina currentUser){
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
            throw new BadRequestException("errore nel invio del payload per il metodo PUT"+bindingResult.getAllErrors());
        }else {
            return paginaService.modify(body,currentUser.getId());
        }
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
                       @AuthenticationPrincipal Pagina currentUser){
        paginaService.delete(currentUser.getId());
    }
    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByAdmin(@PathVariable UUID uuid){
        paginaService.deleteByAdmin(uuid);
    }



}
