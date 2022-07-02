package it.polimi.telco.entities.services;


import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "mobileinternetservice",schema = "telco")
@NamedQuery(name = "MobileInternetService.findAll",query = "select mis from MobileInternetService mis")
public class MobileInternetService extends Service {
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
        return "MobileInternetService: "+numOfGiga+"Giga "+feeForExtraGiga+"Euro/extraGiga";
    }
}
