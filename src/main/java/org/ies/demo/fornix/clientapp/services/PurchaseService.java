package org.ies.demo.fornix.clientapp.services;

import org.ies.demo.fornix.clientapp.dto.purchase.DownloadResponseDTO;
import org.ies.demo.fornix.clientapp.dto.purchase.LibraryGameDTO;
import org.ies.demo.fornix.clientapp.dto.purchase.PurchaseCreateDTO;
import org.ies.demo.fornix.clientapp.dto.purchase.PurchaseResponseDTO;
import org.ies.demo.fornix.clientapp.exception.AlreadyPurchasedException;
import org.ies.demo.fornix.clientapp.models.Game;
import org.ies.demo.fornix.clientapp.models.Purchase;
import org.ies.demo.fornix.clientapp.models.Client;
import org.ies.demo.fornix.clientapp.repositories.ClientRepository;
import org.ies.demo.fornix.clientapp.repositories.GameRepository;
import org.ies.demo.fornix.clientapp.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GameRepository gameRepository;

    public PurchaseResponseDTO createPurchase(PurchaseCreateDTO dto) {
        Client client = clientRepository.findById(dto.getClientId()).orElse(null);
        if (client == null) {
            return null;
        }

        Game game = gameRepository.findById(dto.getGameId()).orElse(null);
        if (game == null) {
            return null;
        }

        boolean alreadyPurchased = purchaseRepository.existsByClientIdAndGameId(dto.getClientId(), dto.getGameId());
        if (alreadyPurchased) {
            throw new AlreadyPurchasedException("El cliente ya compró este juego");
        }

        Purchase purchase = new Purchase();
        purchase.setClient(client);
        purchase.setGame(game);
        purchase.setPaymentDate(LocalDate.now());

        Purchase saved = purchaseRepository.save(purchase);
        return toResponseDTO(saved);
    }

    public List<PurchaseResponseDTO> getByClientId(Integer clientId) {
        List<Purchase> purchases = purchaseRepository.findByClientId(clientId);
        List<PurchaseResponseDTO> response = new ArrayList<>();

        for (Purchase purchase : purchases) {
            response.add(toResponseDTO(purchase));
        }

        return response;
    }

    public List<LibraryGameDTO> getLibraryByClientId(Integer clientId) {
        List<Purchase> purchases = purchaseRepository.findByClientId(clientId);
        List<LibraryGameDTO> library = new ArrayList<>();

        for (Purchase purchase : purchases) {
            Game game = purchase.getGame();

            LibraryGameDTO dto = new LibraryGameDTO();
            dto.setGameId(game.getId());
            dto.setTitle(game.getTitle());
            dto.setDescription(game.getDescription());
            dto.setPublishedDate(game.getPublishedDate());
            dto.setS3PathGeneric(game.getS3PathGeneric());
            dto.setTamanoMb(game.getTamanoMb());
            dto.setDownloads(game.getDownloads());
            dto.setPrice(game.getPrice());

            library.add(dto);
        }

        return library;
    }

    public DownloadResponseDTO downloadGame(Integer clientId, Integer gameId) {
        boolean purchased = purchaseRepository.existsByClientIdAndGameId(clientId, gameId);
        if (!purchased) {
            return null;
        }

        Game game = gameRepository.findById(gameId).orElse(null);
        if (game == null) {
            return null;
        }

        Integer downloads = game.getDownloads();
        if (downloads == null) {
            downloads = 0;
        }

        game.setDownloads(downloads + 1);
        Game updatedGame = gameRepository.save(game);

        DownloadResponseDTO dto = new DownloadResponseDTO();
        dto.setGameId(updatedGame.getId());
        dto.setTitle(updatedGame.getTitle());
        dto.setDownloadPath(updatedGame.getS3PathGeneric());
        dto.setDownloads(updatedGame.getDownloads());

        return dto;
    }

    public boolean hasPurchased(Integer clientId, Integer gameId) {
        return purchaseRepository.existsByClientIdAndGameId(clientId, gameId);
    }

    private PurchaseResponseDTO toResponseDTO(Purchase purchase) {
        PurchaseResponseDTO dto = new PurchaseResponseDTO();
        dto.setId(purchase.getId());
        dto.setPaymentDate(purchase.getPaymentDate());
        dto.setClientId(purchase.getClient().getId());
        dto.setGameId(purchase.getGame().getId());
        dto.setGameTitle(purchase.getGame().getTitle());
        dto.setPrice(purchase.getGame().getPrice());
        return dto;
    }
}