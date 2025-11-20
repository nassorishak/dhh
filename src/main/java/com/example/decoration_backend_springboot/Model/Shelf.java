package com.example.decoration_backend_springboot.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "shelf")
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shelfId;

    private String shelfName;
    private String locationDescription;

    // ⚠️ ADD THIS - This prevents circular reference
    @OneToMany(mappedBy = "shelf")
    @JsonIgnore
    private List<Product> products;

    // Constructors
    public Shelf() {}

    public Shelf(String shelfName, String locationDescription) {
        this.shelfName = shelfName;
        this.locationDescription = locationDescription;
    }

    // Getters and Setters
    public Integer getShelfId() { return shelfId; }
    public void setShelfId(Integer shelfId) { this.shelfId = shelfId; }

    public String getShelfName() { return shelfName; }
    public void setShelfName(String shelfName) { this.shelfName = shelfName; }

    public String getLocationDescription() { return locationDescription; }
    public void setLocationDescription(String locationDescription) { this.locationDescription = locationDescription; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public Integer getId() {
        return this.shelfId;
    }
}

