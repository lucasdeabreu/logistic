package br.com.lucasdeabreu.playground.logistics.bootstraps;

import br.com.lucasdeabreu.playground.logistics.domains.Address;
import br.com.lucasdeabreu.playground.logistics.domains.Customer;
import br.com.lucasdeabreu.playground.logistics.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
@Profile("dev")
public class DevDataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    public DevDataLoader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCustomers();
    }

    private void loadCustomers() {
        for (long i = 0; i < 10; i++) {
            customerRepository.save(createCustomerById(i));
        }
    }

    private Customer createCustomerById(long id) {
        return Customer.builder()
                .id(id)
                .name("Customer " + id)
                .address(
                        Address.builder()
                                .address("Address " + id)
                                .city("City " + id)
                                .postCode(String.join("", Collections.nCopies(7, "" + id)))
                                .state("SP")
                                .build()
                )
                .build();
    }
}
