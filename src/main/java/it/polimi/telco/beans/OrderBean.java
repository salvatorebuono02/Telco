package it.polimi.telco.beans;

import it.polimi.telco.entities.Order;
import it.polimi.telco.entities.ServicePackage;
import it.polimi.telco.entities.User;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateful
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;
    private int orderInStandBy=-1;

    public int getOrderInStandBy() {
        return orderInStandBy;
    }

    public void setOrderInStandBy(int orderInStandBy) {
        this.orderInStandBy = orderInStandBy;
    }

    //TODO usare get() con findFirst() nelle query ed eliminare optional nei servlet
    public Optional<Order> getOrderFromId(int id){
        return entityManager.createNamedQuery("Order.findFromId",Order.class).setParameter("id",id).getResultStream().findFirst();
    }

    public List<ServicePackage> getServicePackagesId(User user){
        if(findFromCreator(user)==null){
            return null;
        }
        List<ServicePackage> servicePackages= new ArrayList<>();
        System.out.println("findavai getService pack" + servicePackages);
        for (Order order: findFromCreator(user)){
            servicePackages.add(order.getService());
        }
        System.out.println("findavai getService pack final"+ servicePackages);
        return servicePackages;

    }

    public void CreateNewOrder(Order o){
        entityManager.persist(o);
        entityManager.flush();
    }

    public List<Order> findFromCreator(User user){
        return entityManager.createNamedQuery("Order.findFromCreator", Order.class).setParameter("user", user).getResultList();
    }

}
