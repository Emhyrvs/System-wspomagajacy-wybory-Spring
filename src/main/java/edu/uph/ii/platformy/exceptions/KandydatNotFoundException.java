package edu.uph.ii.platformy.exceptions;

import edu.uph.ii.platformy.models.Kandydat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such kandydat")
public class KandydatNotFoundException extends RuntimeException{
    public KandydatNotFoundException(){
        super(String.format("Kandydat nie istnieje"));
    }
    public KandydatNotFoundException(String a){
        super(String.format("Złozyłeś już wiosek o dodanie do kandydatów "));
    }
    public KandydatNotFoundException(Long id){
        super(String.format("Kandydat o id %d nie istnieje", id));
    }
}
