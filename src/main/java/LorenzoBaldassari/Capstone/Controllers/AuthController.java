package LorenzoBaldassari.Capstone.Controllers;

import LorenzoBaldassari.Capstone.Exceptions.BadRequestException;
import LorenzoBaldassari.Capstone.Payloads.AuthPayloads.AuthRequestDTO;
import LorenzoBaldassari.Capstone.Payloads.DocentePayloads.DocenteRequestDto;
import LorenzoBaldassari.Capstone.Payloads.TokenPayloads.TokenRespondPaylaod;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteRequestDto;
import LorenzoBaldassari.Capstone.Payloads.UtentePayloads.UtenteRespondDto;
import LorenzoBaldassari.Capstone.Servicies.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteRespondDto createUtente(@RequestBody @Validated UtenteRequestDto body,
                                         BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
            throw new BadRequestException("errore nel invio del payload per il metodo POST"+bindingResult.getAllErrors());
        } else {
            return authService.create(body);
        }
    }

    @PostMapping("/register/docenti")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteRespondDto createUtente(@RequestBody @Validated DocenteRequestDto body,
                                         BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.err.println(bindingResult.getAllErrors());
            throw new BadRequestException("errore nel invio del payload per il metodo POST"+bindingResult.getAllErrors());
        } else {
            return authService.create(body);
        }
    }
    @PostMapping("/login")
    public TokenRespondPaylaod getToken(@RequestBody AuthRequestDTO body){
        String accessToken= authService.authenticateUser(body);
        return new TokenRespondPaylaod(accessToken);

    }
}
