package org.ies.demo.fornix.clientapp.dto.wishlist;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WishlistResponseDTO {
    private Integer id;
    private Integer clientId;
    private Integer gameId;
    private String gameTitle;
    private LocalDate addedAt;
}