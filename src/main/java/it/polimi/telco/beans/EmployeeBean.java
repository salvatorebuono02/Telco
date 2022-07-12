package it.polimi.telco.beans;

import it.polimi.telco.entities.*;
import it.polimi.telco.entities.employeeReport.*;
import it.polimi.telco.entities.services.*;
import it.polimi.telco.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Stateless
public class EmployeeBean {
    @PersistenceContext
    private EntityManager em;

    public EmployeeBean() {

    }

    public Employee findById(int employeeId) {
        return em.find(Employee.class, employeeId);
    }

    public Integer checkCredentials(String usrn, String pwd) throws CredentialsException {
        List<Employee> empList;
        try {
            empList = em.createNamedQuery("Employee.checkCredentials", Employee.class).setParameter(1, usrn).setParameter(2, pwd).getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentials");
        }
        if (empList.isEmpty())
            return null;
        else {
            Employee found = empList.get(0);
            return found.getId();
        }

    }

    public Integer checkUsername(String usrn) throws CredentialsException {
        List<Employee> employeeList;
        try {
            employeeList = em.createNamedQuery("Employee.checkUsername", Employee.class).setParameter("username", usrn).getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("could not verify username");
        }
        if (employeeList.isEmpty())
            return null;
        else {
            Employee found = employeeList.get(0);
            return found.getId();
        }
    }

    public Employee createEmployee(String username, String password) throws SQLException {
        Employee newEmployee = new Employee();
        newEmployee.setUsername(username);
        newEmployee.setPassword(password);
        em.persist(newEmployee);
        em.flush();
        return newEmployee;
    }


    public List<Service> findAllServices() {
        List<Service> serviceList = new ArrayList<Service>();
        List<FixedPhoneService> fpserviceList = em.createNamedQuery("FixedPhoneService.findAll", FixedPhoneService.class).getResultList();
        List<MobileInternetService> miserviceList = em.createNamedQuery("MobileInternetService.findAll", MobileInternetService.class).getResultList();
        List<MobilePhoneService> mobilePhoneServiceList = em.createNamedQuery("MobilePhoneService.findAll", MobilePhoneService.class).getResultList();
        List<FixedInternetService> fixedInternetServices = em.createNamedQuery("FixedInternetService.findAll", FixedInternetService.class).getResultList();

        for (int i = 0; i < fpserviceList.size(); i++)
            serviceList.add(fpserviceList.get(i));
        for (int i = 0; i < miserviceList.size(); i++)
            serviceList.add(miserviceList.get(i));
        for (int i = 0; i < mobilePhoneServiceList.size(); i++)
            serviceList.add(mobilePhoneServiceList.get(i));
        for (int i = 0; i < fixedInternetServices.size(); i++)
            serviceList.add(fixedInternetServices.get(i));

        return serviceList;
    }

    public List<Product> findAllProducts() {
        return em.createNamedQuery("Products.findAll", Product.class).getResultList();
    }

    public List<ValidityPeriod> findAllValidityPeriods() {
        return em.createNamedQuery("ValidityPeriod.findAll", ValidityPeriod.class).getResultList();
    }

    public Service findServiceById(int serviceId,String type) {
        if(Objects.equals(type, "FixedInternetService")){
            List<FixedInternetService> fixedInternetServices =em.createNamedQuery("FixedInternetService.findAll", FixedInternetService.class).getResultList();
            for (int i = 0; i < fixedInternetServices.size(); i++) {
                if (fixedInternetServices.get(i).getId() == serviceId)
                    return fixedInternetServices.get(i);
            }
        }
        if(Objects.equals(type, "FixedPhoneService")){
            List<FixedPhoneService> fixedPhoneServices = em.createNamedQuery("FixedPhoneService.findAll", FixedPhoneService.class).getResultList();
            for (int i = 0; i < fixedPhoneServices.size(); i++) {
                if (fixedPhoneServices.get(i).getId() == serviceId)
                    return fixedPhoneServices.get(i);
            }
        }
        if(Objects.equals(type, "MobileInternetService")){
            List<MobileInternetService> mobileInternetServices =em.createNamedQuery("MobileInternetService.findAll", MobileInternetService.class).getResultList();
            for (int i = 0; i < mobileInternetServices.size(); i++) {
                if (mobileInternetServices.get(i).getId() == serviceId)
                    return mobileInternetServices.get(i);
            }
        }
        if(Objects.equals(type, "MobilePhoneService")){
            List<MobilePhoneService> mobilePhoneServices =em.createNamedQuery("MobilePhoneService.findAll", MobilePhoneService.class).getResultList();
            for (int i = 0; i < mobilePhoneServices.size(); i++) {
                if (mobilePhoneServices.get(i).getId() == serviceId)
                    return mobilePhoneServices.get(i);
            }
        }

        return null;
    }


