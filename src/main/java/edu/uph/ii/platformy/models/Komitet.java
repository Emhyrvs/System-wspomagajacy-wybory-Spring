package edu.uph.ii.platformy.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter

@Log4j2
@Entity
@Table(name = "komitety")
@NamedQuery(name = "Komitet.findAllUsingNamedQuery",

        query = "SELECT komitet FROM Komitet komitet WHERE upper(komitet.nazwa) LIKE upper(:phrase) ")
public class Komitet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


   private String nazwa;
   private String podpisy;

    public Komitet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public static Logger getLog() {
        return log;
    }
}
