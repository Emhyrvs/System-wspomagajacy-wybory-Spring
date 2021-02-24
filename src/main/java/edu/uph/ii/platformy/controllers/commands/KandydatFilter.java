package edu.uph.ii.platformy.controllers.commands;

import org.springframework.util.StringUtils;

import javax.validation.constraints.PositiveOrZero;

public class KandydatFilter {
    private String phrase;




    public boolean isEmpty(){
        return StringUtils.isEmpty(phrase) ;
    }

    public void clear(){
        this.phrase = "";

    }

    public String getPhraseLIKE(){
        if(StringUtils.isEmpty(phrase)) {
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
