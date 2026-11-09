package com.lulak.frugo.service.order;

import com.lulak.frugo.dto.event.OrderStatusHistoryListDto;
import com.lulak.frugo.model.Status;
import com.lulak.frugo.model.employee.Employee;
import com.lulak.frugo.model.order.Order;
import com.lulak.frugo.model.order.OrderStatusHistory;
import com.lulak.frugo.repository.order.OrderStatusHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusHistoryService {

    private final OrderStatusHistoryRepository historyRepository;

    public OrderStatusHistoryService(
            OrderStatusHistoryRepository historyRepository
    ){
        this.historyRepository = historyRepository;
    }

    public List<OrderStatusHistoryListDto> getFilteredStatusHistories(
            String orderNumber,
            String employeeName
    ){
        return historyRepository.getFilteredStatusHistories(
                orderNumber,
                employeeName
        );
    }

    @Transactional
    public void recordStatusChange(
            Order order,
            Status oldStatus,
            Status newStatus,
            Employee employee,
            String note
    ){
        OrderStatusHistory history =
                new OrderStatusHistory();

        history.setOrder(order);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setEmployee(employee);
        history.setNote(note);

        historyRepository.save(history);
    }
}
