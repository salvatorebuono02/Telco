package it.polimi.telco.entities.services;


import it.polimi.telco.entities.ServicePackage;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("FixedPhoneService")
public class FixedPhoneService extends Service{

    @Override
    public String toString() {
        return "Plan to be defined";
    }
}
