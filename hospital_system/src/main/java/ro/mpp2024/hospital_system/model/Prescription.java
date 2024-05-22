package ro.mpp2024.hospital_system.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "prescriptions")
public class Prescription implements Identifiable<Long>, Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    private Long id;


    @Column(name = "user_id")
    private Long userId;

    @Column(name = "status")
    private Status status;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Prescription() {
        id = 0L;
    }

    public Prescription(Long userId, Status status) {
        id = 0L;
        this.userId = userId;
        this.status = status;
    }

    public Prescription(Long id, Long userId, Status status) {
        this.id = id;
        this.userId = userId;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", userId=" + userId +
                ", status=" + status +
                '}';
    }
}
