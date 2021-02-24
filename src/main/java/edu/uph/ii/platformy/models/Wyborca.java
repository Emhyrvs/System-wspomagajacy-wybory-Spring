package edu.uph.ii.platformy.models;

import edu.uph.ii.platformy.validators.annotations.InvalidValues;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "wyborcy")
@NamedQuery(name = "Wyborca.findAllUsingNamedQuery",
        query = "SELECT w FROM Wyborca  w WHERE upper(w.imie) LIKE upper(:phrase) or upper(w.nazwisko) LIKE upper(:phrase) or upper(w.Pesel) LIKE upper(:phrase)")

public class Wyborca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    //@Size(min = 2, max = 30)
    @Length(min = 2, max = 30)
    @InvalidValues(ignoreCase = true, values = {"Maciej", "Michal"})
    private String imie;

    @NotBlank
    @Size(min = 2, max = 50)
    private String nazwisko;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    private String Pesel;
private Boolean status;
    @OneToOne(fetch = FetchType.EAGER)


    private User user;

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

    public String getPesel() {
        return Pesel;
    }

    public void setPesel(String pesel) {
        Pesel = pesel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Wyborca(String imie, String nazwisko, String Pesel) {
        this.imie=imie;
        this.nazwisko=nazwisko;
        this.Pesel=Pesel;
    }
}
