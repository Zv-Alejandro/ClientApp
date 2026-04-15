package org.ies.demo.fornix.clientapp.dto.purchase;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LibraryGameDTO {
    private Integer gameId;
    private String title;
    private String description;
    private LocalDate publishedDate;
    private String s3PathGeneric;
    private BigDecimal tamanoMb;
    private Integer downloads;
    private BigDecimal price;
}