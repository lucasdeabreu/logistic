package br.com.lucasdeabreu.playground.logistics.converters;

import br.com.lucasdeabreu.playground.logistics.commands.CustomerCommand;
import br.com.lucasdeabreu.playground.logistics.domains.Address;
import br.com.lucasdeabreu.playground.logistics.domains.Customer;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerCommandToCustomer implements Converter<CustomerCommand, Customer> {

    private final AddressCommandToAddress addressCommandToAddress;

    public CustomerCommandToCustomer(AddressCommandToAddress addressCommandToAddress) {
        this.addressCommandToAddress = addressCommandToAddress;
    }

    @Synchronized
    @Override
    public Customer convert(CustomerCommand customerCommand) {
        if (customerCommand == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(customerCommand.getId());
        customer.setName(customerCommand.getName());
        Address address = addressCommandToAddress.convert(customerCommand.getAddress());
        customer.setAddress(address);
        return customer;
    }
}
