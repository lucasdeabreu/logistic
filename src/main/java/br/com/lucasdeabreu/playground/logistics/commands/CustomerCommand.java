package br.com.lucasdeabreu.playground.logistics.commands;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerCommand {

    private Long id;

    private String name;

    private AddressCommand address;

}
