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

    @Column(name = "name",length = 45)
    private String name;

    @Column(name = "lastname",length = 45)
    private String lastname;

    @Column(name = "email",length = 45)
    private String email;

    @Column(name = "username",length = 45)
    private String username;
    @Column(name = "password",length = 45)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
