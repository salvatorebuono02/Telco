package it.polimi.telco.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order",schema = "telco")

@NamedQuery(name="Order.findFromId",query="select o from Order o where o.id=:id")
@NamedQuery(name="Order.findFromCreator", query="select distinct o from Order o where o.creator=: user")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date_of_creation;
    private LocalDate date_of_subscription;

    private boolean confirmed;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "creator")
    private User creator;

    @ManyToOne(fetch= FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name= "serviceId")
    private ServicePackage service;

    //TODO orphanRemoval?
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="validityId")
    private ValidityPeriod validityPeriod;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "order_product",joinColumns = {@JoinColumn(name = "order_id")},inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private List<Product> products;

    private float totalValueOrder;

    private float totalvalueservices;

    public float getTotalvalueservices() {
        return totalvalueservices;
    }

    public void setTotalvalueservices(float totalvalueservices) {
        this.totalvalueservices = totalvalueservices;
    }

    public float getTotalvalueproducts() {
        return totalvalueproducts;
    }

    public void setTotalvalueproducts(float totalvalueproducts) {
        this.totalvalueproducts = totalvalueproducts;
    }

    private float totalvalueproducts;
    private LocalDate date_end_subscription;
    public Order() {

    }
    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Date getDate_of_creation() {
        return date_of_creation;
    }

    public void setDate_of_creation(Date date_of_creation) {
        this.date_of_creation = date_of_creation;
    }

    public LocalDate getDate_of_subscription() {
        return date_of_subscription;
    }

    public void setDate_of_subscription(LocalDate date_of_subscription) {
        this.date_of_subscription = date_of_subscription;
    }

    public LocalDate getDate_end_subscription() {
        return date_end_subscription;
    }

    public void setDate_end_subscription(LocalDate date_end_subscription) {
        this.date_end_subscription = date_end_subscription;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public ServicePackage getService() {
        return service;
    }

    public void setService(ServicePackage service) {
        this.service = service;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public float getTotalValueOrder() {
        return totalValueOrder;
    }

    public void setTotalValueOrder(float totalValueOrder) {
        this.totalValueOrder = totalValueOrder;
    }


}
