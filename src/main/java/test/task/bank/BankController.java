package test.task.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import test.task.bank.domain.Account;
import test.task.bank.domain.Client;
import test.task.bank.domain.Transaction;
import test.task.bank.repos.AccountRepo;
import test.task.bank.repos.ClientRepo;
import test.task.bank.repos.TransactionRepo;

import java.util.Map;

@Controller
public class BankController {

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @GetMapping
    public String main (Map<String, Object> model) {
        return "main";
    }

    @GetMapping("/client")
    public String client (Map<String, Object> model) {
        Iterable<Client> clients = clientRepo.findAll();
        model.put("clients", clients);
        return "client";
    }

    @GetMapping("/account")
    public String account (Map<String, Object> model) {
        Iterable<Account> accounts = accountRepo.findAll();
        model.put("accounts", accounts);
        return "account";
    }

    @GetMapping("/transaction")
    public String transaction (Map<String, Object> model) {
        Iterable<Transaction> transactions = transactionRepo.findAll();
        model.put("transactions", transactions);
        return "transaction";
    }

    @PostMapping(path = "/client")
    public String addClient(@RequestParam String name, @RequestParam String age, Map<String, Object> model){
        if (!name.isEmpty() && !age.isEmpty() && name != null && age != null) {
            Client client = new Client(name, Integer.parseInt(age));
            clientRepo.save(client);
        }
        Iterable<Client> clients = clientRepo.findAll();
        model.put("clients", clients);

        return "client";
    }

    @PostMapping(path = "/account")
    public String addAccount(@RequestParam int clientId, @RequestParam double amount, Map<String, Object> model) {
        if (clientRepo.existsById(clientId)) {
            Account account = new Account(clientId, amount);
            accountRepo.save(account);
        }

        Iterable<Account> accounts = accountRepo.findAll();
        model.put("accounts", accounts);

        return "account";
    }

    @PostMapping(path = "/transaction")
    public String addTransaction(@RequestParam int accountId, @RequestParam double sumMoney, Map<String, Object> model) {
        if (accountRepo.existsById(accountId)) {
            Transaction transaction = new Transaction(accountId, sumMoney);
            Account account = accountRepo.findById(accountId);
            account.setAmount(account.getAmount() + sumMoney);
            transactionRepo.save(transaction);
        }

        Iterable<Transaction> transactions = transactionRepo.findAll();
        model.put("transactions", transactions);

        return "transaction";
    }

    @PostMapping(path = "/account/filter")
    public String accountFilter(@RequestParam int filter, Map<String, Object> model) {
        Iterable<Account> accounts;
        if (filter > 0) {
            accounts = accountRepo.findByClientId(filter);
        }
        else {
            accounts = accountRepo.findAll();
        }

        model.put("accounts", accounts);
        return "accountFilter";
    }

    @PostMapping(path = "/transaction/filter")
    public String transactionFilter(@RequestParam int filter, Map<String, Object> model) {

        Iterable<Transaction> transactions;
        if (filter > 0) {
            transactions = transactionRepo.findByAccountId(filter);
        }
        else {
            transactions = transactionRepo.findAll();
        }

        model.put("transactions", transactions);
        return "transactionFilter";
    }

    @PostMapping(path = "/client/filter_name")
    public String clientFilter(@RequestParam String filter, Map<String, Object> model) {

        Iterable<Client> clients;
        if (filter != null && !filter.isEmpty()) {
            clients = clientRepo.findByName(filter);
        }
        else {
            clients = clientRepo.findAll();
        }

        model.put("clients", clients);
        return "clientFilter";
    }

    @PostMapping(path = "/client/filter_age")
    public String clientFilter(@RequestParam int filter, Map<String, Object> model) {

        Iterable<Client> clients;
        if (filter > 0) {
            clients = clientRepo.findByAge(filter);
        }
        else {
            clients = clientRepo.findAll();
        }

        model.put("clients", clients);
        return "clientFilter";
    }
}
