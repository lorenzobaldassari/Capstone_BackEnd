package LorenzoBaldassari.Capstone.Exceptions;

public class NotYourPageException extends RuntimeException{

    public NotYourPageException(){
        super("non sei il proprietario della pagina, non hai aceeso a questa funzione!");
    }
}
