package com.lulak.frugo;

import com.lulak.frugo.model.*;
import com.lulak.frugo.repository.CustomerRepository;
import com.lulak.frugo.repository.EmployeeRepository;
import com.lulak.frugo.repository.OrderRepository;
import com.lulak.frugo.repository.ShipmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    public DataLoader(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args){
        if(customerRepository.count() > 0)return;

        Customer customer1 = new Customer();
        customer1.setCustomerNumber("CS001");
        customer1.setName("Zelinářství ABC");
        customer1.setCountry("Česká Republika");
        customer1.setAddress("Na Obci 1778, 251 01 Říčany u Prahy");
        customer1.setExecutive("Martin Novák");
        customer1.setEmail("m.novak@email.cz");
        customer1.setPhoneNumber("+420 777555666");

        Customer customer2 = new Customer();
        customer2.setCustomerNumber("CS002");
        customer2.setName("Petr Novotný");
        customer2.setCountry("Česká Republika");
        customer2.setAddress("Máchova 411, 332 02 Starý Plzenec");
        customer2.setEmail("p.novotný@email.cz");
        customer2.setPhoneNumber("+420 777999111");

        Customer customer3 = new Customer();
        customer3.setCustomerNumber("DE001");
        customer3.setName("Sportheim Bannewitz");
        customer3.setCountry("Německo");
        customer3.setAddress("Muhlenweg 3, 01728 Bannewitz");
        customer3.setExecutive("Hans Müller");
        customer3.setEmail("h.muller@email.de");
        customer3.setPhoneNumber("+49 155 4447895");

        customerRepository.saveAll(List.of(customer1, customer2, customer3));

    }


    //Employees
//    private final EmployeeRepository employeeRepository;
//
//    public DataLoader(EmployeeRepository employeeRepository){
//        this.employeeRepository = employeeRepository;
//    }
//
//    @Override
//    public void run(String... args){
//        if(employeeRepository.count() > 0) return;
//
//        Employee employee1 = new Employee();
//        employee1.setPersonalNumber("TS001");
//        employee1.setFirstName("Karel");
//        employee1.setLastName("Novotny");
//        employee1.setPosition("CEO");
//        employee1.setPhoneNumber("123456789");
//        employee1.setEmail("knovotny@email.com");
//        employee1.setAddress("Ulice12, Praha 1");
//        employee1.setHireDate(LocalDate.of(2008, 3, 1));
//        employee1.setBirthDate(LocalDate.of(1970, 6, 12));
//
//        Employee employee2 = new Employee();
//        employee2.setPersonalNumber("TS002");
//        employee2.setFirstName("Pepa");
//        employee2.setLastName("Novak");
//        employee2.setPosition("Skladnik");
//        employee2.setPhoneNumber("987654312");
//        employee2.setEmail("pnovak@email.com");
//        employee2.setAddress("Ulicka3, Praha 8");
//        employee2.setHireDate(LocalDate.of(2010, 10, 1));
//        employee2.setBirthDate(LocalDate.of(1991, 12, 20));
//
//        employeeRepository.saveAll(List.of(employee1, employee2));
//    }


    //Shipment
//    private final ShipmentRepository shipmentRepository;
//
//    public DataLoader(ShipmentRepository shipmentRepository){
//        this.shipmentRepository = shipmentRepository;
//    }
//
//    @Override
//    public void run(String... args){
//        if(shipmentRepository.count() > 0) return;
//
//        Shipment shipment1 = new Shipment();
//        shipment1.setShipmentNumber("Z-9001");
//        shipment1.setOrderNumber("FRG-0199");
//        shipment1.setStatus(Status.DOKONČENO);
//        shipment1.setCustomerName("Hotel Cloud");
//
//        Shipment shipment2 = new Shipment();
//        shipment2.setShipmentNumber("Z-9002");
//        shipment2.setOrderNumber("FRG-1003");
//        shipment2.setStatus(Status.UVOLNĚNO);
//        shipment2.setCustomerName("Petr Novotny");
//
//        Shipment shipment3 = new Shipment();
//        shipment3.setShipmentNumber("Z-9003");
//        shipment3.setOrderNumber("FRG-1002");
//        shipment3.setStatus(Status.ZADÁNO);
//        shipment3.setCustomerName("Penny Market - Hradčanská 12");
//
//        shipmentRepository.saveAll(List.of(shipment1, shipment2, shipment3));
//    }


    //Orders
