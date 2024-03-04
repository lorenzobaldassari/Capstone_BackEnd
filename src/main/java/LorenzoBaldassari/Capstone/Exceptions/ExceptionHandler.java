package LorenzoBaldassari.Capstone.Exceptions;

import LorenzoBaldassari.Capstone.Payloads.ErrorPayloads.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public ErrorDto unauthorized(UnauthorizedException ex) {
        return new ErrorDto("ERRORE DI AUTENTICAZIONE!"+ex.getMessage(), LocalDateTime.now());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public ErrorDto handleAccessDenied(AccessDeniedException ex) {
        return new ErrorDto("ATTENZIONE AREA PRIVATA! NON SEI NON HAI IL RUOLO NECESSARIO!", LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleBadRequest(BadRequestException ex) {
        return new ErrorDto("richiesta errata controlla i dati inseriti! " +ex.getMessage(), LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleGenericError(Exception ex) {
        System.err.println(ex);
        return new ErrorDto("Problema del server,stiamo sistemando! <3", LocalDateTime.now());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(EmailAlreadyInDbException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleEmailAlreadyInDb(Exception ex) {
        return new ErrorDto(ex.getMessage(), LocalDateTime.now());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleItemNotFound(Exception ex) {
        return new ErrorDto(ex.getMessage(), LocalDateTime.now());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(NotYourPostException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDto handleNotYourPage(Exception ex) {
        return new ErrorDto(ex.getMessage(), LocalDateTime.now());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(OwnerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleOwnerNotFoundException(Exception ex) {
        return new ErrorDto(ex.getMessage(), LocalDateTime.now());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(AlreadyLikedExceptions.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleAlreadyLikedException(Exception ex) {
        return new ErrorDto(ex.getMessage(), LocalDateTime.now());
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(ThereIsNotYourLikeExceptions.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleThereIsNotYourLikeExceptions(Exception ex) {
        return new ErrorDto(ex.getMessage(), LocalDateTime.now());
    }
}
