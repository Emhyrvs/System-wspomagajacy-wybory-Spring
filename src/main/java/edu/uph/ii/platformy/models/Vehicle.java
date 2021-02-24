package edu.uph.ii.platformy.models;

import edu.uph.ii.platformy.validators.annotations.InvalidValues;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "vehicles")
@NamedQuery(name = "Vehicle.findAllVehiclesUsingNamedQuery",
        query = "SELECT v FROM Vehicle v WHERE upper(v.name) LIKE upper(:phrase) or upper(v.model) LIKE upper(:phrase) or upper(v.vehicleType.name) LIKE upper(:phrase)")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    //@Size(min = 2, max = 30)
    @Length(min = 2, max = 30)
    @InvalidValues(ignoreCase = true, values = {"BMW", "Honda"})
    private String name;

    @NotBlank
    @Size(min = 2, max = 50)
    private String model;
    @Positive
    @Max(1000000)
    private Float price;

    @Past
    @Temporal(TemporalType.DATE)
    @Column(name="production_date")
    private Date productionDate;

    private boolean exclusive;

    @Valid
    @ManyToOne(fetch = FetchType.EAGER)//EAGER powoduje pobranie obiektu VehicleType wraz z obiektem Vehicle.
    @JoinColumn(name="type_id", nullable = false)
    private VehicleType vehicleType;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Accessory> accessories;

    @Column(name="created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public Vehicle() {
        this.creationDate = new Date();
        this.vehicleType = new VehicleType();
        this.accessories = new HashSet<>();
    }

    public Vehicle(String name, String model, Float price, Date productionDate, boolean exclusive, VehicleType vehicleType, Date creationDate) {
        this.name = name;
        this.model = model;
        this.price = price;
        this.productionDate = productionDate;
        this.exclusive = exclusive;
        this.vehicleType = vehicleType;
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public boolean isExclusive() { return exclusive; }

    public void setExclusive(boolean exclusive) { this.exclusive = exclusive; }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
