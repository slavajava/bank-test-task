package test.task.bank.repos;

import org.springframework.data.repository.CrudRepository;
import test.task.bank.domain.Transaction;

import java.util.List;


public interface TransactionRepo extends CrudRepository<Transaction, Integer> {
    List<Transaction> findByAccountId(int accountId);
}
