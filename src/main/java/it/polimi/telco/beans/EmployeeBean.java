package it.polimi.telco.beans;

import it.polimi.telco.entities.Employee;
import it.polimi.telco.entities.User;
import it.polimi.telco.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.util.List;

@Stateless
public class EmployeeBean {
    @PersistenceContext
    private EntityManager em;
    public EmployeeBean(){

    }
    public Employee findById(int employeeId){
        return em.find(Employee.class,employeeId);
    }
    public Integer checkCredentials(String usrn,String pwd) throws CredentialsException {
        List<Employee> empList;
        try {
            empList=em.createNamedQuery("Employee.checkCredentials",Employee.class).setParameter(1,usrn).setParameter(2,pwd).getResultList();
        } catch (PersistenceException e){
            throw new CredentialsException("Could not verify credentials");
        }
        if (empList.isEmpty())
            return null;
        else {
            Employee found=empList.get(0);
            return found.getId();
        }

    }

    public Integer checkUsername(String usrn) throws CredentialsException{
        List<Employee> employeeList;
        try {
            employeeList=em.createNamedQuery("Employee.checkUsername",Employee.class).setParameter("username",usrn).getResultList();
        }
        catch (PersistenceException e){
            throw new CredentialsException("could not verify username");
        }
        if(employeeList.isEmpty())
            return null;
        else {
            Employee found= employeeList.get(0);
            return found.getId();
        }
    }

    public Employee createEmployee(String username,String password) throws SQLException{
        Employee newEmployee=new Employee();
        newEmployee.setUsername(username);
        newEmployee.setPassword(password);
        em.persist(newEmployee);
        em.flush();
        return newEmployee;
    }

}
