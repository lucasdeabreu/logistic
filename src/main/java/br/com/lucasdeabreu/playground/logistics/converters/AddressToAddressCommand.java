package br.com.lucasdeabreu.playground.logistics.converters;

import br.com.lucasdeabreu.playground.logistics.commands.AddressCommand;
import br.com.lucasdeabreu.playground.logistics.domains.Address;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddressToAddressCommand implements Converter<Address, AddressCommand> {

    @Synchronized
    @Override
    public AddressCommand convert(Address address) {
        if (address == null) {
            return null;
        }
        AddressCommand addressCommand = new AddressCommand();
        addressCommand.setAddress(address.getAddress());
        addressCommand.setCity(address.getCity());
        addressCommand.setPostCode(address.getPostCode());
        addressCommand.setState(address.getState());
        return addressCommand;
    }
}
