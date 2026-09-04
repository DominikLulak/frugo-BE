package com.lulak.frugo.model.purchaseOrders;

import com.lulak.frugo.model.Country;
import com.lulak.frugo.model.Status;
import com.lulak.frugo.model.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "purchase_order_item")
public class PurchaseOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "received_quantity", nullable = false)
    private Integer receivedQuantity = 0;

    @ManyToOne
    @JoinColumn(name = "country_of_origin_id", nullable = false)
    private Country country;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    public Integer getId(){ return id; }

    public PurchaseOrder getPurchaseOrder(){ return purchaseOrder; }
    public void setPurchaseOrder(PurchaseOrder purchaseOrder){ this.purchaseOrder = purchaseOrder; }

    public Product getProduct(){ return product; }
    public void setProduct(Product product){ this.product = product; }

    public Integer getQuantity(){ return quantity; }
    public void setQuantity(Integer quantity){ this.quantity = quantity; }

    public Integer getReceivedQuantity(){ return receivedQuantity; }
    public void setReceivedQuantity(Integer receivedQuantity){ this.receivedQuantity = receivedQuantity; }

    public Country getCountry(){ return country; }
    public void setCountry(Country country){ this.country = country; }

    public Status getStatus(){ return status; }
    public void setStatus(Status status){ this.status = status; }
}
