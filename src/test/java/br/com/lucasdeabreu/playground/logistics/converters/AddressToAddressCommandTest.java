package br.com.lucasdeabreu.playground.logistics.converters;

import br.com.lucasdeabreu.playground.logistics.commands.AddressCommand;
import br.com.lucasdeabreu.playground.logistics.domains.Address;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class AddressToAddressCommandTest {

    private final AddressToAddressCommand converter;

    public AddressToAddressCommandTest() {
        this.converter = new AddressToAddressCommand();
    }

    @Test
    public void convertNull() {
        AddressCommand converted = converter.convert(null);
        assertThat(converted, is(nullValue()));
    }

    @Test
    public void convertBlank() {
        AddressCommand converted = converter.convert(new Address());
        assertThat(converted, is(not(equalTo(nullValue()))));
    }


    @Test
    public void convert() {
        String addressStreet = "Rua Brasil n.666";
        String city = "Rio Preto";
        String postCode = "15014330";
        String state = "SP";
        Address address = Address.builder()
                                .address(addressStreet)
                                .city(city)
                                .postCode(postCode)
                                .state(state)
                                .build();

        AddressCommand convertedAddress = converter.convert(address);
        assertThat(convertedAddress, is(not(equalTo(nullValue()))));
        assertThat(convertedAddress.getAddress(), is(equalTo(addressStreet)));
        assertThat(convertedAddress.getCity(), is(equalTo(city)));
        assertThat(convertedAddress.getPostCode(), is(equalTo(postCode)));
        assertThat(convertedAddress.getState(), is(equalTo(state)));
    }
}