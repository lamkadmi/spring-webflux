package com.demo;


import com.demo.dao.SocieteRepository;
import com.demo.dao.TransactionRepository;
import com.demo.entities.Societe;
import com.demo.entities.Transaction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.stream.Stream;

@SpringBootApplication
public class WebFluxReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFluxReactiveApplication.class, args);
    }

    @Bean
    CommandLineRunner start(SocieteRepository societeRepository,
                            TransactionRepository transactionRepository) {
        return args -> {
            societeRepository.deleteAll().subscribe(null, null, () -> {
                Stream.of("SG", "AWB", "BMCE", "AXA").forEach(s -> {
                    societeRepository.save(new Societe(s, s, 100 + Math.random() * 900))
                            .subscribe(soc -> {
                                System.out.println(soc.toString());
                            });
                });
                transactionRepository.deleteAll().subscribe(null, null, () -> {
                    Stream.of("SG", "AWB", "BMCE", "AXA").forEach(s -> {
                        societeRepository.findById(s).subscribe(soc -> {
                            for (int i = 0; i < 10; i++) {
                                Transaction transaction = new Transaction();
                                transaction.setInstant(Instant.now());
                                transaction.setSociete(soc);
                                transaction.setPrice(soc.getPrice() * (1 + (Math.random() * 12 - 6) / 100));
                                transactionRepository.save(transaction).subscribe(t -> {
                                    System.out.println(t.toString());
                                });
                            }
                        });

                    });
                });


            });
            System.out.println("......");

        };
    }

}

