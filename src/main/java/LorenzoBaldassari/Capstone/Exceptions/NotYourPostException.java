package LorenzoBaldassari.Capstone.Exceptions;

public class NotYourPostException extends RuntimeException{

    public NotYourPostException(){
        super("non sei il proprietario del post, non hai aceeso a questa funzione!");
    }
}
