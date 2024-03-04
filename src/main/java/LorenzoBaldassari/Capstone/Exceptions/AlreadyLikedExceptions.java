package LorenzoBaldassari.Capstone.Exceptions;

import java.util.UUID;

public class AlreadyLikedExceptions extends RuntimeException{
    public AlreadyLikedExceptions(UUID uuid){
        super("utente "+uuid+ " ha gia messo like");
    }
}
