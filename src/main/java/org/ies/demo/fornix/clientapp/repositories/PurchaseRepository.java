package org.ies.demo.fornix.clientapp.repositories;

import org.ies.demo.fornix.clientapp.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    List<Purchase> findByClientId(Integer clientId);
    boolean existsByClientIdAndGameId(Integer clientId, Integer gameId);
}