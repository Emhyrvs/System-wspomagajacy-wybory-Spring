package edu.uph.ii.platformy.controllers.commands;

import edu.uph.ii.platformy.models.Vehicle;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class VehicleSpecifications {
    private String phrase;
    private Float minPrice;
    private Float maxPrice;

    public static Specification<Vehicle> findByPhrase(final String phrase)
    {
        return (root, query, cb) -> {
            if(StringUtils.isEmpty(phrase) == false){
                String phraseLike = "%"+phrase.toUpperCase() +"%";
                return cb.or(
                        cb.or(
                                cb.like(cb.upper(root.get("name")),
                                        phraseLike),
                                cb.like(cb.upper(root.get("model")),
                                        phraseLike)
                        ),
                        cb.like(cb.upper(root.get("vehicleType").get("name")), phraseLike)
                );
            }
            return null;
        };
    }
    public static Specification<Vehicle> findByPriceRange(Float minPrice,
                                                          Float maxPrice) {
        return (root, query, cb) -> {
            if(minPrice != null && maxPrice != null){
                return cb.between(root.get("price"), minPrice, maxPrice);
            }else if(minPrice != null){
                return  cb.greaterThan(root.get("price"), minPrice);
            }else if(maxPrice != null) {
                return cb.lessThan(root.get("price"), maxPrice);
            }
            return null;
        };
    }

    public boolean isEmpty(){
        return org.springframework.util.StringUtils.isEmpty(phrase) && minPrice == null && minPrice == null;
    }

    public void clear(){
        this.phrase = "";
        this.minPrice = null;
        this.maxPrice = null;
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

    public Float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }
}