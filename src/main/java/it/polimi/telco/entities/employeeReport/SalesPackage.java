package it.polimi.telco.entities.employeeReport;

import it.polimi.telco.entities.ServicePackage;
import it.polimi.telco.entities.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQuery(name="SalesPackage.findAll", query="select s from SalesPackage s ")
@Table(name="salesPackage", schema="telco")
public class SalesPackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="package_id", nullable = false)
    private int package_id;
    @OneToOne
    @JoinColumn(name="package_id")
    private ServicePackage servicePackage;
    private int totalSalesWithProduct=0;
    private int totalSalesWithoutProduct=0;

    public int getPackage_id() {
        return package_id;
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public int getTotalSalesWithProduct() {
        return totalSalesWithProduct;
    }

    public void setTotalSalesWithProduct(int totalSalesWithProduct) {
        this.totalSalesWithProduct = totalSalesWithProduct;
    }

    public int getTotalSalesWithoutProduct() {
        return totalSalesWithoutProduct;
    }

    public void setTotalSalesWithoutProduct(int totalSalesWithoutProduct) {
        this.totalSalesWithoutProduct = totalSalesWithoutProduct;
    }
}
