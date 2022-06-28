package it.polimi.telco.entities.services;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "fixedphoneservice",schema = "telco")
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
}

