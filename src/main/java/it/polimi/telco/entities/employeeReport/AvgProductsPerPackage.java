package it.polimi.telco.entities.employeeReport;

import it.polimi.telco.entities.Alert;
import it.polimi.telco.entities.ServicePackage;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQuery(name="AvgProductsPerPackage.findAll", query ="select n from AvgProductsPerPackage n" )
@NamedQuery(name="AvgProductsPerPackage.findByPackage", query="select n from AvgProductsPerPackage n where n.package_id=:package_id")
@Table(name="avgnumofproductsperpackage", schema="telco")
public class AvgProductsPerPackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="package_id", nullable = false)
    private int package_id;

    @OneToOne
    @JoinColumn(name="package_id")
    private ServicePackage servicePackage;

    private float average;


    public AvgProductsPerPackage() {

    }

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

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }
}
