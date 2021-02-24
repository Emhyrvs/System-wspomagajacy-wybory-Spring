package edu.uph.ii.platformy.repositories;


import edu.uph.ii.platformy.models.Komitet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KomitetRepository extends JpaRepository<Komitet, Long>, JpaSpecificationExecutor<Komitet> {
        Page<Komitet> findById(Long id, org.springframework.data.domain.Pageable pageable);
        //nazwa metody jest jednocześnie zapytaniem
        Page<Komitet> findByNazwaContaining(String phrase, org.springframework.data.domain.Pageable pageable);

        //nad klasą Kandydat znajduje się definicja zapytania (@NamedQuery) powiązana z tą metodą
        Page<Komitet> findAllUsingNamedQuery(String phrase, org.springframework.data.domain.Pageable pageable);

        @Query("SELECT komitet FROM Komitet  komitet WHERE " +

                ":phrase is null OR :phrase = '' OR "+
                "upper(komitet.nazwa) LIKE upper(:phrase)  ")

        Page<Komitet> findAllKomitetowUsingFilter(@Param("phrase") String p, Pageable pageable);
}
