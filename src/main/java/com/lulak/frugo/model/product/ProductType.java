package com.lulak.frugo.model.product;

import jakarta.persistence.*;

@Entity
@Table(name = "product_type")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String name;

    public ProductType(){ }

    public Integer getId(){ return id; }

    public Category getCategory(){ return category; }
    public void setCategory(Category category){ this.category = category; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }
}
