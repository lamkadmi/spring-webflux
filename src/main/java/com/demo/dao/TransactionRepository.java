package com.demo.dao;

import com.demo.entities.Societe;
import com.demo.entities.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;


public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    Flux<Transaction> findBySociete(Societe societe);
}
