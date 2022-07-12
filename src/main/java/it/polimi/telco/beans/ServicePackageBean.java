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

    public Optional<ServicePackage> findServicePackageById(int id){
        return entityManager.createNamedQuery("ServicePackage.findId",ServicePackage.class).setParameter("id", id).getResultStream().findFirst();
    }

    private List<Service> findAllServices () {
        List<Service> serviceList = new ArrayList<>();
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
        System.out.println("allServices:" + allServices);
        List<Service> servicesPackage = new ArrayList<>();
        for(Service s : allServices){
            System.out.println("service package connected to service"+ s.getTypeofService()+":"+s.getServicePackage());
            if(s.getServicePackage()!=null && s.getServicePackage().getId()==id){
                System.out.println("service has servicepackageid = " + id);
                servicesPackage.add(s);
            }

        }
        System.out.println("list of services of package:" + servicesPackage);
        return servicesPackage;
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

}
