package org.ies.demo.fornix.clientapp.repositories;

import org.ies.demo.fornix.clientapp.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByUsername(String username);
    Optional<Client> findByEmail(String email);
    Optional<Client> findByNickname(String nickname);
}