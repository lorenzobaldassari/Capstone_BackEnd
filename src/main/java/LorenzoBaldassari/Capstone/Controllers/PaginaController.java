package LorenzoBaldassari.Capstone.Controllers;

import LorenzoBaldassari.Capstone.Entities.Pagina;
import LorenzoBaldassari.Capstone.Entities.Utente;
import LorenzoBaldassari.Capstone.Exceptions.BadRequestException;
import LorenzoBaldassari.Capstone.Payloads.PaginaPayloads.PaginaRequestDto;
import LorenzoBaldassari.Capstone.Payloads.PaginaPayloads.PaginaRespondDto;
import LorenzoBaldassari.Capstone.Servicies.AuthService;
import LorenzoBaldassari.Capstone.Servicies.PaginaService;
import jakarta.servlet.annotation.HttpConstraint;
import org.apache.http.conn.util.PublicSuffixList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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
        return paginaService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private PaginaRespondDto create(@RequestBody @Validated PaginaRequestDto body,
                                    BindingResult bindingResult,
                                    @AuthenticationPrincipal Utente currentUser){
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
            throw new BadRequestException("errore nel invio del payload per il metodo PUT"+bindingResult.getAllErrors());
        }else {
            return authService.create(body,currentUser);
        }
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Pagina findByUUID(@PathVariable UUID uuid){
        return paginaService.findByUUID(uuid);
    }
    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public PaginaRespondDto findByUUID(@RequestBody @Validated PaginaRequestDto body,
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
    @DeleteMapping("/admin/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByAdmin(@PathVariable UUID uuid){
        paginaService.deleteByAdmin(uuid);
    }



}
