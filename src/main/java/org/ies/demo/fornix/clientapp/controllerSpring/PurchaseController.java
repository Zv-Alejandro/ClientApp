package org.ies.demo.fornix.clientapp.controllerSpring;

import org.ies.demo.fornix.clientapp.dto.purchase.LibraryGameDTO;
import org.ies.demo.fornix.clientapp.dto.purchase.PurchaseCreateDTO;
import org.ies.demo.fornix.clientapp.dto.purchase.PurchaseResponseDTO;
import org.ies.demo.fornix.clientapp.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ies.demo.fornix.clientapp.exception.AlreadyPurchasedException;
import org.ies.demo.fornix.clientapp.dto.purchase.DownloadResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("")
    public ResponseEntity<?> createPurchase(@RequestBody PurchaseCreateDTO dto) {
        try {
            PurchaseResponseDTO response = purchaseService.createPurchase(dto);
            if (response == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(response);
        } catch (AlreadyPurchasedException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<PurchaseResponseDTO>> getByClientId(@PathVariable Integer clientId) {
        try {
            return ResponseEntity.ok(purchaseService.getByClientId(clientId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/client/{clientId}/library")
    public ResponseEntity<List<LibraryGameDTO>> getLibraryByClientId(@PathVariable Integer clientId) {
        try {
            return ResponseEntity.ok(purchaseService.getLibraryByClientId(clientId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/client/{clientId}/download/{gameId}")
    public ResponseEntity<DownloadResponseDTO> downloadGame(@PathVariable Integer clientId,
                                                            @PathVariable Integer gameId) {
        try {
            DownloadResponseDTO response = purchaseService.downloadGame(clientId, gameId);
            if (response == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}