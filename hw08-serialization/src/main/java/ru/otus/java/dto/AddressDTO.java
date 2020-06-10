package ru.otus.java.dto;

import lombok.*;

@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    @Setter@Getter
    private String city;
    @Setter@Getter
    private String country;
}
