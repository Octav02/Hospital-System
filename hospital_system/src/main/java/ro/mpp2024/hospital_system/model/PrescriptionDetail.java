package ro.mpp2024.hospital_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import javafx.util.Pair;

import java.io.Serializable;

@Entity
@Table(name = "prescription_details")
public class PrescriptionDetail implements Identifiable<Pair<Long,Long>>, Serializable {

    @Id
    @Column(name = "prescription_id")
    private Long prescriptionId;
    @Id
    @Column(name = "drug_id")
    private Long drugId;
    @Column(name = "quantity")
    private int quantity;

    @Override
    public Pair<Long, Long> getId() {
        return new Pair<>(prescriptionId, drugId);
    }

    @Override
    public void setId(Pair<Long, Long> id) {
        this.prescriptionId = id.getKey();
        this.drugId = id.getValue();
    }

    public PrescriptionDetail() {
        prescriptionId = 0L;
        drugId = 0L;
    }

    public PrescriptionDetail(Long prescriptionId, Long drugId, int quantity) {
        this.prescriptionId = prescriptionId;
        this.drugId = drugId;
        this.quantity = quantity;
    }

    public PrescriptionDetail(Long drugId, int quantity) {
        this.quantity = quantity;
        this.drugId = drugId;
    }

    public PrescriptionDetail(Pair<Long, Long> id, int quantity) {
        this.prescriptionId = id.getKey();
        this.drugId = id.getValue();
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId=" + prescriptionId +
                ", drugId=" + drugId +
                ", quantity=" + quantity +
                '}';
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
