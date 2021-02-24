package edu.uph.ii.platformy.controllers.commands;


import edu.uph.ii.platformy.models.Komitet;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public class KomitetSpecifications {

    private String phrase;


    public static Specification<Komitet> findByPhrase(final String phrase)
    {
        return (root, query, cb) -> {
            if(StringUtils.isEmpty(phrase) == false){
                String phraseLike = "%"+phrase.toUpperCase() +"%";
                return cb.or(

                        cb.like(cb.upper(root.get("nazwa")),
                                phraseLike)


                );
            }
            return null;
        };
    }




    public boolean isEmpty(){
        return org.springframework.util.StringUtils.isEmpty(phrase);
    }

    public void clear(){
        this.phrase = "";

    }

    public String getPhraseLIKE(){
        if(org.springframework.util.StringUtils.isEmpty(phrase)) {
            return null;
        }else{
            return "%"+phrase+"%";
        }
    }



    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }



}