    public ServicePackage createServicePackage(String name, ArrayList<Product> products, ArrayList<ValidityPeriod> validityPeriods) throws SQLException{
            ServicePackage servicePackage=new ServicePackage();
            servicePackage.setName(name);
            servicePackage.setProducts(products);
            servicePackage.setValidityPeriod(validityPeriods);
            ServicePackage servicePackage1= em.merge(servicePackage);
            return servicePackage1;
    }

    public List<AlertReport> findAllAlertReport(){
        try {
            return em.createNamedQuery("AlertReport.findAll", AlertReport.class).getResultList();

        }catch (PersistenceException e){
            return null;
        }
    }

    public List<AvgProductsPerPackage> findAllAvgProd(){
        try {
            return em.createNamedQuery("AvgProductsPerPackage.findAll", AvgProductsPerPackage.class).getResultList();
        }
        catch (PersistenceException e){
            return null;
        }
    }
    public List<AvgProductsPerPackage> findByPackage(int package_id){
        try {
            return em.createNamedQuery("AvgProductsPerPackage.findByPackage", AvgProductsPerPackage.class).setParameter("package_id", package_id).getResultList();
        }
        catch (PersistenceException e){
            return null;
        }
    }

    public BestProduct findBestProduct(){
        try {
            return em.createNamedQuery("BestProduct.findOne", BestProduct.class).getSingleResult();
        } catch (PersistenceException e){
            return null;
        }
    }

    public List<InsolventUsers> findAllInsolvents(){
        try {
            return em.createNamedQuery("InsolventUsers.findAll", InsolventUsers.class).getResultList();
        } catch (PersistenceException e){
            return null;
        }
    }

    public List<SalesPackage> findAllSalesPack(){
        try {
            return em.createNamedQuery("SalesPackage.findAll", SalesPackage.class).getResultList();
        }
        catch (PersistenceException e){
            return null;
        }
    }

    public List<SuspendedOrders> findAllSuspended(){
        try {
            return em.createNamedQuery("SuspendedOrders.findAll", SuspendedOrders.class).getResultList();
        }
        catch (PersistenceException e){
            return null;
        }
    }

    public List<TotalPurchasePackage> findAllPurchasePack(){
        try {
            return em.createNamedQuery("TotalPurchasePackage.findAll", TotalPurchasePackage.class).getResultList();
        }
        catch (PersistenceException e){
            return null;
        }
    }
    public List<TotalPurchasePackage> findByPackageWithoutValidity(int package_id){
        try {
            return em.createNamedQuery("TotalPurchasePackage.findByPackage", TotalPurchasePackage.class).setParameter("package_id",package_id).getResultList();
        }
        catch (PersistenceException e){
            return null;
        }
    }

    public List<TotalPurchasePackageAndValidity> findAllPurchasePackAndVal(){
        try {
            return em.createNamedQuery("TotalPurchasePackageAndValidity.findAll", TotalPurchasePackageAndValidity.class).getResultList();
        }
        catch (PersistenceException e){
            return null;
        }
    }
    public List<TotalPurchasePackageAndValidity> findByValidity(int valPeriod_id){
        return em.createNamedQuery("TotalPurchasePackageAndValidity.findByValidity", TotalPurchasePackageAndValidity.class).setParameter("valPeriod_id",valPeriod_id).getResultList();
    }

    public List<TotalPurchasePackageAndValidity> findByPackageWithValidity(int package_id){
        return em.createNamedQuery("TotalPurchasePackageAndValidity.findByPackage", TotalPurchasePackageAndValidity.class).setParameter("package_id",package_id).getResultList();
    }


    public void updateServices(ArrayList<Integer> fis,ArrayList<Integer> fps,ArrayList<Integer> mps,ArrayList<Integer> mis, ServicePackage servicePackage) {
        /*List<Integer> servicesId=new ArrayList<>();
        for (Service s:serviceArrayList){
            servicesId.add(s.getId());
        }*/
        if (fps!=null){
            for (int i:fps){
                Service fps1=findServiceById(i,"FixedPhoneService");
                fps1.setServicePackage(servicePackage);
                Service fps2=em.merge(fps1);
            }
        }
        if (fis!=null){
            for (int i:fis){
                Service fis1=findServiceById(i,"FixedInternetService");
                fis1.setServicePackage(servicePackage);
                Service fis2=em.merge(fis1);
            }
        }
        if (mis!=null){
            for (int i:mis){
                Service mis1 =findServiceById(i,"MobileInternetService");
                mis1.setServicePackage(servicePackage);
                Service mis2=em.merge(mis1);
            }
        }
        if (mps!=null){
            for (int i:mps){
                Service mps1 =findServiceById(i,"MobilePhoneService");
                mps1.setServicePackage(servicePackage);
                Service fps2=em.merge(mps1);
            }
        }
    }
}
