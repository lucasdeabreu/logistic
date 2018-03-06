package br.com.lucasdeabreu.playground.logistics.services;

import br.com.lucasdeabreu.playground.logistics.commands.CustomerCommand;

import java.util.List;

public interface CustomerService {
    List<CustomerCommand> findAllCustomers();

    CustomerCommand findById(Long id);

    CustomerCommand save(CustomerCommand customer);

    void deleteById(Long id);
}
