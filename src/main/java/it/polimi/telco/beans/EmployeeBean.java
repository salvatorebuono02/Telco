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
    public Employee createEmployee(String username, String password, String name,
                                   String lastname, String email) throws SQLException {
        Employee newEmployee = new Employee();
        newEmployee.setUsername(username);
        newEmployee.setName(name);
        newEmployee.setLastname(lastname);
        newEmployee.setEmail(email);
        newEmployee.setPassword(password);
        em.persist(newEmployee);
        em.flush();
        return newEmployee;
    }
    public List<Service> findAllServices() {
        return em.createNamedQuery("Service.findAll",Service.class).getResultList();
    }
    public List<Product> findAllProducts() {
        return em.createNamedQuery("Products.findAll", Product.class).getResultList();
    }
    public List<ValidityPeriod> findAllValidityPeriods() {
        return em.createNamedQuery("ValidityPeriod.findAll", ValidityPeriod.class).getResultList();
    }
    public Service findServiceById(int serviceId) {
        return em.createNamedQuery("Service.findFromId",Service.class).setParameter("id",serviceId).getSingleResult();
    }
    public ServicePackage createServicePackage(String name, ArrayList<Product> products,
                                               ArrayList<ValidityPeriod> validityPeriods,
                                               ArrayList<Service> serviceArrayList) throws SQLException{
            ServicePackage servicePackage=new ServicePackage();
            em.persist(servicePackage);
            em.flush();
            servicePackage.setName(name);
            servicePackage.setProducts(products);
            servicePackage.setValidityPeriod(validityPeriods);
            servicePackage.setServices(serviceArrayList);

            return servicePackage;
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
            BestProduct p= em.createNamedQuery("BestProduct.findOne", BestProduct.class).getSingleResult();
            System.out.println("best product: "+ p);
            return p;
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
    public void updateServices(List<Integer> id, ServicePackage servicePackage) {

        for (int i:id){
            Service s1=findServiceById(i);
            s1.setServicePackages(servicePackage);
            Service s2=em.merge(s1);
        }


    }
    public Service getManagedService(Service s) {
        Service s1=em.merge(s);
        return s1;
    }
}
