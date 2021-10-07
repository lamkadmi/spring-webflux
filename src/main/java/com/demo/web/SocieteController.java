package com.demo.web;

import com.demo.dao.SocieteRepository;
import com.demo.dao.TransactionRepository;
import com.demo.entities.Societe;
import com.demo.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SocieteController {
    @Autowired
    private SocieteRepository repository;

    @Autowired
    private TransactionRepository transRepository;

    @GetMapping(value = "/societes")
    public Flux<Societe> findAll() {
        return repository.findAll();
    }

    @GetMapping(value = "/societes/{id}")
    public Mono<Societe> findById(@PathVariable String id) {
        return repository.findById(id);
    }

    @GetMapping(value = "/societes/{id}/transactions")
    public Flux<Transaction> transactionsBySociete(@PathVariable String id) {
        Societe societe = new Societe();
        societe.setId(id);
        return transRepository.findBySociete(societe);
    }

    @GetMapping(value = "/transactionsBySociete/{id}")
    public Flux<Transaction> transactionsBySoc(@PathVariable String id){
        Societe societe=new Societe();societe.setId(id);
        return transRepository.findBySociete(societe);
    }
}
