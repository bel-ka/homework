package ru.otus.java.dto;

import lombok.*;

@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Setter@Getter
public class AddressDTO {
    private String city;
    private String country;
}
