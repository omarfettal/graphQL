package ma.projet.graph.controllers;

import lombok.AllArgsConstructor;
import ma.projet.graph.entities.Compte;
import ma.projet.graph.entities.Transaction;
import ma.projet.graph.entities.TransactionRequest;
import ma.projet.graph.entities.TypeTransaction;
import ma.projet.graph.repositories.CompteRepository;
import ma.projet.graph.repositories.TransactionRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class TransactionControllerGraphQL {
    private final CompteRepository compteRepository;
    private TransactionRepository transactionRepository;

    @MutationMapping
    public Transaction addTransaction(@Argument("transaction") TransactionRequest transactionRequest) {
        if (transactionRequest == null) {
            throw new RuntimeException("TransactionRequest is null");
        }

        Compte compte = compteRepository.findById(transactionRequest.getCompteId())
                .orElseThrow(() -> new RuntimeException("Compte not Found"));

        Transaction transaction = new Transaction();
        transaction.setMontant(transactionRequest.getMontant());
        transaction.setDate(transactionRequest.getDate());
        transaction.setType(transactionRequest.getType());
        transaction.setCompte(compte);

        transactionRepository.save(transaction);
        return transaction;
    }

    @QueryMapping
    public List<Transaction> compteTransactions(@Argument Long id ) {
        Compte compte=compteRepository.findById(id).orElseThrow(()->new RuntimeException("Compte not Found"));
        return transactionRepository.findByCompte(compte);
    }
    @QueryMapping
    public Map<String, Object> transactionStats() {
        long count = transactionRepository.count();
        double sumDepots = transactionRepository.sumByType(TypeTransaction.DEPOT);
        double sumRetraits = transactionRepository.sumByType(TypeTransaction.RETRAIT);
        return Map.of(
                "count", count,
                "sumDepots", sumDepots,
                "sumRetraits", sumRetraits
        );
    }
}

