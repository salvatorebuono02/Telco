package it.polimi.telco.entities.services;


import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "fixedinternetservice", schema = "telco")
@NamedQuery(name = "FixedInternetService.findAll",query = "select fis from FixedInternetService fis")
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

    @Override
    public String getTypeofService() {
        return "FixedInternetService:" + numOfGiga+"GByte"+" "+feeForExtraGiga+"Euro/ExtraGiga";
    }

    @Override
    public String getName() {
        return "FixedInternetService";
    }
}

