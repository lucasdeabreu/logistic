package br.com.lucasdeabreu.playground.logistics.commands;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressCommand {
    private String address;

    private String city;

    private String state;

    private String postCode;
}
