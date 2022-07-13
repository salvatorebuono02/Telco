package it.polimi.telco.entities.services;

import it.polimi.telco.entities.ServicePackage;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("MobilePhoneService")
public class MobilePhoneService extends Service{

    private int numOfMinutes;
    private int numOfSms;
    private float feeExtraMin;
    private float feeExtraSms;

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

    public float getFeeExtraMin() {
        return feeExtraMin;
    }

    public void setFeeExtraMin(float feeExtraMin) {
        this.feeExtraMin = feeExtraMin;
    }

    public float getFeeExtraSms() {
        return feeExtraSms;
    }

    public void setFeeExtraSms(float feeExtraSms) {
        this.feeExtraSms = feeExtraSms;
    }
    @Override
    public String toString() {
        return numOfMinutes+" Min "+numOfSms+" SMS, "+feeExtraMin+" $/min "+feeExtraSms+" $/sms ";
    }
}
