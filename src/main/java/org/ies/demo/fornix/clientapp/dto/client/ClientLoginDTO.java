package org.ies.demo.fornix.clientapp.dto.client;

import lombok.Data;

@Data
public class ClientLoginDTO {
    private String login;     // username o email
    private String password;
}