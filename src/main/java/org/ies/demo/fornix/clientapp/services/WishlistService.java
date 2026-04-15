package org.ies.demo.fornix.clientapp.services;

import org.ies.demo.fornix.clientapp.dto.wishlist.WishlistCreateDTO;
import org.ies.demo.fornix.clientapp.dto.wishlist.WishlistResponseDTO;
import org.ies.demo.fornix.clientapp.models.Client;
import org.ies.demo.fornix.clientapp.models.Game;
import org.ies.demo.fornix.clientapp.models.Wishlist;
import org.ies.demo.fornix.clientapp.repositories.ClientRepository;
import org.ies.demo.fornix.clientapp.repositories.GameRepository;
import org.ies.demo.fornix.clientapp.repositories.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ies.demo.fornix.clientapp.exception.AlreadyInWishlistException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GameRepository gameRepository;

    public WishlistResponseDTO create(WishlistCreateDTO dto) {
        Client client = clientRepository.findById(dto.getClientId()).orElse(null);
        if (client == null) {
            return null;
        }

        Game game = gameRepository.findById(dto.getGameId()).orElse(null);
        if (game == null) {
            return null;
        }

        boolean exists = wishlistRepository.existsByClientIdAndGameId(dto.getClientId(), dto.getGameId());
        if (exists) {
            throw new AlreadyInWishlistException("El juego ya está en la wishlist del cliente");
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setClient(client);
        wishlist.setGame(game);
        wishlist.setAddedAt(LocalDate.now());

        Wishlist saved = wishlistRepository.save(wishlist);
        return toResponseDTO(saved);
    }

    public List<WishlistResponseDTO> getByClientId(Integer clientId) {
        List<Wishlist> list = wishlistRepository.findByClientId(clientId);
        List<WishlistResponseDTO> response = new ArrayList<>();

        for (Wishlist wishlist : list) {
            response.add(toResponseDTO(wishlist));
        }

        return response;
    }

    public boolean delete(Integer id) {
        if (!wishlistRepository.existsById(id)) {
            return false;
        }

        wishlistRepository.deleteById(id);
        return true;
    }

    private WishlistResponseDTO toResponseDTO(Wishlist wishlist) {
        WishlistResponseDTO dto = new WishlistResponseDTO();
        dto.setId(wishlist.getId());
        dto.setClientId(wishlist.getClient().getId());
        dto.setGameId(wishlist.getGame().getId());
        dto.setGameTitle(wishlist.getGame().getTitle());
        dto.setAddedAt(wishlist.getAddedAt());
        return dto;
    }
}