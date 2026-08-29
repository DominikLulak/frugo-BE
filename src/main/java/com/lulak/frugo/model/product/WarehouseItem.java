package com.lulak.frugo.model.product;

import com.lulak.frugo.model.Country;
import com.lulak.frugo.model.warehouse.Location;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "warehouse_item")
public class WarehouseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "eti_number", nullable = false, unique = true)
    private String etiNumber;

    @ManyToOne
    @JoinColumn(name = "packaging_id", nullable = false)
    private Packaging packagingType;

    @Column(name = "quantity_per_package", nullable = false)
    private Integer quantityPerPackage;

    @ManyToOne
    @JoinColumn(name = "subpackaging_id")
    private Packaging subpackageType;

    @Column(name = "quantity_per_subpackage")
    private Integer quantityPerSubpackage;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "country_of_origin_id", nullable = false)
    private Country countryOfOrigin;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "allocated_quantity", nullable = false)
    private Integer allocatedQuantity = 0;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    public Integer getId(){ return id; }

    public Product getProduct(){ return product; }
    public void setProduct(Product product){ this.product = product; }

    public String getEtiNumber(){ return etiNumber; }
    public void setEtiNumber(String etiNumber){ this.etiNumber = etiNumber; }

    public Packaging getPackageType(){ return packagingType; }
    public void setPackageType(Packaging packagingType){ this.packagingType = packagingType; }

    public Integer getQuantityPerPackage(){ return quantityPerPackage; }
    public void setQuantityPerPackage(Integer quantityPerPackage){ this.quantityPerPackage = quantityPerPackage; }

    public Packaging getSubpackageType(){ return subpackageType; }
    public void setSubpackageType(Packaging subpackageType){ this.subpackageType = subpackageType; }

    public Integer getQuantityPerSubpackage(){ return quantityPerSubpackage; }
    public void setQuantityPerSubpackage(Integer quantityPerSubpackage){ this.quantityPerSubpackage = quantityPerSubpackage; }

    public Integer getQuantity(){ return quantity; }
    public void setQuantity(Integer quantity){ this.quantity = quantity; }

    public Country getCountryOfOrigin(){ return countryOfOrigin; }
    public void setCountryOfOrigin(Country countryOfOrigin){ this.countryOfOrigin = countryOfOrigin; }

    public LocalDateTime getReceivedAt(){ return receivedAt; }
    public void setReceivedAt(LocalDateTime receivedAt){ this.receivedAt = receivedAt; }

    public LocalDate getExpirationDate(){ return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate){ this.expirationDate = expirationDate; }

    public Integer getAllocatedQuantity(){ return allocatedQuantity; }
    public void setAllocatedQuantity(Integer allocatedQuantity){ this.allocatedQuantity = allocatedQuantity; }

    public Location getLocation(){ return location; }
    public void setLocation(Location location){ this.location = location; }
}
