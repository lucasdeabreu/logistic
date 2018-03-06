package br.com.lucasdeabreu.playground.logistics.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    private String address;

    private String city;

    private String state;

    private String postCode;

}
