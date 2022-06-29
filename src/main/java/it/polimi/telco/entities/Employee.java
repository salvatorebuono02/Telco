package it.polimi.telco.entities;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "employee",schema = "telco")
@NamedQuery(name = "Employee.checkCredentials",query ="SELECT e FROM Employee e where e.username = ?1 and e.password = ?2")
@NamedQuery(name = "Employee.checkUsername",query = "select e from Employee e where e.username=:username")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
