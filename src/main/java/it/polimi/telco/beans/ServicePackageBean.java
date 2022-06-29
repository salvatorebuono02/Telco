package it.polimi.telco.beans;

import it.polimi.telco.entities.ServicePackage;
import it.polimi.telco.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ServicePackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public ServicePackageBean(){}

    public List<ServicePackage> findAvailable(User user){
        List<ServicePackage> allServicePackages=entityManager.createNamedQuery("ServicePackage.findAll",ServicePackage.class).getResultList();
        List<ServicePackage> availableServicePackages=new ArrayList<>();
        for(int i=0;i< allServicePackages.size();i++){
            if(allServicePackages.get(i).getUser()==null){
                availableServicePackages.add(allServicePackages.get(i));
            }
        }
        return availableServicePackages;
    }
}
