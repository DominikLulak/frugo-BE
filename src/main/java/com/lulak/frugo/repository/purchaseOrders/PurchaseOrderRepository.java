package com.lulak.frugo.repository.purchaseOrders;

import com.lulak.frugo.dto.purchaseOrders.AdminPurchaseOrderListDto;
import com.lulak.frugo.model.purchaseOrders.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.purchaseOrders.AdminPurchaseOrderListDto(
            po.id,
            po.purchaseOrderNumber,
            sup.name,
            po.createdAt,
            CONCAT(e.firstName, ' ', e.lastName), 
            s.code
        )
        FROM PurchaseOrder po
        JOIN po.supplier sup
        JOIN po.employee e
        JOIN po.status s
        WHERE (
            COALESCE(:purchaseOrderNumber, '') = ''
            OR po.purchaseOrderNumber LIKE CONCAT('%', :purchaseOrderNumber, '%')
        )
        AND (
            COALESCE(:supplierName, '') = ''
            OR sup.name LIKE CONCAT('%', :supplierName, '%') 
        )
        AND (
            COALESCE(:employeeName, '') = ''
            OR LOWER(CONCAT(e.firstName, ' ', e.lastName))
                LIKE LOWER(CONCAT('%', :employeeName, '%')) 
        )
        AND (
            COALESCE(:statusCode, '') = ''
            OR s.code LIKE CONCAT('%', :statusCode, '%') 
        )
    """)
    List<AdminPurchaseOrderListDto> getFilteredPurchaseOrders(
            @Param("purchaseOrderNumber") String purchaseOrderNumber,
            @Param("supplierName") String supplierName,
            @Param("employeeName") String employeeName,
            @Param("statusCode") String statusCode
    );

    @Query("""
        SELECT po
        FROM PurchaseOrder po
        WHERE po.id = :id
    """)
    PurchaseOrder findPurchaseOrderById(
            @Param("id") Integer id
    );
}
