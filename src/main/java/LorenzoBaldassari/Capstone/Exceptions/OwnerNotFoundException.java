package LorenzoBaldassari.Capstone.Exceptions;

import java.util.UUID;

public class OwnerNotFoundException extends  RuntimeException{

    public OwnerNotFoundException(){
        super("proprietario non trovato");
    }
}
