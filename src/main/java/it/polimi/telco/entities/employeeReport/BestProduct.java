package it.polimi.telco.entities.employeeReport;

import it.polimi.telco.entities.Product;
import it.polimi.telco.entities.ServicePackage;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@NamedQuery(name="BestProduct.findOne", query="select p from BestProduct p where " +
        "p.sales= (select max(p1.sales) from BestProduct p1)")
@Table(name="salesForEachOptProduct", schema="telco")
public class BestProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="optionalProduct_id", nullable = false)
    private int optionalProduct_id;
    @OneToOne
    @JoinColumn(name="optionalProduct_id")
    private Product product;
    private float sales;

    public int getOptionalProduct_id() {
        return optionalProduct_id;
    }

    public void setOptionalProduct_id(int optionalProduct_id) {
        this.optionalProduct_id = optionalProduct_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getSales() {
        return sales;
    }

    public void setSales(float sales) {
        this.sales = sales;
    }
}
