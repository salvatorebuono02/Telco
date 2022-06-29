package it.polimi.telco.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user",schema = "telco")
@NamedQuery(name="User.checkCredentials", query = "SELECT u FROM User u where u.username = ?1 and u.password = ?2")
@NamedQuery(name = "User.checkUsername",query = "select u from User u where u.username=:username")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstname;
    private String lastname;
    private String username;
    private String password;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
    private List<ServicePackage> servicePackages;


    public User(){

    }

    public int getId() {
        return id;
    }

    public List<ServicePackage> getServicePackages() {
        return servicePackages;
    }

    public void setServicePackages(List<ServicePackage> servicePackages) {
        this.servicePackages = servicePackages;
    }

    public void addServicePackage(ServicePackage servicePackage){
        getServicePackages().add(servicePackage);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
