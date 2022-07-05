package it.polimi.telco.beans;

import it.polimi.telco.entities.ServicePackage;
import it.polimi.telco.entities.User;
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

    public List<ServicePackage> findActive(User user){
        List<ServicePackage> allServicePackages=entityManager.createNamedQuery("ServicePackage.findAll",ServicePackage.class).getResultList();
        List<ServicePackage> activeServicePackages=new ArrayList<>();
        for(int i=0;i< allServicePackages.size();i++){
            System.out.println(allServicePackages.get(i).getName());
            if(allServicePackages.get(i).getUser()!=null && allServicePackages.get(i).getUser().getId()==user.getId()){
                activeServicePackages.add(allServicePackages.get(i));
                System.out.println("user trovato in pack" + allServicePackages.get(i).getName());
            }

        }
        return activeServicePackages;
    }
    public List<ServicePackage> findAvailable(User user){
        List<ServicePackage> allServicePackages=entityManager.createNamedQuery("ServicePackage.findAll",ServicePackage.class).getResultList();
        List<ServicePackage> availableServicePackages=new ArrayList<>();
        for(int i=0;i< allServicePackages.size();i++){
            if(allServicePackages.get(i).getUser()==null || allServicePackages.get(i).getUser().getId()!=user.getId()){
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

    public Optional<ServicePackage> findServicePackageById(int id){
        return entityManager.createNamedQuery("ServicePackage.findId",ServicePackage.class).setParameter("id", id).getResultStream().findFirst();
    }

    private List<Service> findAllServices () {
        List<Service> serviceList = new ArrayList<Service>();
        List<FixedPhoneService> fpserviceList = entityManager.createNamedQuery("FixedPhoneService.findAll", FixedPhoneService.class).getResultList();
        List<MobileInternetService> miserviceList = entityManager.createNamedQuery("MobileInternetService.findAll", MobileInternetService.class).getResultList();
        List<MobilePhoneService> mobilePhoneServiceList = entityManager.createNamedQuery("MobilePhoneService.findAll", MobilePhoneService.class).getResultList();
        List<FixedInternetService> fixedInternetServices = entityManager.createNamedQuery("FixedInternetService.findAll", FixedInternetService.class).getResultList();

        serviceList.addAll(fpserviceList);
        serviceList.addAll(miserviceList);
        serviceList.addAll(mobilePhoneServiceList);
        serviceList.addAll(fixedInternetServices);

        return serviceList;
    }

    public List<Service> findServicesFromServicePackageId(int id){
        List<Service> allServices= findAllServices();
        List<Service> servicesPackage = new ArrayList<>();
        for(Service s : allServices){
            if(s.getServicePackage().getId()==id)
                servicesPackage.add(s);
        }
        return servicesPackage;
    }
}
