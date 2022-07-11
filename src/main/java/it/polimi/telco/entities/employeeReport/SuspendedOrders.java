package it.polimi.telco.entities.employeeReport;

import it.polimi.telco.entities.Order;
import it.polimi.telco.entities.ServicePackage;

import javax.persistence.*;

@Entity
@NamedQuery(name="SuspendedOrders.findAll", query="select s from SuspendedOrders s ")
@Table(name="suspendedorders", schema="telco")
public class SuspendedOrders {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="order_id", nullable = false)
    private int order_id;

    @OneToOne
    @JoinColumn(name="order_id")
    private Order order;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
