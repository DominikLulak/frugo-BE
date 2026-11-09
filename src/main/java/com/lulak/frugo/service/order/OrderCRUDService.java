package com.lulak.frugo.service.order;

import com.lulak.frugo.dto.order.CRUD.*;
import com.lulak.frugo.model.Status;
import com.lulak.frugo.model.customer.Customer;
import com.lulak.frugo.model.employee.EmployeeLogin;
import com.lulak.frugo.model.order.Order;
import com.lulak.frugo.model.order.OrderItem;
import com.lulak.frugo.model.product.Product;
import com.lulak.frugo.model.product.WarehouseItem;
import com.lulak.frugo.repository.customer.CustomerRepository;
import com.lulak.frugo.repository.employee.EmployeeLoginRepository;
import com.lulak.frugo.repository.order.OrderItemRepository;
import com.lulak.frugo.repository.order.OrderRepository;
import com.lulak.frugo.repository.product.ProductRepository;
import com.lulak.frugo.repository.product.WarehouseItemRepository;
import com.lulak.frugo.repository.referenceData.StatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderCRUDService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final WarehouseItemRepository warehouseItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final StatusRepository statusRepository;
    private final EmployeeLoginRepository employeeLoginRepository;
    private final OrderStatusHistoryService orderStatusHistoryService;

    public OrderCRUDService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            WarehouseItemRepository warehouseItemRepository,
            ProductRepository productRepository,
            CustomerRepository customerRepository,
            StatusRepository statusRepository,
            EmployeeLoginRepository employeeLoginRepository,
            OrderStatusHistoryService orderStatusHistoryService){
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.warehouseItemRepository = warehouseItemRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.statusRepository = statusRepository;
        this.employeeLoginRepository = employeeLoginRepository;
        this.orderStatusHistoryService = orderStatusHistoryService;
    }

    private OrderItem createOrderItem(
            Order order,
            WarehouseItem warehouseItem,
            Status status,
            Integer quantity
    ){
        OrderItem item = new OrderItem();

        item.setOrder(order);
        item.setWarehouseItem(warehouseItem);
        item.setQuantity(quantity);
        item.setPickedQuantity(0);
        item.setStatus(status);

        return item;
    }

    private void allocateWarehouseItems(
            Order order,
            Product product,
            Integer requestedQuantity,
            Status itemStatus
    ){
        if(requestedQuantity == null || requestedQuantity <= 0){
            throw new IllegalStateException(
                    "Requested quantity must be greater than zero"
            );
        }

        int remainingQuantity = requestedQuantity;

        List<OrderItem> existingOrderItems =
                orderItemRepository.findAllByOrderIdAndWarehouseItemProductId(
                        order.getId(),
                        product.getId()
                );

        for(OrderItem orderItem : existingOrderItems){
            if(remainingQuantity <= 0){
                break;
            }

            WarehouseItem warehouseItem = orderItem.getWarehouseItem();

            int packageQuantity = warehouseItem.getQuantityPerPackage();

            if(packageQuantity <= 0){
                throw new RuntimeException(
                        "Invalid package quantity for ETI: " + warehouseItem.getEtiNumber()
                );
            }

            int availableQuantity = warehouseItem.getQuantity() - warehouseItem.getAllocatedQuantity();

            int availablePackages = availableQuantity/packageQuantity;

            if(availablePackages <= 0){
                continue;
            }

            int requiredPackages = (int) Math.ceil(
                    (double) remainingQuantity/packageQuantity
            );

            int packageToAllocate = Math.min(requiredPackages, availablePackages);
            int allocatedQuantity = packageToAllocate * packageQuantity;

            warehouseItem.setAllocatedQuantity(
                    warehouseItem.getAllocatedQuantity() + allocatedQuantity
            );
            warehouseItemRepository.save(warehouseItem);

            orderItem.setQuantity(
                    orderItem.getQuantity() + allocatedQuantity
            );
            orderItemRepository.save(orderItem);

            remainingQuantity -= allocatedQuantity;
        }

        if(remainingQuantity > 0){
            allocateNewWarehouseItems(
                    order, product, remainingQuantity, itemStatus
            );
        }
    }

    private void allocateNewWarehouseItems(
            Order order,
            Product product,
            Integer requestedQuantity,
            Status itemStatus
    ){
        List<WarehouseItem> warehouseItems =
                warehouseItemRepository.findAvailableItemsForAllocation(
                        product.getId()
                );
        int remainingQuantity = requestedQuantity;

        for(WarehouseItem warehouseItem : warehouseItems){
            if(remainingQuantity <= 0){
                break;
            }

            int packageQuantity = warehouseItem.getQuantityPerPackage();
            if(packageQuantity <= 0){
                throw new RuntimeException(
                        "Invalid package quantity for ERI "
                        + warehouseItem.getEtiNumber()
                );
            }

            int availableQuantity = warehouseItem.getQuantity() - warehouseItem.getAllocatedQuantity();

            int availablePackages = availableQuantity / packageQuantity;
            if(availablePackages <= 0){
                break;
            }

            int requiredPackages = (int) Math.ceil(
                    (double) remainingQuantity / packageQuantity
            );

            int packagesToAllocate = Math.min(
                    requiredPackages,
                    availablePackages
            );

            int allocatedQuantity = packagesToAllocate * packageQuantity;

            warehouseItem.setAllocatedQuantity(
                    warehouseItem.getAllocatedQuantity() + allocatedQuantity
            );

            warehouseItemRepository.save(warehouseItem);

            OrderItem orderItem = createOrderItem(
                    order, warehouseItem, itemStatus, allocatedQuantity
            );
            orderItemRepository.save(orderItem);
            remainingQuantity -= allocatedQuantity;
        }

        if(remainingQuantity > 0){
            throw new RuntimeException(
                    "Insufficient stock for product "
                    + product.getProductCode()
                    + ". Missing quantity "
                    + remainingQuantity
            );
        }
    }

    private Order getEditableOrder(Integer orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found " + orderId));

        String statusCode = order.getStatus().getCode();

        if(!"ENTERED".equals(statusCode)
            && !"BLOCKED".equals(statusCode)){
            throw new RuntimeException(
                    "Order cannot be modified in status " + statusCode
            );
        }

        return order;
    }

    private Product getOrderableProduct(Integer productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found " + productId));

        if(!product.isForSale()){
            throw new RuntimeException("Product is not available for sale: " + product.getProductCode());
        }

        return product;
    }

    private void decreaseAllocation(
            List<OrderItem> orderItems,
            Integer targetQuantity
    ){
        int currentQuantity = orderItems.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();

        List<OrderItem> sortedItems = orderItems.stream()
                .sorted(
                        Comparator
                                .comparing(
                                        (OrderItem item) ->
                                                item.getPickedQuantity() > 0
                                )
                                .thenComparing(
                                        item -> item.getWarehouseItem()
                                                .getExpirationDate(),
                                        Comparator.reverseOrder()
                                )
                )
                .toList();

        for(OrderItem orderItem : sortedItems){
            WarehouseItem warehouseItem = orderItem.getWarehouseItem();
            int packageQuantity = warehouseItem.getQuantityPerPackage();

            if(packageQuantity <= 0){
                throw new IllegalStateException(
                        "Invalid package quantity for ETI: "
                        + warehouseItem.getEtiNumber()
                );
            }

            while(currentQuantity - packageQuantity >= targetQuantity
                && orderItem.getQuantity() - packageQuantity
                    >= orderItem.getPickedQuantity()
            ){
                if(warehouseItem.getAllocatedQuantity() < packageQuantity){
                    throw new IllegalStateException(
                            "Insufficient allocated quantity for ETI: "
                            + warehouseItem.getEtiNumber()
                    );
                }

                orderItem.setQuantity(
                        orderItem.getQuantity() - packageQuantity
                );
                warehouseItem.setAllocatedQuantity(
                        warehouseItem.getAllocatedQuantity() - packageQuantity
                );

                currentQuantity -= packageQuantity;
            }

            if(currentQuantity == targetQuantity){
                break;
            }
        }

        List<OrderItem> itemsToDelete = orderItems.stream()
                .filter(item -> item.getQuantity() == 0)
                .toList();

        orderItemRepository.deleteAll(itemsToDelete);

        orderItemRepository.saveAll(
                orderItems.stream()
                        .filter(item -> item.getQuantity() > 0)
                        .toList()
        );
        warehouseItemRepository.saveAll(
                orderItems.stream()
                        .map(OrderItem::getWarehouseItem)
                        .distinct()
                        .toList()
        );
    }

    private void validateStatusTransition(String currentStatus, String newStatus){
        boolean isAllowed = switch (currentStatus){
            case "ENTERED" ->
                newStatus.equals("RELEASED") ||
                        newStatus.equals("BLOCKED") ||
                        newStatus.equals("CANCELLED");
            case "RELEASED" ->
                newStatus.equals("BLOCKED") ||
                        newStatus.equals("CANCELLED");
            case "BLOCKED" ->
                newStatus.equals("RELEASED") ||
                        newStatus.equals("CANCELLED");
            default -> false;
        };

        if(!isAllowed){
            throw new IllegalStateException(
                    "Status transaction from " + currentStatus
                    + " to " + newStatus + " is not allowed"
            );
        }
    }

    private void updateOrderItemsStatus(
            List<OrderItem> orderItems,
            Status newStatus
    ){
        for(OrderItem item : orderItems){
            String currentStatus = item.getStatus().getCode();

            if("COMPLETED".equals(currentStatus)){
                continue;
            }
            item.setStatus(newStatus);
        }
        orderItemRepository.saveAll(orderItems);
    }

    private void releaseOrderAllocations(List<OrderItem> orderItems){
        for(OrderItem item : orderItems){
            WarehouseItem warehouseItem = item.getWarehouseItem();

            int newAllocatedQuantity =
                    warehouseItem.getAllocatedQuantity() - item.getQuantity();
            if(newAllocatedQuantity < 0){
                throw new IllegalStateException(
                        "Allocated quantity cannot be negative fot ETI: "
                        +warehouseItem.getEtiNumber()
                );
            }

            warehouseItem.setAllocatedQuantity(newAllocatedQuantity);
        }

        warehouseItemRepository.saveAll(
                orderItems.stream()
                        .map(OrderItem::getWarehouseItem)
                        .distinct()
                        .toList()
        );
    }

    @Transactional
    public Order createOrder(OrderCreateDto dto){
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new IllegalStateException("Customer not found " + dto.getCustomerId()));

        if(dto.getItems() == null || dto.getItems().isEmpty()){
            throw new IllegalStateException("Order must contain at least one product");
        }

        Set<Integer> productIds = new HashSet<>();

        for(OrderItemCreateDto itemDto : dto.getItems()){
            if(itemDto.getProductId() == null
                || itemDto.getQuantity() == null
                || itemDto.getQuantity() <= 0
            ){
                throw new RuntimeException("Invalid order item");
            }

            if(!productIds.add(itemDto.getProductId())){
                throw new RuntimeException(
                        "Duplicate product in order: " + itemDto.getProductId()
                );
            }
        }

        Status enteredStatus = statusRepository.findByCode("ENTERED")
                .orElseThrow(() -> new RuntimeException("Status ENTERED not found"));

        Order order = new Order();

        order.setCustomer(customer);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(enteredStatus);
        order.setOrderNumber("TEMP-" + UUID.randomUUID());

        order = orderRepository.save(order);

        String orderNumber = String.format(
                "ORD-%d-%05d",
                order.getCreatedAt().getYear(),
                order.getId()
        );

        order.setOrderNumber(orderNumber);

        for (OrderItemCreateDto itemDto: dto.getItems()){
            Product product = getOrderableProduct(itemDto.getProductId());

            allocateNewWarehouseItems(
                    order, product, itemDto.getQuantity(), enteredStatus
            );
        }

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        EmployeeLogin employeeLogin = employeeLoginRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Login not found " + username));

        orderStatusHistoryService.recordStatusChange(
                order, null, enteredStatus, employeeLogin.getEmployee(), "Vytvoření objednávky"
        );

        return order;
    }

    @Transactional
    public void addItem(
            Integer orderId,
            OrderItemCreateDto dto
    ){
        Order order = getEditableOrder(orderId);

        if(dto.getQuantity() == null || dto.getQuantity() <= 0){
            throw new RuntimeException("Quantity must be greater than zero");
        }

        Product product = getOrderableProduct(dto.getProductId());

        boolean alreadyExists = orderItemRepository
                .existsByOrderIdAndWarehouseItemProductId(
                        orderId, product.getId()
                );

        if(alreadyExists){
            throw new RuntimeException(
                    "Product already exists in order: "
                    + product.getProductCode()
            );
        }

        allocateWarehouseItems(
                order, product, dto.getQuantity(), order.getStatus()
        );
    }

    @Transactional
    public void updateItem(
            Integer orderId,
            Integer productId,
            OrderItemUpdateDto dto
    ){
        Order order = getEditableOrder(orderId);

        if(dto.getQuantity() == null || dto.getQuantity() <= 0){
            throw new IllegalStateException(
                    "Quantity must be greater than zero"
            );
        }

        List<OrderItem> orderItems =
                orderItemRepository.findAllByOrderIdAndWarehouseItemProductId(
                        orderId, productId
                );
        if(orderItems.isEmpty()){
            throw new RuntimeException(
                    "Product not found in order: " + productId
            );
        }

        int currentQuantity = orderItems.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
        int pickedQuantity = orderItems.stream()
                .mapToInt(OrderItem::getPickedQuantity)
                .sum();
        int newQuantity = dto.getQuantity();

        if(newQuantity < pickedQuantity){
            throw new RuntimeException(
                    "Quantity cannot be lower than already picked quantity"
            );
        }

        if(newQuantity == currentQuantity){
            return;
        }

        if(newQuantity > currentQuantity){
            Product product = orderItems.get(0)
                    .getWarehouseItem()
                    .getProduct();

            allocateWarehouseItems(
                    order, product, newQuantity - currentQuantity, order.getStatus()
            );
        }else{
            decreaseAllocation(orderItems, newQuantity);
        }
    }

    @Transactional
    public void deleteItem(Integer orderId, Integer productId){
        getEditableOrder(orderId);

        List<OrderItem> orderItems =
                orderItemRepository.findAllByOrderIdAndWarehouseItemProductId(
                        orderId, productId
                );
        if(orderItems.isEmpty()){
            throw new RuntimeException(
                    "Product not found in order: " + productId
            );
        }

        long totalItemCount = orderItemRepository.countByOrderId(orderId);
        if(orderItems.size() == totalItemCount){
            throw new IllegalStateException(
                    "Cannot delete the last product from an order"
            );
        }

        boolean hasPickedItems = orderItems.stream()
                .anyMatch(item -> item.getPickedQuantity() > 0);
        if(hasPickedItems){
            throw new IllegalStateException(
                    "Cannot delete product with already picked quantity"
            );
        }

        for(OrderItem item : orderItems){
            WarehouseItem warehouseItem = item.getWarehouseItem();

            int newAllocatedQuantity =
                    warehouseItem.getAllocatedQuantity() - item.getQuantity();

            if(newAllocatedQuantity < 0){
                throw new IllegalStateException(
                        "Allocated quantity cannot be negative for EIT "
                        + warehouseItem.getEtiNumber()
                );
            }

            warehouseItem.setAllocatedQuantity(newAllocatedQuantity);
            warehouseItemRepository.save(warehouseItem);
        }

        orderItemRepository.deleteAll(orderItems);
    }

    @Transactional
    public void updateOrder(Integer orderId, OrderUpdateDto dto){
        Order order = getEditableOrder(orderId);

        if(dto.getCustomerId() == null){
            throw new IllegalStateException(
                    "Customer ID cannot be null"
            );
        }

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException(
                        "Customer not found " + dto.getCustomerId()
                ));

        if(order.getCustomer().getId().equals(customer.getId())){
            return;
        }

        order.setCustomer(customer);
        orderRepository.save(order);
    }

    @Transactional
    public void changeStatus(Integer orderId, OrderStatusChangeDto dto){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException(
                        "Order not found " + orderId
                ));

        Status oldStatus = order.getStatus();

        Status newStatus = statusRepository.findByCode(dto.getStatusCode())
                .orElseThrow(() -> new RuntimeException(
                        "Status not found " + dto.getStatusCode()
                ));

        validateStatusTransition(
                oldStatus.getCode(), newStatus.getCode()
        );

        List<OrderItem> orderItems =
                orderItemRepository.findAllByOrderId(orderId);

        if("CANCELLED".equals(newStatus.getCode())){
            boolean hasFinishedItems = orderItems.stream()
                    .anyMatch(item -> "FINISHED".equals(item.getStatus().getCode()));

            boolean hasPickedItems = orderItems.stream()
                    .anyMatch(item -> item.getPickedQuantity() > 0);
            if(hasFinishedItems || hasPickedItems){
                throw new IllegalStateException(
                        "Cannot cancel an order with picked or finished items"
                );
            }

            releaseOrderAllocations(orderItems);
        }
        updateOrderItemsStatus(orderItems, newStatus);

        order.setStatus(newStatus);
        orderRepository.save(order);

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        EmployeeLogin employeeLogin = employeeLoginRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException(
                        "Employee login not found " + username
                ));
        orderStatusHistoryService.recordStatusChange(
                order, oldStatus, newStatus, employeeLogin.getEmployee(), dto.getNote()
        );
    }
}
