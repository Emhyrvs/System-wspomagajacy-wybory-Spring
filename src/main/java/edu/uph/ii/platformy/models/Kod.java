package edu.uph.ii.platformy.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Log4j2
@NoArgsConstructor

public class Kod {
    @Id
    private long id;
    private String kod;

}
