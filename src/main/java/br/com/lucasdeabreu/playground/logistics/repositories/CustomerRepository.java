package br.com.lucasdeabreu.playground.logistics.repositories;

import br.com.lucasdeabreu.playground.logistics.domains.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
