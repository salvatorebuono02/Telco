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
@NamedQuery(name="ServicePackage.findId", query= "select sp from ServicePackage sp where sp.id =:id")
public class ServicePackage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private int id;
    private String name;

    @OneToMany(mappedBy ="service",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "service_package_product", joinColumns = {@JoinColumn(name = "SERVICEPACKAGES_ID")}, inverseJoinColumns = {@JoinColumn(name = "PRODUCTS_ID")})
    private List<Product> products;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "service_package_validity_periods", joinColumns = {@JoinColumn(name = "servicePackage_id")}, inverseJoinColumns = {@JoinColumn(name = "validityPeriod_id")})
    private List<ValidityPeriod> validityPeriods;

    public List<ValidityPeriod> getValidityPeriods() {
        return validityPeriods;
    }

    public void setValidityPeriod(List<ValidityPeriod> validityPeriods) {
        this.validityPeriods = validityPeriods;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
