package br.com.lucasdeabreu.playground.logistics.converters;

import br.com.lucasdeabreu.playground.logistics.commands.AddressCommand;
import br.com.lucasdeabreu.playground.logistics.commands.CustomerCommand;
import br.com.lucasdeabreu.playground.logistics.domains.Address;
import br.com.lucasdeabreu.playground.logistics.domains.Customer;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class CustomerToCustomerCommandTest {

    private final CustomerToCustomerCommand converter;

    public CustomerToCustomerCommandTest() {
        this.converter = new CustomerToCustomerCommand(new AddressToAddressCommand());
    }

    @Test
    public void convertNull() {
        CustomerCommand converted = converter.convert(null);
        assertThat(converted, is(nullValue()));
    }

    @Test
    public void convertBlank() {
        CustomerCommand converted = converter.convert(new Customer());
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
        Customer customer = Customer.builder()
                .id(id)
                .name(name)
                .address(
                        Address.builder()
                                .address(address)
                                .city(city)
                                .postCode(postCode)
                                .state(state)
                                .build()
                )
                .build();
        CustomerCommand convertedCustomer = converter.convert(customer);
        assertThat(convertedCustomer, is(not(equalTo(nullValue()))));

        assertThat(convertedCustomer.getId(), is(equalTo(id)));
        assertThat(convertedCustomer.getName(), is(equalTo(name)));

        AddressCommand convertedAddress = convertedCustomer.getAddress();
        assertThat(convertedAddress, is(not(equalTo(nullValue()))));
        assertThat(convertedAddress.getAddress(), is(equalTo(address)));
        assertThat(convertedAddress.getCity(), is(equalTo(city)));
        assertThat(convertedAddress.getPostCode(), is(equalTo(postCode)));
        assertThat(convertedAddress.getState(), is(equalTo(state)));
    }
}