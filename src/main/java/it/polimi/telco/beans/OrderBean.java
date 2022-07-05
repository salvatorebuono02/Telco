package it.polimi.telco.beans;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;
    private boolean orderInStandBy=false;

    public boolean isOrderInStandBy() {
        return orderInStandBy;
    }

    public void setOrderInStandBy(boolean orderInStandBy) {
        this.orderInStandBy = orderInStandBy;
    }
}
