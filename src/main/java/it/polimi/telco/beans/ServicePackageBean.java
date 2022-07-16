package it.polimi.telco.beans;

import it.polimi.telco.entities.Product;
import it.polimi.telco.entities.ServicePackage;
import it.polimi.telco.entities.User;
import it.polimi.telco.entities.ValidityPeriod;
import it.polimi.telco.entities.services.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Stateless
public class ServicePackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public ServicePackageBean(){}


    /*public List<ServicePackage> findAvailable(User user){
        List<ServicePackage> allServicePackages=entityManager.createNamedQuery("ServicePackage.findAll",ServicePackage.class).getResultList();
        List<ServicePackage> availableServicePackages=new ArrayList<>();
        for(int i=0;i< allServicePackages.size();i++){
            if(user!=null) {
                if (allServicePackages.get(i).getUser() == null || allServicePackages.get(i).getUser().getId() != user.getId()) {
                    availableServicePackages.add(allServicePackages.get(i));
                }
            }
            else
                availableServicePackages.add(allServicePackages.get(i));
        }
        return availableServicePackages;
    }*/
    public List<ServicePackage> findAllServicePackages(){
        return entityManager.createNamedQuery("ServicePackage.findAll",ServicePackage.class).getResultList();
    }

    public ServicePackage findServicePackageByName(String name){
        List<ServicePackage> allServicePackages=entityManager.createNamedQuery("ServicePackage.findAll",ServicePackage.class).getResultList();
        for (int i = 0; i < allServicePackages.size(); i++) {
            if(Objects.equals(allServicePackages.get(i).getName(), name))
                return allServicePackages.get(i);
        }
        return null;
    }

    public ServicePackage findServicePackageById(int id){
        return entityManager.createNamedQuery("ServicePackage.findId",ServicePackage.class).setParameter("id", id).getSingleResult();
    }

    public List<Service> findAllServices() {
        return entityManager.createNamedQuery("Service.findAll",Service.class).getResultList();
    }

    public List<Service> findServicesFromServicePackageId(int id){
        ServicePackage servicePackage=findServicePackageById(id);
        List<Service> services=findAllServices();
        List<Service> res=new ArrayList<>();
        for (Service s:services){
            if(s.getServicePackages().contains(servicePackage)){
                res.add(s);
            }
        }
        return res;
    }

    public ValidityPeriod findValidityPeriodById(int vpId) {
        List<ValidityPeriod> validityPeriodArrayList = entityManager.createNamedQuery("ValidityPeriod.findAll", ValidityPeriod.class).getResultList();
        for (int i = 0; i < validityPeriodArrayList.size(); i++) {
            if (validityPeriodArrayList.get(i).getId() == vpId)
                return validityPeriodArrayList.get(i);
        }
        return null;
    }

    public Product findProductById(int productId) {
        List<Product> productArrayList =entityManager.createNamedQuery("Products.findAll", Product.class).getResultList();
        for (int i = 0; i < productArrayList.size(); i++) {
            if (productArrayList.get(i).getId() == productId)
                return productArrayList.get(i);
        }
        return null;
    }

    public List<Product> findProductsType(ServicePackage servicePackage) {
        List<Product> prodTypes= servicePackage.getProducts();
        List<Product> allProds = findAllProducts();
        for(Product p1:prodTypes) {
            System.out.println("findproductstype spBean, inside for: " + p1);
            int flag = 0;
            for (Product p : allProds) {
                if (p.getName().equals(p1.getName()) && p.getOrder() == null){
                    flag = 1;
                    System.out.println("found product free");
                }

            }
            if(flag==0)
                prodTypes.remove(p1);
        }

        return prodTypes;
    }

    public List<Product> findAllProducts() {
        return entityManager.createNamedQuery("Products.findAll", Product.class).getResultList();
    }

}
