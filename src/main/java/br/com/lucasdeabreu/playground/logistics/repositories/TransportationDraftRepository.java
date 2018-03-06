package br.com.lucasdeabreu.playground.logistics.repositories;

import br.com.lucasdeabreu.playground.logistics.domains.TransportationDraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportationDraftRepository extends JpaRepository<TransportationDraft, Long> {
}
