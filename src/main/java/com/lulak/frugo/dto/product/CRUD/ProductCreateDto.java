package com.lulak.frugo.dto.product.CRUD;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ProductCreateDto {

    @NotNull
    private Integer categoryId;
    @NotBlank
    private String productTypeName;

    @NotBlank
    private String productCode;
    @NotBlank
    private String name;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal pricePerUnit;
    @NotNull
    @Positive
    private Integer shelfLifeDays;
    @NotNull
    @PositiveOrZero
    private Integer reorderPoint;
    @NotNull
    @PositiveOrZero
    private Integer minimumStock;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Integer getShelfLifeDays() {
        return shelfLifeDays;
    }

    public void setShelfLifeDays(Integer shelfLifeDays) {
        this.shelfLifeDays = shelfLifeDays;
    }

    public Integer getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(Integer reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public Integer getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(Integer minimumStock) {
        this.minimumStock = minimumStock;
    }
}
