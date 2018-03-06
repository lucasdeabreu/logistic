package br.com.lucasdeabreu.playground.logistics.services;

import br.com.lucasdeabreu.playground.logistics.commands.CustomerCommand;
import br.com.lucasdeabreu.playground.logistics.converters.CustomerCommandToCustomer;
import br.com.lucasdeabreu.playground.logistics.converters.CustomerToCustomerCommand;
import br.com.lucasdeabreu.playground.logistics.domains.Customer;
import br.com.lucasdeabreu.playground.logistics.excepetions.NotFoundException;
import br.com.lucasdeabreu.playground.logistics.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerToCustomerCommand customerToCustomerCommand;
    private final CustomerCommandToCustomer customerCommandToCustomer;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerToCustomerCommand customerToCustomerCommand,
                               CustomerCommandToCustomer customerCommandToCustomer) {
        this.customerRepository = customerRepository;
        this.customerToCustomerCommand = customerToCustomerCommand;
        this.customerCommandToCustomer = customerCommandToCustomer;
    }

    @Override
    public List<CustomerCommand> findAllCustomers() {
        List<CustomerCommand> customers = new LinkedList<>();
        customerRepository.findAll()
                .iterator()
                .forEachRemaining(customer -> customers.add(toCommand(customer)));
        return customers;
    }

    @Override
    public CustomerCommand findById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent()) {
            throw new NotFoundException("Customer #" + id + " not found.");
        }
        return toCommand(customer.get());
    }

    @Override
    public CustomerCommand save(CustomerCommand command) {
        Customer customer = toCustomer(command);
        return toCommand(customerRepository.save(customer));
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    private Customer toCustomer(CustomerCommand command) {
        return customerCommandToCustomer.convert(command);
    }

    private CustomerCommand toCommand(Customer customer) {
        return customerToCustomerCommand.convert(customer);
    }
}
