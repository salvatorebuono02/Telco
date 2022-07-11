package it.polimi.telco.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "product",schema = "telco")

@NamedQuery(name = "Products.findAll", query = "SELECT p FROM Product p")

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int monthly_fee;
    private int validityPeriod;

    public int getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(int validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    @ManyToMany(mappedBy = "products",fetch = FetchType.EAGER)
    private List<ServicePackage> servicePackages;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "products",cascade = CascadeType.ALL)
    private List<Order> orders;
    public String toString() {
        return monthly_fee +"Euro/month";
    }

    public List<ServicePackage> getServicePackages() {
        return servicePackages;
    }

    public void setServicePackages(List<ServicePackage> servicePackages) {
        this.servicePackages = servicePackages;
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

    public int getMonthly_fee() {
        return monthly_fee;
    }

    public void setMonthly_fee(int monthly_fee) {
        this.monthly_fee = monthly_fee;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(Order order) {
        this.orders.add(order);
    }
}
