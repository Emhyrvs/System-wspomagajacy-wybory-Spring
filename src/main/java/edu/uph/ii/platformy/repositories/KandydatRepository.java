package edu.uph.ii.platformy.repositories;

import edu.uph.ii.platformy.models.Kandydat;

import edu.uph.ii.platformy.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KandydatRepository extends JpaRepository<Kandydat, Long>, JpaSpecificationExecutor<Kandydat> {
    Page<Kandydat> findById(Long id, Pageable pageable);
    //nazwa metody jest jednocześnie zapytaniem
    Page<Kandydat> findByNazwiskoContaining(String phrase, Pageable pageable);

    //nad klasą Kandydat znajduje się definicja zapytania (@NamedQuery) powiązana z tą metodą
    Page<Kandydat> findAllUsingNamedQuery(String phrase, Pageable pageable);

    @Query("SELECT k FROM Kandydat  k WHERE " +

            ":phrase is null OR :phrase = '' OR "+
            "upper(k.imie) LIKE upper(:phrase) OR " +
            "upper(k.nazwisko) LIKE upper(:phrase) OR " +
            "upper(k.komitet) LIKE upper(:phrase)" )

    Page<Kandydat> findAllKandydatowUsingFilter(@Param("phrase") String p, Pageable pageable);


    Kandydat findByUser(User user);

    Optional<Kandydat> findById(Long id);
}
