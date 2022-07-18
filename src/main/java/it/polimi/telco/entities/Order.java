package it.polimi.telco.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name="validityId")
    private ValidityPeriod validityPeriod;

    @OneToMany(mappedBy ="order", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Product> products;

    private float totalValueOrder;

    private float totalvalueservices;

    public Order(/*ArrayList<Product> products,*/ Date dc, LocalDate s, LocalDate end, ServicePackage servicePackage, ValidityPeriod validityPeriod1, float totalValue, float optionalValue, float packageValue, User user1, Boolean confirmed) {
//        this.products=products;
        this.date_of_creation=dc;
        this.date_of_subscription=s;
        this.date_end_subscription=end;
        this.service=servicePackage;
        this.validityPeriod=validityPeriod1;
        this.totalValueOrder=totalValue;
        this.totalvalueproducts=optionalValue;
        this.totalvalueservices=packageValue;
        this.creator=user1;
        this.confirmed=confirmed;
    }

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
