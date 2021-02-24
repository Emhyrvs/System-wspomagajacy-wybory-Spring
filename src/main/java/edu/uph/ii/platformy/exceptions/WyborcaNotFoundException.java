package edu.uph.ii.platformy.exceptions;

public class WyborcaNotFoundException extends RuntimeException{

    public WyborcaNotFoundException(){
        super(String.format("Wyborca nie istnieje"));
    }

    public WyborcaNotFoundException(Long id){
        super(String.format("Wyborca o id %d nie istnieje", id));
    }
}
