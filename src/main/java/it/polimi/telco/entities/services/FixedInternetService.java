package it.polimi.telco.entities.services;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "fixedinternetservice", schema = "telco")
public class FixedInternetService extends Service {

    private int numOfGiga;
    private int feeForExtraGiga;

    public int getNumOfGiga() {
        return numOfGiga;
    }

    public void setNumOfGiga(int numOfGiga) {
        this.numOfGiga = numOfGiga;
    }

    public int getFeeForExtraGiga() {
        return feeForExtraGiga;
    }

    public void setFeeForExtraGiga(int feeForExtraGiga) {
        this.feeForExtraGiga = feeForExtraGiga;
    }
}

