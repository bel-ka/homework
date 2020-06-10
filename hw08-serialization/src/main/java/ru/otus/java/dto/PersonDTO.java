package ru.otus.java.dto;

import lombok.*;

@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    @Setter@Getter
    private long personId;
    @Setter@Getter
    private String firstName;
    @Setter@Getter
    private String lastName;
    @Setter@Getter
    private AddressDTO address;
}
