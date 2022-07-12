package it.polimi.telco.entities.services;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "mobilephoneservice",schema = "telco")
@NamedQuery(name = "MobilePhoneService.findAll",query = "select mps from MobilePhoneService mps")
public class MobilePhoneService extends Service {
    @Override
    public String getTypeofService() {
        return "MobilePhoneService";
    }

    @Override
    public String getName() {
        return "MobilePhoneService";
    }
}
