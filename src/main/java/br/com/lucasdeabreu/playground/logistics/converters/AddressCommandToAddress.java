package br.com.lucasdeabreu.playground.logistics.converters;

import br.com.lucasdeabreu.playground.logistics.commands.AddressCommand;
import br.com.lucasdeabreu.playground.logistics.domains.Address;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddressCommandToAddress implements Converter<AddressCommand, Address>{

    @Synchronized
    @Override
    public Address convert(AddressCommand addressCommand) {
        if (addressCommand == null) {
            return null;
        }
        Address address = new Address();
        address.setAddress(addressCommand.getAddress());
        address.setCity(addressCommand.getCity());
        address.setPostCode(addressCommand.getPostCode());
        address.setState(addressCommand.getState());
        return address;
    }
}
