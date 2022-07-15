package it.polimi.telco.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "product",schema = "telco")

@NamedQuery(name = "Products.findAll", query = "SELECT p FROM Product p")
@NamedQuery(name = "Product.findById",query = "select p from Product p where p.id=:id")
@NamedQuery(name="Product.findByOrder", query="select p from Product p where p.order=:order")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",length = 45)
    private String name;
    private int monthly_fee;

    @ManyToMany(mappedBy = "products",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<ServicePackage> servicePackages;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name="orderId")
    private Order order;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order=order;
    }
}