//    private final OrderRepository orderRepository;
//
//    public DataLoader(OrderRepository orderRepository){
//        this.orderRepository = orderRepository;
//    }
//
//    @Override
//    public void run(String... args){
//
//        if(orderRepository.count() > 0) return;
//
//        //1.
//        Order order1 = new Order();
//        order1.setOrderNumber("FRG-1001");
//        order1.setStatus(Status.ZADÁNO);
//        order1.setCustomerName("Zelinářství ABC");
//
//        OrderItem item1 = new OrderItem();
//        item1.setCategory("fruits");
//        item1.setName("Jablka");
//        item1.setVariant("Golden Apple");
//        item1.setQuantity(100);
//        item1.setOrder(order1);
//
//        OrderItem item2 = new OrderItem();
//        item2.setCategory("fruits");
//        item2.setName("Banány");
//        item2.setVariant("Cavendish");
//        item2.setQuantity(50);
//        item2.setOrder(order1);
//
//        order1.setItems(List.of(item1, item2));
//
//        //2.
//        Order order2 = new Order();
//        order2.setOrderNumber("FRG-1002");
//        order2.setStatus(Status.UVOLNĚNO);
//        order2.setCustomerName("Penny Market - Hradčanská 12");
//
//        OrderItem item3 = new OrderItem();
//        item3.setCategory("fruits");
//        item3.setName("Jablka");
//        item3.setVariant("Gala");
//        item3.setQuantity(40);
//        item3.setOrder(order2);
//
//        OrderItem item4 = new OrderItem();
//        item4.setCategory("vegetables");
//        item4.setName("Mrkve");
//        item4.setVariant("Oranžová");
//        item4.setQuantity(55);
//        item4.setOrder(order2);
//
//        order2.setItems(List.of(item3, item4));
//
//        //3.
//        Order order3 = new Order();
//        order3.setOrderNumber("FRG-1003");
//        order3.setStatus(Status.DOKONČENO);
//        order3.setCustomerName("Petr Novotny");
//
//        OrderItem item5 = new OrderItem();
//        item5.setCategory("fruits");
//        item5.setName("Jablka");
//        item5.setVariant("Granny Smith");
//        item5.setQuantity(500);
//        item5.setOrder(order3);
//
//        OrderItem item6 = new OrderItem();
//        item6.setCategory("herbs");
//        item6.setName("Petržel");
//        item6.setVariant("Einfache Schnitt");
//        item6.setQuantity(250);
//        item6.setOrder(order3);
//
//        order3.setItems(List.of(item5, item6));
//
//        orderRepository.saveAll(List.of(order1, order2, order3));
//    }
//
//



    //Users!!
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder){
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void run(String... args){
//
//        if(userRepository.findByUsername("admin").isEmpty()){
//            userRepository.save(
//                    new User(
//                            "admin",
//                            passwordEncoder.encode("admin123"),
//                            Role.ADMIN
//                    )
//            );
//        }
//
//        if(userRepository.findByUsername("user").isEmpty()){
//            userRepository.save(
//                    new User(
//                            "user",
//                            passwordEncoder.encode("user123"),
//                            Role.USER
//                    )
//            );
//        }
//    }
//Produkty!!
//    private final ProductRepository repository;
//
//    public DataLoader(ProductRepository repository){
//        this.repository = repository;
//    }
//
//    @Override
//    public void run(String... args){
//        if(repository.count() == 0){
//            Product p1 = new Product();
//            p1.setId(1);
//            p1.setName("offering-products.apples");
//            p1.setCategory("fruits");
//            p1.setImage("/images/apple.png");
//            p1.setVariants(List.of("Golden Apple", "Granny Smith", "Gala"));
//
//            Product p2 = new Product();
//            p2.setId(2);
//            p2.setName("offering-products.bananas");
//            p2.setCategory("fruits");
//            p2.setImage("/images/banana.png");
//            p2.setVariants(List.of("Cavendish", "Plantain"));
//
//            Product p3 = new Product();
//            p3.setId(3);
//            p3.setName("offering-products.carrots");
//            p3.setCategory("vegetables");
//            p3.setImage("/images/carrot.png");
//            p3.setVariants(List.of("Oranžová", "Fialová"));
//
//            Product p4 = new Product();
//            p4.setId(4);
//            p4.setName("offering-products.parsley");
//            p4.setCategory("herbs");
//            p4.setImage("/images/parsley.png");
//            p4.setVariants(List.of("Einfache Schnitt"));
//
//            repository.saveAll(List.of(p1,p2,p3,p4));
//        }
//    }
}
