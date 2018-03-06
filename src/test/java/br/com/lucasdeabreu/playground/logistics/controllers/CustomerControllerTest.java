package br.com.lucasdeabreu.playground.logistics.controllers;

import br.com.lucasdeabreu.playground.logistics.commands.CustomerCommand;
import br.com.lucasdeabreu.playground.logistics.excepetions.NotFoundException;
import br.com.lucasdeabreu.playground.logistics.services.CustomerService;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    private final MockMvc mockMvc;

    public CustomerControllerTest() {
        MockitoAnnotations.initMocks(this);
        CustomerController customerController = new CustomerController(customerService);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void listCustomers() throws Exception {
        when(customerService.findAllCustomers()).thenReturn(Arrays.asList(new CustomerCommand(), new CustomerCommand()));
        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/list"))
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attribute("customers", is(not(nullValue()))))
                .andExpect(model().attribute("customers", hasSize(2)));
    }

    @Test
    public void showCustomer() throws Exception {
        when(customerService.findById(any())).thenReturn(new CustomerCommand());
        mockMvc.perform(get("/customer/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/show"))
                .andExpect(model().attributeExists("customer"))
                .andExpect(model().attribute("customer", is(not(nullValue()))));
    }

    @Test
    public void showCustomerWhenNumberFormatException() throws Exception {
        mockMvc.perform(get("/customer/ABC/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    public void showCustomerWhenNotFound() throws Exception {
        when(customerService.findById(any())).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/customer/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    public void formNewCustomer() throws Exception {
        mockMvc.perform(get("/customer/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/form"))
                .andExpect(model().attributeExists("customer"))
                .andExpect(model().attribute("customer", is(not(nullValue()))));
    }

    @Test
    public void formUpdateCustomer() throws Exception {
        when(customerService.findById(any())).thenReturn(new CustomerCommand());
        mockMvc.perform(get("/customer/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/form"))
                .andExpect(model().attributeExists("customer"))
                .andExpect(model().attribute("customer", is(not(nullValue()))));
    }

    @Test
    public void saveOrUpdateCustomer() throws Exception {
        CustomerCommand command = CustomerCommand.builder()
                .id(5L)
                .build();
        when(customerService.save(any())).thenReturn(command);
        mockMvc.perform(
                post("/customer")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", String.valueOf(command.getId()))
                        .param("name", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/" + command.getId() + "/show"))
                .andExpect(model().attributeExists("customer"))
                .andExpect(model().attribute("customer", is(not(nullValue()))));
    }

    @Test
    public void deleteCustomer() throws Exception {
        mockMvc.perform(get("/customer/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer"));
    }

}