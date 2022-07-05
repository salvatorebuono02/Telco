package it.polimi.telco.entities.services;


import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "fixedphoneservice",schema = "telco")
@NamedQuery(name = "FixedPhoneService.findAll",query = "select fps from FixedPhoneService fps")
public class FixedPhoneService extends Service {
    private int numOfMinutes;
    private int numOfSms;
    private int feeExtraMin;
    private int feeExtraSms;

    public int getNumOfMinutes() {
        return numOfMinutes;
    }

    public void setNumOfMinutes(int numOfMinutes) {
        this.numOfMinutes = numOfMinutes;
    }

    public int getNumOfSms() {
        return numOfSms;
    }

    public void setNumOfSms(int numOfSms) {
        this.numOfSms = numOfSms;
    }

    public int getFeeExtraMin() {
        return feeExtraMin;
    }

    public void setFeeExtraMin(int feeExtraMin) {
        this.feeExtraMin = feeExtraMin;
    }

    public int getFeeExtraSms() {
        return feeExtraSms;
    }

    public void setFeeExtraSms(int feeExtraSms) {
        this.feeExtraSms = feeExtraSms;
    }

    @Override
    public String getTypeofService() {
        return "FixedPhoneService:"+numOfMinutes+"minutes "+numOfSms+"sms "+feeExtraMin+ "Euro/extra SMS "+feeExtraMin+"Euro/extra minutes";
    }
    public String getName(){
        return "FixedPhoneService";
    }
}

