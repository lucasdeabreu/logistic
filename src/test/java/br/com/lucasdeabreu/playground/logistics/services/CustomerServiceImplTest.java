package br.com.lucasdeabreu.playground.logistics.services;

import br.com.lucasdeabreu.playground.logistics.commands.CustomerCommand;
import br.com.lucasdeabreu.playground.logistics.converters.AddressCommandToAddress;
import br.com.lucasdeabreu.playground.logistics.converters.AddressToAddressCommand;
import br.com.lucasdeabreu.playground.logistics.converters.CustomerCommandToCustomer;
import br.com.lucasdeabreu.playground.logistics.converters.CustomerToCustomerCommand;
import br.com.lucasdeabreu.playground.logistics.domains.Customer;
import br.com.lucasdeabreu.playground.logistics.excepetions.NotFoundException;
import br.com.lucasdeabreu.playground.logistics.repositories.CustomerRepository;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


public class CustomerServiceImplTest {

    private final CustomerService customerService;

    @Mock
    private CustomerRepository repository;

    public CustomerServiceImplTest() {
        MockitoAnnotations.initMocks(this);
        AddressToAddressCommand addressToCommand = new AddressToAddressCommand();
        CustomerToCustomerCommand customerToCommand = new CustomerToCustomerCommand(addressToCommand);
        AddressCommandToAddress commandToAddress = new AddressCommandToAddress();
        CustomerCommandToCustomer commandToCustomer = new CustomerCommandToCustomer(commandToAddress);
        this.customerService = new CustomerServiceImpl(repository, customerToCommand, commandToCustomer);
    }

    @Test
    public void findAllCustomers() {
        when(repository.findAll())
                .thenReturn(
                        Arrays.asList(new Customer(), new Customer(), new Customer())
                );
        List<CustomerCommand> allCustomers = customerService.findAllCustomers();
        assertThat(allCustomers, is(not(nullValue())));
        assertThat(allCustomers, hasSize(3));
    }

    @Test
    public void findAllCustomersWhenEmptyList() {
        when(repository.findAll()).thenReturn(new LinkedList<>());
        List<CustomerCommand> allCustomers = customerService.findAllCustomers();
        assertThat(allCustomers, is(not(nullValue())));
        assertThat(allCustomers, hasSize(0));
    }

    @Test
    public void findById() {
        Optional<Customer> optionalCustomer = Optional.of(new Customer());
        when(repository.findById(ArgumentMatchers.any())).thenReturn(optionalCustomer);
        CustomerCommand customerCommand = customerService.findById(anyLong());
        assertThat(customerCommand, is(not(nullValue())));
    }

    @Test(expected = NotFoundException.class)
    public void findByIdWhenNotFound() {
        Optional<Customer> optionalCustomer = Optional.empty();
        when(repository.findById(ArgumentMatchers.any())).thenReturn(optionalCustomer);
        customerService.findById(anyLong());
    }

}