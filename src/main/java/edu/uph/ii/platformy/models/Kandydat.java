package edu.uph.ii.platformy.models;

import edu.uph.ii.platformy.validators.annotations.InvalidValues;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Getter
@Setter
@Entity
@Log4j2
@Table(name = "kandydaci")
@NamedQuery(name = "Kandydat.findAllUsingNamedQuery",

        query = "SELECT k FROM Kandydat k WHERE upper(k.imie) LIKE upper(:phrase) or upper(k.nazwisko) LIKE upper(:phrase) or upper(k.komitet) LIKE upper(:phrase)")
public class Kandydat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    @NotBlank
    //@Size(min = 2, max = 30)
    @Length(min = 2, max = 30)
    @InvalidValues(ignoreCase = true, values = {"BMW", "Honda"})
    private String imie;

    public Boolean getStaus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public static Logger getLog() {
        return log;
    }

    @NotBlank
    @Size(min = 2, max = 50)
    private String nazwisko;
   ;
    private  Boolean status;
    @OneToOne(fetch = FetchType.EAGER)


    private User user;

    @Valid
    @ManyToOne(fetch = FetchType.EAGER)//EAGER powoduje pobranie obiektu VehicleType wraz z obiektem Vehicle.
    @JoinColumn(name="komitet_id")
    private Komitet komitet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Boolean getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Komitet getKomitet() {
        return komitet;
    }

    public void setKomitet(Komitet komitet) {
        this.komitet = komitet;
    }

    public Kandydat() {

    }

    public Kandydat(String imie, String nazwisko, Komitet komitet) {
        this.imie=imie;
        this.nazwisko=nazwisko;
        this.komitet=komitet;
    }


}
