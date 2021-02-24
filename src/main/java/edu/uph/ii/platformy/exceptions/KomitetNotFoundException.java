package edu.uph.ii.platformy.exceptions;

import edu.uph.ii.platformy.models.Komitet;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such komiet")
public class KomitetNotFoundException extends RuntimeException {
    public KomitetNotFoundException(){
        super(String.format("Komitet nie istnieje"));
    }

    public KomitetNotFoundException(Long id){
        super(String.format("Komitet  id %d nie istnieje", id));
    }
}
