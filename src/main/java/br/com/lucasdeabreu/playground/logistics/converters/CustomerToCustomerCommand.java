package br.com.lucasdeabreu.playground.logistics.converters;

import br.com.lucasdeabreu.playground.logistics.commands.AddressCommand;
import br.com.lucasdeabreu.playground.logistics.commands.CustomerCommand;
import br.com.lucasdeabreu.playground.logistics.domains.Customer;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerToCustomerCommand implements Converter<Customer, CustomerCommand> {

    private final AddressToAddressCommand addressToAddressCommand;

    public CustomerToCustomerCommand(AddressToAddressCommand addressToAddressCommand) {
        this.addressToAddressCommand = addressToAddressCommand;
    }

    @Synchronized
    @Override
    public CustomerCommand convert(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerCommand customerCommand = new CustomerCommand();
        customerCommand.setId(customer.getId());
        customerCommand.setName(customer.getName());
        AddressCommand addressCommand = addressToAddressCommand.convert(customer.getAddress());
        customerCommand.setAddress(addressCommand);
        return customerCommand;
    }
}
