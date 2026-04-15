package org.ies.demo.fornix.clientapp.services;

import org.ies.demo.fornix.clientapp.dto.client.ClientLoginDTO;
import org.ies.demo.fornix.clientapp.dto.client.ClientRegisterDTO;
import org.ies.demo.fornix.clientapp.dto.client.ClientResponseDTO;
import org.ies.demo.fornix.clientapp.dto.client.ClientUpdateDTO;
import org.ies.demo.fornix.clientapp.dto.client.LoginResponseDTO;
import org.ies.demo.fornix.clientapp.models.Client;
import org.ies.demo.fornix.clientapp.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final int MAX_INTENTOS = 3;
    private static final int MINUTOS_BLOQUEO = 15;

    public ClientResponseDTO register(ClientRegisterDTO dto) {
        if (clientRepository.findByUsername(dto.getUsername()).isPresent()) {
            return null;
        }

        if (clientRepository.findByEmail(dto.getEmail()).isPresent()) {
            return null;
        }

        if (clientRepository.findByNickname(dto.getNickname()).isPresent()) {
            return null;
        }

        Client client = new Client();
        client.setUsername(dto.getUsername());
        client.setNickname(dto.getNickname());
        client.setEmail(dto.getEmail());
        client.setPasswordHashed(passwordEncoder.encode(dto.getPassword()));
        client.setEmailVerificado(false);
        client.setFechaVerificado(null);
        client.setIntentosLogin(0);
        client.setUltimoIntentoLogin(null);
        client.setBloqueadoHasta(null);
        client.setPathProfilePictureS3(null);
        client.setBio(null);
        client.setFechaCreacion(LocalDate.now());

        Client saved = clientRepository.save(client);
        return toResponseDTO(saved);
    }

    public LoginResponseDTO login(ClientLoginDTO dto) {
        Client client = findByLogin(dto.getLogin());

        if (client == null) {
            return new LoginResponseDTO(-2, "Usuario no existe");
        }

        if (client.getBloqueadoHasta() != null &&
                client.getBloqueadoHasta().isAfter(LocalDateTime.now())) {
            return new LoginResponseDTO(-3, "Usuario bloqueado temporalmente");
        }

        boolean passwordCorrecta = passwordEncoder.matches(dto.getPassword(), client.getPasswordHashed());

        client.setUltimoIntentoLogin(LocalDateTime.now());

        if (passwordCorrecta) {
            client.setIntentosLogin(0);
            client.setBloqueadoHasta(null);
            clientRepository.save(client);
            return new LoginResponseDTO(1, "Login correcto");
        }

        Integer intentos = client.getIntentosLogin();
        if (intentos == null) {
            intentos = 0;
        }

        intentos++;
        client.setIntentosLogin(intentos);

        if (intentos >= MAX_INTENTOS) {
            client.setBloqueadoHasta(LocalDateTime.now().plusMinutes(MINUTOS_BLOQUEO));
            client.setIntentosLogin(0);
        }

        clientRepository.save(client);
        return new LoginResponseDTO(-1, "Password incorrecta");
    }

    public ClientResponseDTO getById(Integer id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return null;
        }
        return toResponseDTO(client);
    }

    public ClientResponseDTO getByUsername(String username) {
        Client client = clientRepository.findByUsername(username).orElse(null);
        if (client == null) {
            return null;
        }
        return toResponseDTO(client);
    }

    public ClientResponseDTO update(Integer id, ClientUpdateDTO dto) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return null;
        }

        if (dto.getNickname() != null && !dto.getNickname().equals(client.getNickname())) {
            Optional<Client> existingNickname = clientRepository.findByNickname(dto.getNickname());
            if (existingNickname.isPresent()) {
                return null;
            }
            client.setNickname(dto.getNickname());
        }

        if (dto.getEmail() != null && !dto.getEmail().equals(client.getEmail())) {
            Optional<Client> existingEmail = clientRepository.findByEmail(dto.getEmail());
            if (existingEmail.isPresent()) {
                return null;
            }
            client.setEmail(dto.getEmail());
            client.setEmailVerificado(false);
            client.setFechaVerificado(null);
        }

        client.setBio(dto.getBio());
        client.setPathProfilePictureS3(dto.getPathProfilePictureS3());

        Client updated = clientRepository.save(client);
        return toResponseDTO(updated);
    }

    public boolean delete(Integer id) {
        if (!clientRepository.existsById(id)) {
            return false;
        }
        clientRepository.deleteById(id);
        return true;
    }

    public ClientResponseDTO verifyEmail(Integer id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return null;
        }

        client.setEmailVerificado(true);
        client.setFechaVerificado(LocalDate.now());

        Client updated = clientRepository.save(client);
        return toResponseDTO(updated);
    }

    private Client findByLogin(String login) {
        if (login == null) {
            return null;
        }

        if (login.contains("@")) {
            return clientRepository.findByEmail(login).orElse(null);
        }

        return clientRepository.findByUsername(login).orElse(null);
    }

    private ClientResponseDTO toResponseDTO(Client client) {
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(client.getId());
        dto.setUsername(client.getUsername());
        dto.setNickname(client.getNickname());
        dto.setEmail(client.getEmail());
        dto.setEmailVerificado(client.getEmailVerificado());
        dto.setBio(client.getBio());
        dto.setFechaCreacion(client.getFechaCreacion());
        dto.setPathProfilePictureS3(client.getPathProfilePictureS3());
        return dto;
    }
}