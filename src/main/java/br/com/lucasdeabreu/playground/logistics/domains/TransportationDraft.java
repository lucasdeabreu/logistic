package br.com.lucasdeabreu.playground.logistics.domains;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class TransportationDraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer receiver;

    private String note;

    private Date creationDate;

    @OneToMany(mappedBy = "transportationDraft")
    private List<TransportationDraftDetail> details;

}
