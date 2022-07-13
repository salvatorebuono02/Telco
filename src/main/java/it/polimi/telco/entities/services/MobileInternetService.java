package it.polimi.telco.entities.services;


import it.polimi.telco.entities.ServicePackage;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("MobileInternetService")
public class MobileInternetService extends Service{
    private int numOfGiga;
    private float feeForExtraGiga;

    public int getNumOfGiga() {
        return numOfGiga;
    }

    public void setNumOfGiga(int numOfGiga) {
        this.numOfGiga = numOfGiga;
    }

    public float getFeeForExtraGiga() {
        return feeForExtraGiga;
    }

    public void setFeeForExtraGiga(float feeForExtraGiga) {
        this.feeForExtraGiga = feeForExtraGiga;
    }

    @Override
    public String toString() {
        return numOfGiga+" Gigas "+feeForExtraGiga+" $/Giga ";
    }
}