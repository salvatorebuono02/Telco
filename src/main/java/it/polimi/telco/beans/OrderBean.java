package it.polimi.telco.beans;

import it.polimi.telco.entities.*;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Stateful
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;
//    private int orderInStandBy=-1;

   /* public int getOrderInStandBy() {
        return orderInStandBy;
    }

    public void setOrderInStandBy(int orderInStandBy) {
        this.orderInStandBy = orderInStandBy;
    }*/

    //TODO usare get() con findFirst() nelle query ed eliminare optional nei servlet
    public Optional<Order> getOrderFromId(int id){
        return entityManager.createNamedQuery("Order.findFromId",Order.class).setParameter("id",id).getResultStream().findFirst();
    }

    public List<ServicePackage> getServicePackagesId(User user){
        if(findFromCreator(user)==null){
            return null;
        }
        List<ServicePackage> servicePackages= new ArrayList<>();
        for (Order order: findFromCreator(user)){
            if (!servicePackages.contains(order.getService()))
                servicePackages.add(order.getService());
        }
        return servicePackages;

    }


    public void updateOrder(Order o){
        Order order = entityManager.merge(o);
    }

    /*public void getOrderManaged(Order o){
        entityManager.persist(o);
        entityManager.flush();
    }*/

    public void removeOrder(Order o){
        Order order = entityManager.merge(o);
        entityManager.remove(order);
        entityManager.flush();
    }

    public List<Order> findFromCreator(User user){
        return entityManager.createNamedQuery("Order.findFromCreator", Order.class).setParameter("user", user).getResultList();
    }

    public void setConfirmed(Order order, boolean b) {
        Order order1 = entityManager.find(Order.class, order.getId());
        order1.setConfirmed(b);
        entityManager.merge(order1);
    }

    public void setCreator(Order order, User user){
        Order order1=entityManager.find(Order.class, order.getId());
        order1.setCreator(user);
        entityManager.flush();
    }

    public Order CreateNewOrder(List<Product> productArrayList, Date dc, LocalDate s, LocalDate end, ServicePackage sp, ValidityPeriod validityPeriod, float totalValue, float optionalValue, float packageValue, User user, Boolean confirmed){

        ServicePackage servicePackage=entityManager.find(ServicePackage.class,sp.getId());
        ValidityPeriod validityPeriod1=entityManager.find(ValidityPeriod.class,validityPeriod.getId());
        User user1=null;
        if(user!=null){
            user1=entityManager.find(User.class,user.getId());
        }
        Order order=new Order(/*products,*/dc,s,end,servicePackage,validityPeriod1,totalValue,optionalValue,packageValue,user1,confirmed);
        if(productArrayList!=null){
            ArrayList<Product> products=new ArrayList<>();
            for(Product p: productArrayList){
                products.add(entityManager.find(Product.class,p.getId()));
            }
            for (Product p:products){
                p.setOrder(order);
                entityManager.merge(p);
            }
        }
        //entityManager.merge(order);
        entityManager.persist(order);
        entityManager.flush();
        return order;
    }


    public List<Product> findProductsFromOrder(Order order) {
        if(entityManager.createNamedQuery("Product.findByOrder", Product.class).setParameter("order",order).getResultList().isEmpty())
            return null;
        else
            return entityManager.createNamedQuery("Product.findByOrder", Product.class).setParameter("order",order).getResultList();

    }

    public void updateProductsOrder(Order order) {
        List<Product> products=findProductsFromOrder(order);

        for (Product p:products){
            p.setOrder(order);
            entityManager.merge(p);
        }
    }
}
