package org.ies.demo.fornix.clientapp.controllerSpring;

import org.ies.demo.fornix.clientapp.dto.wishlist.WishlistCreateDTO;
import org.ies.demo.fornix.clientapp.dto.wishlist.WishlistResponseDTO;
import org.ies.demo.fornix.clientapp.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ies.demo.fornix.clientapp.exception.AlreadyInWishlistException;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody WishlistCreateDTO dto) {
        try {
            WishlistResponseDTO response = wishlistService.create(dto);
            if (response == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(response);
        } catch (AlreadyInWishlistException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<WishlistResponseDTO>> getByClientId(@PathVariable Integer clientId) {
        try {
            return ResponseEntity.ok(wishlistService.getByClientId(clientId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            boolean deleted = wishlistService.delete(id);
            if (!deleted) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}