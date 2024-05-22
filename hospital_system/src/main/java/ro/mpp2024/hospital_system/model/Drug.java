package ro.mpp2024.hospital_system.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "drugs")
public class Drug implements Identifiable<Long>, Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "stock")
    private int stock;

    @Column(name = "contraindications")
    private String contraindications;

    public Drug() {
        id = 0L;
    }

    public Drug(String name, int stock, String contraindications) {
        id = 0L;
        this.name = name;
        this.stock = stock;
        this.contraindications = contraindications;
    }

    public Drug(Long id, String name, int stock, String contraindications) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.contraindications = contraindications;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;

    }

    @Override
    public String toString() {
        return "Drug{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", contraindications='" + contraindications + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getContraindications() {
        return contraindications;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }
}
