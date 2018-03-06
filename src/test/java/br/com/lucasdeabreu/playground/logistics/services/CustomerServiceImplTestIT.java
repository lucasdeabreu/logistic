package br.com.lucasdeabreu.playground.logistics.services;

import br.com.lucasdeabreu.playground.logistics.commands.CustomerCommand;
import br.com.lucasdeabreu.playground.logistics.converters.AddressCommandToAddress;
import br.com.lucasdeabreu.playground.logistics.converters.CustomerCommandToCustomer;
import br.com.lucasdeabreu.playground.logistics.domains.Customer;
import br.com.lucasdeabreu.playground.logistics.excepetions.NotFoundException;
import br.com.lucasdeabreu.playground.logistics.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class CustomerServiceImplTestIT {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findAllCustomers() {
        List<CustomerCommand> allCustomers = customerService.findAllCustomers();
        assertThat(allCustomers, is(not(nullValue())));
        assertThat(allCustomers, hasSize((int) customerRepository.count()));
    }

    @Test
    @Transactional
    public void findAllCustomersWhenEmptyList() {
        List<Customer> backup = customerRepository.findAll();
        customerRepository.deleteAllInBatch();
        List<CustomerCommand> allCustomers = customerService.findAllCustomers();
        assertThat(allCustomers, is(not(nullValue())));
        assertThat(allCustomers, hasSize(0));
        customerRepository.saveAll(backup);
    }

    @Test
    public void findById() {
        CustomerCommandToCustomer converter = new CustomerCommandToCustomer(new AddressCommandToAddress());
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers, hasSize(greaterThan(0)));
        for (Customer customer : customers) {
            CustomerCommand command = customerService.findById(customer.getId());
            assertThat(customer, equalTo(converter.convert(command)));
        }
    }

    @Test(expected = NotFoundException.class)
    public void findByIdWhenNotFound() {
        customerService.findById(666L);
    }

}
