package com.lulak.frugo.model;

import javax.print.DocFlavor;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private String category;
    private String image;
    private List<String> variants;

    public Product() {}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}

    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}

    public List<String> getVariants() {return variants;}
    public void setVariants(List<String> variants) {this.variants = variants;}
}
