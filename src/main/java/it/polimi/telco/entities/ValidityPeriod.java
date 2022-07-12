package it.polimi.telco.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "validityPeriod",schema = "telco")
@NamedQuery(name = "ValidityPeriod.findAll",query = "select vp from ValidityPeriod vp")
public class ValidityPeriod implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int monthly_fee;
    private int numOfMonths;

    //quando carico un validity period, non ho bisogno dei service packages associati a esso, quindi lo poniamo a lazy
    @ManyToMany(mappedBy = "validityPeriods",fetch = FetchType.EAGER)
    private List<ServicePackage> servicePackages;

    //TODO orphan removal?
    @OneToMany(mappedBy="validityPeriod", cascade = CascadeType.ALL )
    private List<Order> orders;
    @Override
    public String toString() {
        return numOfMonths +" months at " + monthly_fee +"Euro/month";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonthly_fee() {
        return monthly_fee;
    }

    public void setMonthly_fee(int monthly_fee) {
        this.monthly_fee = monthly_fee;
    }

    public int getNumOfMonths() {
        return numOfMonths;
    }

    public void setNumOfMonths(int numOfMonths) {
        this.numOfMonths = numOfMonths;
    }
}
