package org.ies.demo.fornix.clientapp.dto.client;

import lombok.Data;

@Data
public class ClientUpdateDTO {
    private String nickname;
    private String email;
    private String bio;
    private String pathProfilePictureS3;
}