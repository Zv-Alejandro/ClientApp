package org.ies.demo.fornix.clientapp.dto.client;

import lombok.Data;

@Data
public class ClientRegisterDTO {
    private String username;
    private String nickname;
    private String email;
    private String password;
}