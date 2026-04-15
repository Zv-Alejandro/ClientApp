package org.ies.demo.fornix.clientapp.dto.purchase;

import lombok.Data;

@Data
public class DownloadResponseDTO {
    private Integer gameId;
    private String title;
    private String downloadPath;
    private Integer downloads;
}