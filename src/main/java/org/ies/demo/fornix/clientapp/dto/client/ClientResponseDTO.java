package org.ies.demo.fornix.clientapp.dto.client;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientResponseDTO {
    private Integer id;
    private String username;
    private String email;
    private String bio;
}