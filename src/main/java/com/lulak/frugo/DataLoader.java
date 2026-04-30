package com.lulak.frugo;

import com.lulak.frugo.model.Product;
import com.lulak.frugo.model.Role;
import com.lulak.frugo.model.User;
import com.lulak.frugo.repository.ProductRepository;
import com.lulak.frugo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args){

        if(userRepository.findByUsername("admin").isEmpty()){
            userRepository.save(
                    new User(
                            "admin",
                            passwordEncoder.encode("admin123"),
                            Role.ADMIN
                    )
            );
        }

        if(userRepository.findByUsername("user").isEmpty()){
            userRepository.save(
                    new User(
                            "user",
                            passwordEncoder.encode("user123"),
                            Role.USER
                    )
            );
        }
    }

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
