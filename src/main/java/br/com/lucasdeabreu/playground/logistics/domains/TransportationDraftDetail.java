package br.com.lucasdeabreu.playground.logistics.domains;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class TransportationDraftDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer sender;

    private String invoice;

    private BigDecimal value;

    @ManyToOne
    private TransportationDraft transportationDraft;

}
