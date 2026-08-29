package com.lulak.frugo.model.product;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    @Column(name = "product_code", nullable = false, unique = true)
    private String productCode;

    @Column(nullable = false)
    private String name;

    @Column(name = "price_per_unit", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerUnit;

    @Column(name = "shelf_life_days", nullable = false)
    private Integer shelfLifeDays;

    @Column(name = "is_for_sale", nullable = false)
    private boolean forSale;

    public Product(){}

    public Integer getId(){ return id; }

    public ProductType getProductType(){ return productType; }
    public void setProductType(ProductType productType){ this.productType = productType; }

    public String getProductCode(){ return productCode; }
    public void setProductCode(String productCode){ this.productCode = productCode; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public BigDecimal getPricePerUnit(){ return pricePerUnit; }
    public void setPricePerUnit(BigDecimal pricePerUnit){ this.pricePerUnit = pricePerUnit; }

    public Integer getShelfLifeDays(){ return shelfLifeDays; }
    public void setShelfLifeDays(Integer shelfLifeDays){ this.shelfLifeDays = shelfLifeDays; }

    public boolean isForSale(){ return forSale; }
    public void setForSale(boolean forSale){ this.forSale = forSale; }
}
