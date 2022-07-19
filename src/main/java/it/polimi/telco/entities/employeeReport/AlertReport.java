package it.polimi.telco.entities.employeeReport;

import it.polimi.telco.entities.Alert;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@NamedQuery(name="AlertReport.findAll", query="select a from AlertReport a")
@Table(name="alertsForReport", schema="telco")
public class AlertReport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private int alert_id;
    @OneToOne
    @JoinColumn(name="alert_id")
    private Alert alert;

    public int getAlert_id() {
        return alert_id;
    }

    public void setAlert_id(int alert_id) {
        this.alert_id = alert_id;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }
}

