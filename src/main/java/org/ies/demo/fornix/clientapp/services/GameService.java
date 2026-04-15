package org.ies.demo.fornix.clientapp.services;

import org.ies.demo.fornix.clientapp.dto.game.GameResponseDTO;
import org.ies.demo.fornix.clientapp.models.Game;
import org.ies.demo.fornix.clientapp.models.Tag;
import org.ies.demo.fornix.clientapp.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<GameResponseDTO> getAll() {
        List<Game> games = gameRepository.findAll();
        List<GameResponseDTO> response = new ArrayList<>();

        for (Game game : games) {
            response.add(toResponseDTO(game));
        }

        return response;
    }

    public GameResponseDTO getById(Integer id) {
        Game game = gameRepository.findById(id).orElse(null);
        if (game == null) {
            return null;
        }
        return toResponseDTO(game);
    }

    public List<GameResponseDTO> getByTitle(String title) {
        List<Game> games = gameRepository.findByTitleContainingIgnoreCase(title);
        List<GameResponseDTO> response = new ArrayList<>();

        for (Game game : games) {
            response.add(toResponseDTO(game));
        }

        return response;
    }

    public List<GameResponseDTO> getByDevId(Integer devId) {
        List<Game> games = gameRepository.findByDevId(devId);
        List<GameResponseDTO> response = new ArrayList<>();

        for (Game game : games) {
            response.add(toResponseDTO(game));
        }

        return response;
    }

    public List<GameResponseDTO> getByTagId(Integer tagId) {
        List<Game> games = gameRepository.findByTagsId(tagId);
        List<GameResponseDTO> response = new ArrayList<>();

        for (Game game : games) {
            response.add(toResponseDTO(game));
        }

        return response;
    }

    private GameResponseDTO toResponseDTO(Game game) {
        GameResponseDTO dto = new GameResponseDTO();

        dto.setId(game.getId());
        dto.setTitle(game.getTitle());
        dto.setDescription(game.getDescription());
        dto.setPublishedDate(game.getPublishedDate());
        dto.setS3PathGeneric(game.getS3PathGeneric());
        dto.setTamanoMb(game.getTamanoMb());
        dto.setDownloads(game.getDownloads());
        dto.setPrice(game.getPrice());

        if (game.getDev() != null) {
            dto.setDevId(game.getDev().getId());
            dto.setDevUsername(game.getDev().getUsername());
        }

        List<String> tagNames = new ArrayList<>();
        if (game.getTags() != null) {
            for (Tag tag : game.getTags()) {
                tagNames.add(tag.getName());
            }
        }
        dto.setTags(tagNames);

        return dto;
    }
}