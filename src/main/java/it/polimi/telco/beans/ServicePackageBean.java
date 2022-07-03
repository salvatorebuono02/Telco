package it.polimi.telco.beans;

import it.polimi.telco.entities.ServicePackage;
import it.polimi.telco.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public ServicePackage findServicePackageByName(String name){
        List<ServicePackage> allServicePackages=entityManager.createNamedQuery("ServicePackage.findAll",ServicePackage.class).getResultList();
        for (int i = 0; i < allServicePackages.size(); i++) {
            if(Objects.equals(allServicePackages.get(i).getName(), name))
                return allServicePackages.get(i);
        }
        return null;
    }
}
