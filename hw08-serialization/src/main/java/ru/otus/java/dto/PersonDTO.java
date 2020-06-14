package ru.otus.java.dto;

import lombok.*;

@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Setter@Getter
public class PersonDTO {
    private long personId;
    private String firstName;
    private String lastName;
    private AddressDTO address;
}
