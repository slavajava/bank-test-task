package test.task.bank.repos;

import org.springframework.data.repository.CrudRepository;
import test.task.bank.domain.Account;

import java.util.List;

public interface AccountRepo extends CrudRepository<Account, Integer> {
    Account findById(int id);
    List<Account> findByClientId(int clientId);
}
