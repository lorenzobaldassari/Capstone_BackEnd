package LorenzoBaldassari.Capstone.Exceptions;

public class NotYourPostException extends RuntimeException{

    public NotYourPostException(){
        super("non sei il proprietario della pagina, non hai aceeso a questa funzione!");
    }
}
