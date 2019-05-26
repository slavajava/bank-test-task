package test.task.bank.repos;

import org.springframework.data.repository.CrudRepository;
import test.task.bank.domain.Client;

import java.util.List;

public interface ClientRepo extends CrudRepository<Client, Integer> {
    Client findById(int id);
    List<Client> findByName(String name);
    List<Client> findByAge(int age);

}
