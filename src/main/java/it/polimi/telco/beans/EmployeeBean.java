package it.polimi.telco.beans;

import it.polimi.telco.entities.*;
import it.polimi.telco.entities.services.*;
import it.polimi.telco.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
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

    public Service findServiceById(int serviceId) {
        List<FixedInternetService> fixedInternetServices =em.createNamedQuery("FixedInternetService.findAll", FixedInternetService.class).getResultList();
        for (int i = 0; i < fixedInternetServices.size(); i++) {
            if (fixedInternetServices.get(i).getId() == serviceId)
                return fixedInternetServices.get(i);
        }
        List<FixedPhoneService> fixedPhoneServices = em.createNamedQuery("FixedPhoneService.findAll", FixedPhoneService.class).getResultList();
        for (int i = 0; i < fixedPhoneServices.size(); i++) {
            if (fixedPhoneServices.get(i).getId() == serviceId)
                return fixedPhoneServices.get(i);
        }
        List<MobileInternetService> mobileInternetServices =em.createNamedQuery("MobileInternetService.findAll", MobileInternetService.class).getResultList();
        for (int i = 0; i < mobileInternetServices.size(); i++) {
            if (mobileInternetServices.get(i).getId() == serviceId)
                return mobileInternetServices.get(i);
        }
        List<MobilePhoneService> mobilePhoneServices =em.createNamedQuery("MobilePhoneService.findAll", MobilePhoneService.class).getResultList();
        for (int i = 0; i < mobilePhoneServices.size(); i++) {
            if (mobilePhoneServices.get(i).getId() == serviceId)
                return mobilePhoneServices.get(i);
        }

        return null;
    }


    public void createServicePackage(String name,ArrayList<Service> services,ArrayList<Product> products,ArrayList<ValidityPeriod> validityPeriods) throws SQLException{
        for(int i=0;i< validityPeriods.size();i++){
            ServicePackage servicePackage=new ServicePackage();
            servicePackage.setName(name);
            servicePackage.setProducts(products);
            servicePackage.setValidityPeriod(validityPeriods.get(i));
            em.persist(servicePackage);
            em.flush();
        }

    }
}
