package it.polimi.telco.entities;

import it.polimi.telco.entities.services.FixedInternetService;
import it.polimi.telco.entities.services.FixedPhoneService;
import it.polimi.telco.entities.services.MobileInternetService;
import it.polimi.telco.entities.services.MobilePhoneService;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "service_package",schema = "telco")
@NamedQuery(name = "ServicePackage.findAll",query = "select sp from ServicePackage sp")
public class ServicePackage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private int id;
    private String name;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;


    @ManyToMany
    private List<Product> products;
/*
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    private List<FixedPhoneService> fixedPhoneServices;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    private List<FixedInternetService> fixedInternetServices;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    private List<MobilePhoneService> mobilePhoneServices;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    private List<MobileInternetService> mobileInternetServices;
*/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "validityPeriodId")
    private ValidityPeriod validityPeriod;

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
