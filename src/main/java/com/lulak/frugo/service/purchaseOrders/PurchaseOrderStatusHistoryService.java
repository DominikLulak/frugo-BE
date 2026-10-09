package com.lulak.frugo.service.purchaseOrders;

import com.lulak.frugo.model.Status;
import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.model.purchaseOrders.PurchaseOrder;
import com.lulak.frugo.model.purchaseOrders.PurchaseOrderStatusHistory;
import com.lulak.frugo.repository.purchaseOrders.PurchaseOrderStatusHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderStatusHistoryService {

    private final PurchaseOrderStatusHistoryRepository historyRepository;

    public PurchaseOrderStatusHistoryService(
            PurchaseOrderStatusHistoryRepository historyRepository
    ){
        this.historyRepository = historyRepository;
    }

    @Transactional
    public void recordStatusChange(
            PurchaseOrder purchaseOrder,
            Status oldStatus,
            Status newStatus,
            Employee employee,
            String note
    ){
        PurchaseOrderStatusHistory history =
                new PurchaseOrderStatusHistory();

        history.setPurchaseOrder(purchaseOrder);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setEmployee(employee);
        history.setNote(note);

        historyRepository.save(history);

    }
}
