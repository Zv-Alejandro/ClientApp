package org.ies.demo.fornix.clientapp.dto.game;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class GameResponseDTO {
    private Integer id;
    private String title;
    private String description;
    private LocalDate publishedDate;
    private String s3PathGeneric;
    private BigDecimal tamanoMb;
    private Integer downloads;
    private BigDecimal price;
    private Integer devId;
    private String devUsername;
    private List<String> tags;
}