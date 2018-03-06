package br.com.lucasdeabreu.playground.logistics.controllers;

import br.com.lucasdeabreu.playground.logistics.commands.CustomerCommand;
import br.com.lucasdeabreu.playground.logistics.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public ModelAndView listCustomers() {
        List<CustomerCommand> customers = customerService.findAllCustomers();
        return new ModelAndView("customer/list")
                .addObject("customers", customers);
    }

    @GetMapping("/customer/{id}/show")
    public ModelAndView showCustomer(@PathVariable("id") String id) {
        CustomerCommand customer = customerService.findById(Long.valueOf(id));
        return new ModelAndView("customer/show")
                .addObject("customer", customer);
    }

    @GetMapping("/customer/new")
    public ModelAndView newCustomer() {
        return new ModelAndView("customer/form")
                .addObject("customer", new CustomerCommand());
    }

    @GetMapping("/customer/{id}/update")
    public ModelAndView updateCustomer(@PathVariable("id") String id) {
        CustomerCommand customer = customerService.findById(Long.valueOf(id));
        return new ModelAndView("customer/form")
                .addObject("customer", customer);
    }

    @PostMapping("/customer")
    public String saveOrUpdateCustomer(@ModelAttribute("customer") CustomerCommand customer) {
        customer = customerService.save(customer);
        return "redirect:/customer/" + customer.getId() + "/show";
    }

    @GetMapping("/customer/{id}/delete")
    public String deleteCustomer(@PathVariable("id") String id) {
        customerService.deleteById(Long.valueOf(id));
        return "redirect:/customer";
    }

}
