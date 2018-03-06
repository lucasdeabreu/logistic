package br.com.lucasdeabreu.playground.logistics.converters;

import br.com.lucasdeabreu.playground.logistics.commands.AddressCommand;
import br.com.lucasdeabreu.playground.logistics.commands.CustomerCommand;
import br.com.lucasdeabreu.playground.logistics.domains.Address;
import br.com.lucasdeabreu.playground.logistics.domains.Customer;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class CustomerCommandToCustomerTest {

    private final CustomerCommandToCustomer converter;

    public CustomerCommandToCustomerTest() {
        this.converter = new CustomerCommandToCustomer(new AddressCommandToAddress());
    }

    @Test
    public void convertNull() {
        Customer converted = converter.convert(null);
        assertThat(converted, is(nullValue()));
    }

    @Test
    public void convertBlank() {
        Customer converted = converter.convert(new CustomerCommand());
        assertThat(converted, is(not(equalTo(nullValue()))));
        assertThat(converted.getAddress(), is(not(nullValue())));
    }


    @Test
    public void convert() {
        long id = 1L;
        String name = "Lucas de Abreu";
        String address = "Rua Brasil n.666";
        String city = "Rio Preto";
        String postCode = "15014330";
        String state = "SP";
        CustomerCommand customer = CustomerCommand.builder()
                .id(id)
                .name(name)
                .address(
                        AddressCommand.builder()
                                .address(address)
                                .city(city)
                                .postCode(postCode)
                                .state(state)
                                .build()
                )
                .build();
        Customer convertedCustomer = converter.convert(customer);
        assertThat(convertedCustomer, is(not(equalTo(nullValue()))));

        assertThat(convertedCustomer.getId(), is(equalTo(id)));
        assertThat(convertedCustomer.getName(), is(equalTo(name)));

        Address convertedAddress = convertedCustomer.getAddress();
        assertThat(convertedAddress, is(not(equalTo(nullValue()))));
        assertThat(convertedAddress.getAddress(), is(equalTo(address)));
        assertThat(convertedAddress.getCity(), is(equalTo(city)));
        assertThat(convertedAddress.getPostCode(), is(equalTo(postCode)));
        assertThat(convertedAddress.getState(), is(equalTo(state)));
    }
}